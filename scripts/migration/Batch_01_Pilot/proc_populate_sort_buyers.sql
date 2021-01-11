CREATE OR REPLACE PROCEDURE proc_populate_sort_buyers (v_entityid_seller IN NUMBER,
                                                       v_userid_seller IN NUMBER)
IS

  v_exist          NUMBER(1);
  v_company_id     NUMBER(9);
  v_userid         NUMBER(9);
  v_buyerid        NUMBER(9);
  v_buyerid_old    NUMBER(9);
  v_sorting        NUMBER(4);
  start_time    TIMESTAMP;

  CURSOR c_id IS
    SELECT v_userid_seller, b.fft_entity_id_buyer,c.user_id,c.company_id,b.sorting_order
         FROM oldeon.fft_entity_users a,
              oldeon.fft_sort_buyers b,
              eon_users c
        WHERE a.fft_entity_id=v_entityid_seller
          AND a.users_id=b.user_id
          AND b.fft_entity_id_buyer=c.user_id_old;

BEGIN

  SELECT CURRENT_TIMESTAMP INTO start_time FROM dual;

  OPEN c_id;
  LOOP
    FETCH c_id INTO v_userid, v_buyerid_old, v_buyerid, v_company_id, v_sorting;
    EXIT WHEN c_id%NOTFOUND;

    BEGIN
       SELECT 1
         INTO v_exist
         FROM eon_sort_buyers
        WHERE user_id=v_userid and buyer_id=v_buyerid;

       EXCEPTION
       WHEN NO_DATA_FOUND THEN
            INSERT INTO eon_sort_buyers (user_id, buyer_id, sorting)
                 SELECT v_userid, v_buyerid, v_sorting
                   FROM dual;

    END;

  END LOOP;

  CLOSE c_id;

  COMMIT;


  log_timestamp('proc_populate_sort_buyers', v_userid_seller, 0, start_time);
END;
/
