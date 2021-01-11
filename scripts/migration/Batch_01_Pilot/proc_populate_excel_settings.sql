CREATE OR REPLACE PROCEDURE proc_populate_excel_settings (v_entityid IN NUMBER,
                                                          v_userid IN NUMBER)
IS

  start_time    TIMESTAMP;
  v_exist       NUMBER(1);

BEGIN

  SELECT CURRENT_TIMESTAMP INTO start_time FROM dual;
  
  BEGIN
       SELECT 1
         INTO v_exist
         FROM EON_EXCEL_SETTING
        WHERE user_id=v_userid;

       EXCEPTION
       WHEN NO_DATA_FOUND THEN
              INSERT INTO EON_EXCEL_SETTING (
					user_id,
					weekly_flag,
					date_option,
					seller_option,
					buyer_option,
					has_qty,
					price_flag,
					price_compute_option)
					
			  SELECT 
					v_userid,
					is_weekly_option,
					date_option,
					seller_option,
					buyer_option,
					quantity_option,
					price_option,		
					total_price_option
			  FROM
					oldeon.fft_excel_settings
			  WHERE
			        entity_id = v_entityid;

  END;
  
  
		
  COMMIT;

  log_timestamp('proc_populate_excel_settings', v_userid, 0, start_time);
END;
/
