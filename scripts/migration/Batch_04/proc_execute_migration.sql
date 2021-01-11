CREATE OR REPLACE PROCEDURE proc_execute_migration
IS

   v_user1               VARCHAR2(32);
   v_user2               VARCHAR2(32);
   v_relation            VARCHAR2(128);
   v_userid_seller       NUMBER(9);
   v_entityid_seller     NUMBER(9);
   v_userid_buyer        NUMBER(9);
   v_entityid_buyer      NUMBER(9);
   v_companyid_seller    NUMBER(9);
   v_companyid_buyer     NUMBER(9);
   v_role                VARCHAR2(32);
   start_time            TIMESTAMP;
   dp_start_time         TIMESTAMP;

  CURSOR c_dp IS
    SELECT a.user1, a.user2, a.relation
      FROM migration_dealing_pattern a
     WHERE migration_status = 0; --to_char(migration_date,'MMDDYY')=to_char(sysdate,'MMDDYY');
   

BEGIN 

  SELECT CURRENT_TIMESTAMP INTO start_time FROM dual;
       
  --will call one at a time user1-user2 and relation
  OPEN c_dp;
    LOOP
    FETCH c_dp INTO v_user1, v_user2, v_relation;
    EXIT WHEN C_DP%NOTFOUND;
    
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
    
      BEGIN
        SELECT user_id, user_id_old, company_id
          INTO v_userid_buyer, v_entityid_buyer, v_companyid_buyer
          FROM eon_users
         WHERE username=v_user2;
        EXCEPTION
         WHEN NO_DATA_FOUND THEN 
              --DBMS_OUTPUT.PUT_LINE('USER2 INCORRECT, PLEASE CHECK IF CREATED IN EON_USERS');
              null;
      END;
      
      SELECT CURRENT_TIMESTAMP INTO dp_start_time FROM dual;
      
      IF v_relation IN ('BUYERADMIN-BUYER', 'SELLERADMIN-SELLER') THEN
         proc_populate_dealing_pattern(v_entityid_seller,v_entityid_buyer, v_userid_seller, v_userid_buyer,v_companyid_seller,v_companyid_buyer);
      ELSE
         proc_populate_dealing_pattern(v_entityid_seller,v_entityid_buyer, v_userid_seller, v_userid_buyer,v_companyid_seller,v_companyid_buyer);
         --proc_populate_sort_sku_group(v_entityid_buyer, v_userid_buyer);
         proc_populate_comments_inbox(v_entityid_seller, v_entityid_buyer,  v_userid_seller, v_userid_buyer);
         --proc_populate_history_log(v_entityid_buyer, v_userid_buyer);
         --proc_populate_sort_sku(v_entityid_buyer, v_userid_buyer);
         proc_populate_order(v_entityid_seller, v_entityid_buyer,  v_userid_seller, v_userid_buyer);
         proc_populate_order_item (v_entityid_seller, v_entityid_buyer, v_userid_seller, v_userid_buyer);
         proc_populate_order_item_v (v_entityid_seller, v_entityid_buyer, v_userid_seller, v_userid_buyer);
         proc_populate_order_alloc(v_entityid_seller, v_entityid_buyer,  v_userid_seller, v_userid_buyer);
         proc_populate_order_received(v_entityid_seller, v_entityid_buyer,  v_userid_seller, v_userid_buyer);
         proc_populate_order_billing(v_entityid_seller, v_entityid_buyer,  v_userid_seller, v_userid_buyer);
      END IF;
      
    log_timestamp('migration_' || v_relation, v_user1, v_user2, dp_start_time);
	  
	UPDATE migration_dealing_pattern 
	SET migration_status = 1
	WHERE user1 = v_user1 AND user2 = v_user2;
	
	COMMIT;
  
    END LOOP; 
  CLOSE c_dp;    

  log_timestamp('proc_execute_migration', v_user1, v_user2, start_time);
END;
/