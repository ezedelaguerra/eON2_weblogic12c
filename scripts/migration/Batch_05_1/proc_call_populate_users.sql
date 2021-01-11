CREATE OR REPLACE PROCEDURE proc_call_populate_users
IS

  v_username    VARCHAR2(50);
  v_password    VARCHAR2(32);
  v_company_id  NUMBER(9);
  start_time    TIMESTAMP;

  CURSOR c_user IS
    SELECT a.username, a.password, b.company_id
      FROM migration_user a,
           eon_company b
     WHERE migration_status = 0 --to_char(migration_date,'MMDDYY')=to_char(sysdate,'MMDDYY')
       AND a.company_name=b.company_name
     ORDER BY a.username;

BEGIN
  SELECT CURRENT_TIMESTAMP INTO start_time FROM dual;

  OPEN c_user;
    LOOP
    FETCH c_user INTO v_username, v_password, v_company_id;
    EXIT WHEN C_USER%NOTFOUND;

    proc_populate_users (v_username,v_password, v_company_id);
	
	UPDATE migration_user 
	SET migration_status = 1
	WHERE username = v_username;
	
	COMMIT;

    END LOOP;
  CLOSE c_user;

  log_timestamp('proc_call_populate_users', v_username, ' ', start_time);

END;
/
