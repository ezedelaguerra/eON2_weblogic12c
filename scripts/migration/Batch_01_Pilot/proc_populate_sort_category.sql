CREATE OR REPLACE PROCEDURE proc_populate_sort_category (v_entityid IN NUMBER,
                                                       v_userid IN NUMBER)
IS

  v_exist          NUMBER(1);
  v_category_id    NUMBER(9);
  start_time       TIMESTAMP;

  CURSOR c_id IS
    SELECT CASE
             WHEN b.fft_sku_category_id=10000 THEN 1 --Fruits
             WHEN b.fft_sku_category_id=10001 THEN 2 --Vegetables
             WHEN b.fft_sku_category_id=10002 THEN 3 --FreshFish/Fish
            END		
         FROM oldeon.fft_entity_users a,
              oldeon.fft_sort_category b
        WHERE a.fft_entity_id=v_entityid
          AND a.users_id=b.user_id;

BEGIN

  SELECT CURRENT_TIMESTAMP INTO start_time FROM dual;

  OPEN c_id;
  LOOP
    FETCH c_id INTO v_category_id;
    EXIT WHEN c_id%NOTFOUND;

    BEGIN
       SELECT 1
         INTO v_exist
         FROM eon_users_category
        WHERE user_id=v_userid and category_id=v_category_id;

       EXCEPTION
       WHEN NO_DATA_FOUND THEN
            INSERT INTO eon_users_category (user_id, category_id)
                   VALUES (v_userid, v_category_id);
    END;

  END LOOP;

  CLOSE c_id;

  COMMIT;

  log_timestamp('proc_populate_sort_category', v_userid, 0, start_time);
END;
/
