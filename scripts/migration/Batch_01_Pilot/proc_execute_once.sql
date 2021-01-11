CREATE OR REPLACE PROCEDURE proc_execute_once
IS

  v_userid_seller       NUMBER(9);
  v_entityid_seller     NUMBER(9);
  v_companyid_seller    NUMBER(9);
  v_user1               VARCHAR2(32);
  v_role                VARCHAR2(32);
  start_time            TIMESTAMP;

  CURSOR c_o IS
    SELECT unique(user1),
           CASE WHEN substr(relation,1,(instr(relation,'-')-1)) = 'SELLER' THEN 'SELLERB'
                ELSE substr(relation,1,(instr(relation,'-')-1)) END
      FROM migration_dealing_pattern a
     WHERE migration_status = 0 --to_char(migration_date,'MMDDYY')=to_char(sysdate,'MMDDYY')
     ORDER BY 2 desc;
     --will show first seller

  CURSOR c_buyers IS
    SELECT unique(user2)
      FROM migration_dealing_pattern a
     WHERE migration_status = 0 --to_char(migration_date,'MMDDYY')=to_char(sysdate,'MMDDYY')
	 AND relation = 'SELLER-BUYER';

BEGIN
  SELECT CURRENT_TIMESTAMP INTO start_time FROM dual;

  proc_call_populate_users;
       --will call proc_populate_users
       --will populate all users for batch migration

  OPEN c_o;
    LOOP
    FETCH c_o INTO v_user1, v_role;
    EXIT WHEN C_O%NOTFOUND;

    BEGIN
      SELECT user_id, user_id_old, company_id
        INTO v_userid_seller, v_entityid_seller, v_companyid_seller
        FROM eon_users
       WHERE username=v_user1;
      EXCEPTION
        WHEN NO_DATA_FOUND THEN
             --DBMS_OUTPUT.PUT_LINE('USER1 INCORRECT, PLEASE CHECK IF CREATED IN EON_USERS');
             null;
    END;

    --user will only be SELLER/BUYERADMIN/SELLERADMIN
    IF v_role='SELLERB' THEN
	   
	   proc_populate_sort_category(v_entityid_seller, v_userid_seller);
       proc_populate_sku_group(v_entityid_seller, v_userid_seller);
       proc_populate_sort_sku_group(v_entityid_seller, v_userid_seller);
       --proc_populate_history_log(v_entityid_seller, v_userid_seller);
       proc_populate_sort_sku(v_entityid_seller, v_userid_seller);
       proc_populate_sku(v_entityid_seller, v_userid_seller, v_companyid_seller);
       proc_populate_sort_buyers(v_entityid_seller,v_userid_seller);
	   proc_populate_excel_settings(v_entityid_seller,v_userid_seller);
	   

    ELSIF v_role IN ('BUYERADMIN','SELLERADMIN') THEN
	   
	   proc_populate_sort_category(v_entityid_seller, v_userid_seller);
       proc_populate_sort_sku_group(v_entityid_seller, v_userid_seller);
       proc_populate_sort_sku(v_entityid_seller, v_userid_seller);
       proc_populate_sort_buyers_ad(v_entityid_seller,v_userid_seller);
	   proc_populate_excel_settings(v_entityid_seller,v_userid_seller);

    END IF;

    END LOOP;
  CLOSE c_o;


  OPEN c_buyers;
	LOOP
    FETCH c_buyers INTO v_user1;
    EXIT WHEN c_buyers%NOTFOUND;

	BEGIN
      SELECT user_id, user_id_old, company_id
        INTO v_userid_seller, v_entityid_seller, v_companyid_seller
        FROM eon_users
       WHERE username=v_user1;
      EXCEPTION
        WHEN NO_DATA_FOUND THEN
             --DBMS_OUTPUT.PUT_LINE('USER1 INCORRECT, PLEASE CHECK IF CREATED IN EON_USERS');
             null;
    END;
	
	proc_populate_sort_category(v_entityid_seller, v_userid_seller);
	proc_populate_sort_sku(v_entityid_seller, v_userid_seller);
	proc_populate_sort_sku_group(v_entityid_seller, v_userid_seller);
	proc_populate_excel_settings(v_entityid_seller,v_userid_seller);
    
    END LOOP;
  CLOSE c_buyers;
  
  log_timestamp('proc_execute_once', 0, 0, start_time);

END;
/