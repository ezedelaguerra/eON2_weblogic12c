CREATE OR REPLACE PROCEDURE proc_delete_dealingpattern
IS

   v_user1              VARCHAR2(32);
   v_user2              VARCHAR2(32);
   v_relation           VARCHAR2(128);
   v_dp_type             NUMBER(9);
   v_dp_id               NUMBER(9);
   v_dp_end_date         CHAR(8 BYTE);

  CURSOR c_dp IS
    SELECT a.user1, a.user2, a.relation, to_char(migration_date,'YYYYMMDD') as YYYYMMDD
      FROM migration_dealing_pattern a
     WHERE dp_delete_status = 0; --to_char(migration_date,'MMDDYY')=to_char(sysdate,'MMDDYY');
   

BEGIN 
  --will call one at a time user1-user2 and relation
  OPEN c_dp;
    LOOP
    FETCH c_dp INTO v_user1, v_user2, v_relation, v_dp_end_date;
    EXIT WHEN C_DP%NOTFOUND;
    
        IF v_relation = 'SELLER-BUYER' THEN
           v_dp_type:= 10000;
        ELSIF v_relation = 'BUYERADMIN-BUYER' THEN
           v_dp_type:= 10002;
        ELSIF v_relation = 'SELLERADMIN-SELLER' THEN
           v_dp_type:= 10003;
        END IF;
    
      BEGIN
        select id into v_dp_id
        from fft_dealing_pattern         
        where
         valid_to is null
         and fft_entity_id_seller=
                        (select fft_entity_id
                        from fft_entity_users
                        where users_id=(select id
                                        from users
                                        where username=v_user1))              
         and fft_entity_id_buyer=
                        (select fft_entity_id
                        from fft_entity_users
                        where users_id=(select id
                                        from users
                                        where username=v_user2))
         and fft_dealing_relation_type_id=v_dp_type;
        EXCEPTION
         WHEN NO_DATA_FOUND THEN 
              --DBMS_OUTPUT.PUT_LINE('USER1 INCORRECT, PLEASE CHECK IF CREATED IN EON_USERS');
              null;
      END;
    
      BEGIN
        update fft_dealing_pattern
        set valid_to = v_dp_end_date --to_date(v_dp_end_date, 'yyyymmdd') - 1
        where id = v_dp_id;    

        update  migration_dealing_pattern
        set fft_dealing_pattern_id = v_dp_id,
            dp_delete_status = '1'
        where user1 = v_user1
           and user2 = v_user2
           and relation = v_relation;
      END;
      
    COMMIT;
  
    END LOOP; 
  CLOSE c_dp;    

END;
/