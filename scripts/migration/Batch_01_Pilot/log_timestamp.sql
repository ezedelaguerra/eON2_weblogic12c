CREATE OR REPLACE PROCEDURE log_timestamp (p_proc_name IN VARCHAR2,
                                           p_seller_id IN VARCHAR2,
                                           p_buyer_id IN VARCHAR2,
                                           p_start_time IN TIMESTAMP)
IS
    finish_time                 TIMESTAMP;
    process_time                INTERVAL DAY(3) TO SECOND(3);
    log_text                    VARCHAR2(32);
begin
 SELECT CURRENT_TIMESTAMP INTO finish_time FROM dual;
 
 SELECT finish_time - p_start_time 
  INTO process_time
 FROM dual;

 SELECT 
 EXTRACT(hour from process_time) || ':' ||
 EXTRACT(minute from process_time) || ':' || 
 EXTRACT(second from process_time) 
 INTO log_text FROM dual;

 INSERT INTO migration_log_audit(
	batch_number,
	procedure_name,
	parameter_01,
	parameter_02,
	time_start,
	time_finish,
	time_duration)	
	VALUES(
	(select max(batch_number) from migration_user),
	p_proc_name,
	p_seller_id,
	p_buyer_id,
	p_start_time,
	finish_time,
	process_time);
	
  COMMIT;
	
DBMS_OUTPUT.PUT_LINE(p_proc_name || ' [' || p_seller_id || '-' || p_buyer_id || '] ' || log_text);


end;
/
