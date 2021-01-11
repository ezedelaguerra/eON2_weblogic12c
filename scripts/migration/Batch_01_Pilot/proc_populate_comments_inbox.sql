CREATE OR REPLACE PROCEDURE proc_populate_comments_inbox (v_entityid_seller IN NUMBER,
                                               v_entityid_buyer IN NUMBER,
                                               v_userid_seller IN NUMBER,
                                               v_userid_buyer IN NUMBER)
IS

  v_id      NUMBER(9);
  v_exist   NUMBER(9);
  start_time    TIMESTAMP;

  CURSOR c_id IS
    SELECT id
      FROM oldeon.fft_comment
     WHERE fft_entity_id_buyer=v_entityid_buyer
       AND fft_entity_id_seller=v_entityid_seller;

BEGIN

  SELECT CURRENT_TIMESTAMP INTO start_time FROM dual;

  OPEN c_id;
  LOOP
    FETCH c_id INTO v_id;
    EXIT WHEN c_id%NOTFOUND;

  BEGIN
       SELECT 1
         INTO v_exist
         FROM eon_comments_inbox
        WHERE comments_inbox_id_old=v_id;

       EXCEPTION
       WHEN NO_DATA_FOUND THEN
            INSERT INTO eon_comments_inbox (comments_inbox_id, sender_entity_id, recipient_entity_id, received_date,
                                            comment_subject, comment_message, open_status, create_timestamp, create_user_id,
                                            comments_inbox_id_old)
       SELECT eon_comments_inbox_id_seq.nextval,  v_userid_buyer, v_userid_seller, to_date(received_date,'YYYYMMDD'),  comment_title,
              comment_message, open_status, create_timestamp, v_userid_buyer, id
         FROM oldeon.fft_comment
        WHERE id=v_id;

  END;


  END LOOP;

  CLOSE c_id;

  COMMIT;

  log_timestamp('proc_populate_comments_inbox', v_userid_seller, v_userid_buyer, start_time);

END;
/
