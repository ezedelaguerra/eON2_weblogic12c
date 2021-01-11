CREATE OR REPLACE PROCEDURE proc_populate_users(p_username IN VARCHAR2,
                                                p_password IN VARCHAR2,
                                                p_company_id IN NUMBER)
IS

  v_entity_id           NUMBER(9);
  v_userid_old          NUMBER(9);
  v_exists              NUMBER(1);
  v_entity_id_seller    NUMBER(9);
  v_entity_id_buyer     NUMBER(9);
  v_relation_type_id    NUMBER(9);
  v_role_id             NUMBER(1);
  start_time            TIMESTAMP;

BEGIN
  SELECT CURRENT_TIMESTAMP INTO start_time FROM dual;
  
  -- get entity id of username
  SELECT c.id, a.id
    INTO v_entity_id, v_userid_old
    FROM oldeon.users a,
         oldeon.fft_entity_users b,
         oldeon.fft_entity c
   WHERE a.username=p_username
     AND a.id =b.users_id
     AND b.fft_entity_id=c.id;

  BEGIN

    --check if user already migrated in eon_users
    SELECT 1
      INTO v_exists
      FROM eon_users
     WHERE user_id_old=v_entity_id;

    EXCEPTION
           WHEN NO_DATA_FOUND then null;

             BEGIN
               SELECT a.fft_entity_id_seller, a.fft_entity_id_buyer, a.fft_dealing_relation_type_id
                 INTO v_entity_id_seller, v_entity_id_buyer, v_relation_type_id
                 FROM oldeon.fft_dealing_pattern a,
                      oldeon.fft_dealing_relation_type b
                WHERE (a.fft_entity_id_seller=v_entity_id or a.fft_entity_id_buyer=v_entity_id)
                  AND a.fft_dealing_relation_type_id=b.id
                  AND rownum=1;

              --when no role id, will not insert in eon_users
              EXCEPTION
                  WHEN NO_DATA_FOUND then null;

             END;

             --get role id
             IF v_entity_id_seller=v_entity_id THEN
                IF v_relation_type_id=10000 THEN
                   v_role_id:=1; ----SELLER
                ELSIF v_relation_type_id=10002 THEN
                   v_role_id:=4; --BUYER_ADMIN
                ELSIF v_relation_type_id=10003 THEN
                   v_role_id:=2; --SELLERADMIN
                ELSE
                   v_role_id:=6; --CHOUAI
                END IF;
             ELSIF v_entity_id_buyer=v_entity_id THEN
                IF v_relation_type_id in (10000,10002) THEN
                   v_role_id:=3; --BUYER
                ELSE
                   v_role_id:=1; --SELLER
                END IF;
             ELSE
                v_role_id := NULL;
             END IF;

             IF v_role_id is not null THEN
                INSERT INTO eon_users (user_id, username, password, name, shortname,
                                       company_id, role_id, date_last_login, user_id_old, use_bms)
                     SELECT eon_users_seq.nextval, c.username, p_password, a.long_name, a.short_name,
                            p_company_id, v_role_id, c.last_login, a.id, '0'
                       FROM oldeon.fft_entity a,
                            oldeon.fft_entity_users b,
                            oldeon.users c
                      WHERE a.id=v_entity_id
                        AND a.id=b.fft_entity_id
                        AND b.users_id=c.id
                        AND rownum=1;
             END IF;


  END;

  COMMIT;
  
  log_timestamp('proc_populate_users', p_username, v_role_id, start_time);

END;
/