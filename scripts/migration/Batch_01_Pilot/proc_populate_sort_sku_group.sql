CREATE OR REPLACE PROCEDURE proc_populate_sort_sku_group (v_entityid IN NUMBER,
                                                         v_userid IN NUMBER)
IS

  v_sku_group_id        NUMBER(9);
  v_sku_group_id_old    NUMBER(9);
  v_exist               NUMBER(1);
  v_sku_category_id     NUMBER(9);
  v_sorting             NUMBER(4);
  start_time    TIMESTAMP;

  CURSOR c_id IS
  SELECT a.fft_sku_group_id, b.sku_group_id, 
         CASE
             WHEN a.fft_sku_category_id=10000 THEN 1 --Fruits
             WHEN a.fft_sku_category_id=10001 THEN 2 --Vegetables
             WHEN a.fft_sku_category_id=10002 THEN 3 --FreshFish/Fish
         END as cat_id, a.sorting_order
    FROM oldeon.fft_sort_sku_groups a,
         eon_sku_group b
   WHERE a.fft_entity_id=v_entityid
     AND a.fft_sku_group_id=b.sku_group_id_old;

BEGIN

  SELECT CURRENT_TIMESTAMP INTO start_time FROM dual;

  OPEN c_id;
  LOOP
    FETCH c_id INTO v_sku_group_id_old, v_sku_group_id, v_sku_category_id, v_sorting;
    EXIT WHEN c_id%NOTFOUND;

    BEGIN
       SELECT 1
         INTO v_exist
         FROM eon_sort_sku_group
        WHERE user_id=v_userid
          AND sku_group_id=v_sku_group_id;
          --AND sku_group_id_old=v_sku_group_id_old;

       EXCEPTION
       WHEN NO_DATA_FOUND THEN
            INSERT INTO eon_sort_sku_group (user_id, sku_group_id, sku_category_id, sorting, user_id_old, sku_group_id_old)
                 SELECT v_userid, v_sku_group_id, v_sku_category_id, v_sorting, v_entityid, v_sku_group_id_old
                   FROM dual;
    END;

  END LOOP;

  CLOSE c_id;

  COMMIT;

  log_timestamp('proc_populate_sort_sku_group', v_userid, 0, start_time);
END;
/
