CREATE OR REPLACE PROCEDURE proc_populate_sort_sku(v_entityid IN NUMBER,
                                                   v_userid IN NUMBER)
IS

  v_column      VARCHAR2(64);
  v_sorting     NUMBER(4);
  v_exist       NUMBER(1);
  v_column_ids   VARCHAR2(256);
  start_time    TIMESTAMP;

  CURSOR c_id IS
    SELECT CASE
             WHEN b.fft_column_name='packaging.type' THEN
                  '110' --package type
             WHEN b.fft_column_name='market.condition' THEN
                  '105' --market name
             WHEN b.fft_column_name='alt.price2' THEN
                  '112' --price 2
             WHEN b.fft_column_name='area.production' THEN
                  '106' --home
             WHEN b.fft_column_name='alt.price1' THEN
                  '111' --price 1
             WHEN b.fft_column_name='price.no.tax' THEN
                  '113' --price wo tax
             WHEN b.fft_column_name='class1' THEN
                  '107' --grade
             WHEN b.fft_column_name='name' THEN
                  '104' --sku name
             WHEN b.fft_column_name='packaging.quantity' THEN
                  '109' --package quantity
             WHEN b.fft_column_name='uom' THEN
                  '115' --unit of order
             WHEN b.fft_column_name='price.with.tax' THEN
                  '114' --price w tax
             WHEN b.fft_column_name='seller' THEN
                  '102' --seller name
			 WHEN b.fft_column_name='externalId' THEN
                  '117' --external sku id
             WHEN b.fft_column_name='group' THEN
                  '103' --group name
             WHEN b.fft_column_name='class2' THEN
                  '108' --class name			 
			 ELSE b.fft_column_name
           END,
           b.sorting_order
      FROM oldeon.fft_entity_users a,
           oldeon.fft_sort_skus_by_attributes b
     WHERE a.fft_entity_id=v_entityid
       AND a.users_id=b.user_id
       order by sorting_order;

BEGIN

  SELECT CURRENT_TIMESTAMP INTO start_time FROM dual;
  OPEN c_id;
  LOOP
    FETCH c_id INTO v_column, v_sorting;
    EXIT WHEN c_id%NOTFOUND;
      IF v_column_ids IS NULL THEN
         v_column_ids:=v_column;
      ELSE IF v_column IS NOT NULL THEN
         v_column_ids:=v_column_ids||','||v_column;
      END IF;
      END IF;
  END LOOP;
  CLOSE c_id;

  BEGIN
       SELECT 1
         INTO v_exist
         FROM eon_sort_sku
        WHERE user_id=v_userid;

       EXCEPTION
       WHEN NO_DATA_FOUND THEN
	   IF v_column_ids IS NOT NULL THEN
            INSERT INTO eon_sort_sku (user_id, sku_column_ids)
                 SELECT v_userid,v_column_ids
                   FROM dual;
	   END IF;

  END;

  COMMIT;

  log_timestamp('proc_populate_sort_sku', v_userid, 0, start_time);
END;
/
