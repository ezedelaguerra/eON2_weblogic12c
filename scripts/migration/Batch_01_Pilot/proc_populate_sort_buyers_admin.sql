CREATE OR REPLACE PROCEDURE proc_populate_sort_buyers_ad (v_entityid IN NUMBER,
                                                          v_userid IN NUMBER)
IS

  v_exist          NUMBER(3);
  v_exist2          NUMBER(1);
  v_company_id     NUMBER(9);
  v_buyerid        NUMBER(9);
  v_sorting        NUMBER(4);
  v_username       VARCHAR2(50);
  start_time    TIMESTAMP;

  CURSOR c_id IS
    SELECT c.user_id, c.company_id, c.username, b.sorting_order
         FROM oldeon.fft_entity_users a,
              oldeon.fft_sort_buyers b,
              eon_users c
        WHERE a.fft_entity_id=v_entityid
          AND a.users_id=b.user_id
          AND b.fft_entity_id_buyer=c.user_id_old;

BEGIN

  SELECT CURRENT_TIMESTAMP INTO start_time FROM dual;

  OPEN c_id;
  LOOP
    FETCH c_id INTO v_buyerid, v_company_id, v_username, v_sorting;
    EXIT WHEN c_id%NOTFOUND;

    BEGIN
      SELECT count(*)
        INTO v_exist
        FROM migration_dealing_pattern
       WHERE user2=v_username
         AND relation='SELLER-BUYER'
         AND migration_status = 0; --TO_CHAR(migration_date,'MMDDYY')=TO_CHAR(sysdate,'MMDDYY');

      EXCEPTION
         WHEN NO_DATA_FOUND THEN
              null;
    END;

    IF v_exist>0 THEN
       BEGIN
         SELECT 1
           INTO v_exist2
           FROM eon_sort_buyers
          WHERE user_id=v_userid and buyer_id=v_buyerid;

         EXCEPTION
         WHEN NO_DATA_FOUND THEN
              INSERT INTO eon_sort_buyers (user_id, buyer_id, sorting)
                   SELECT v_userid, v_buyerid, v_sorting
                     FROM dual;
       END;
    END IF;

  END LOOP;

  CLOSE c_id;

  COMMIT;

  log_timestamp('proc_populate_sort_buyers_ad', v_userid, 0, start_time);

END;
/
