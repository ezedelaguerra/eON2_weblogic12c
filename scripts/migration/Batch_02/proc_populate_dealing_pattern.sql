CREATE OR REPLACE PROCEDURE proc_populate_dealing_pattern(v_entityid_seller NUMBER,
                                                          v_entityid_buyer NUMBER,
                                                          v_userid_seller NUMBER,
                                                          v_userid_buyer NUMBER,
                                                          v_companyid_seller NUMBER,
                                                          v_companyid_buyer NUMBER)
IS

  v_id            NUMBER(9);
  v_rel_type      NUMBER(9);
  v_valid_from    CHAR(8);
  v_valid_to      CHAR(8);
  v_exist         NUMBER(1);
  start_time    TIMESTAMP;
  
  CURSOR c_dp IS
  SELECT id, fft_dealing_relation_type_id, valid_from, valid_to    
    FROM oldeon.fft_dealing_pattern
  WHERE fft_entity_id_seller=v_entityid_seller
    AND fft_entity_id_buyer=v_entityid_buyer; 

BEGIN

  SELECT CURRENT_TIMESTAMP INTO start_time FROM dual;

    
  OPEN c_dp;
    LOOP
    FETCH c_dp INTO v_id, v_rel_type, v_valid_from, v_valid_to;
    EXIT WHEN C_DP%NOTFOUND;

    IF v_rel_type=10000 THEN --SELLER_BUYER

      BEGIN
       SELECT 1
         INTO v_exist
         FROM eon_user_dealing_pattern
        WHERE user_dealing_pattern_id_old=v_id;

       EXCEPTION
       WHEN NO_DATA_FOUND THEN
            INSERT INTO eon_user_dealing_pattern (user_dealing_pattern_id,
                        company_dealing_pattern_id,
                        user_01, user_02, dealing_pattern_relation_id, start_date,end_date, user_dealing_pattern_id_old)
            SELECT eon_user_dealing_pattern_seq.nextval,
                   (SELECT company_dealing_pattern_id
                     FROM eon_company_dealing_pattern
                    WHERE company_01=v_companyid_seller
                      AND company_02=v_companyid_buyer),
                   v_userid_seller , v_userid_buyer, v_rel_type, NVL(v_valid_from,'0'), v_valid_to, v_id
              FROM dual;
      END;

    ELSE    -- 10002-10004 BUYERADMIN-BUYER/SELLERADMIN-SELLER/CHOUAI-SELLER

      BEGIN
       SELECT 1 INTO v_exist
         FROM eon_admin_member
        WHERE admin_member_id_old=v_id;

       EXCEPTION
       WHEN NO_DATA_FOUND THEN
            INSERT INTO eon_admin_member (admin_member_id, admin_id, member_id, dealing_pattern_relation_id, start_date, end_date, admin_member_id_old)
            SELECT eon_admin_member_seq.nextval, v_userid_seller , v_userid_buyer, v_rel_type, NVL(v_valid_from,'0'), v_valid_to, v_id
              FROM dual;
      END;

    END IF;
    
    END LOOP;
  CLOSE c_dp;

COMMIT;

log_timestamp('proc_populate_dealing_pattern', v_userid_seller, v_userid_buyer, start_time);

END;
/
