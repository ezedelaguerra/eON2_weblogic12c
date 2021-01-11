CREATE OR REPLACE PROCEDURE proc_populate_order_received(v_entityid_seller IN NUMBER,
                                               v_entityid_buyer IN NUMBER,
                                               v_userid_seller IN NUMBER,
                                               v_userid_buyer IN NUMBER)
IS
    start_time    TIMESTAMP;
--ASSUMING ORDER AND SKU ALREADY LOADED FOR ALL BUYERS UNDER ONE SELLER

BEGIN

    SELECT CURRENT_TIMESTAMP INTO start_time FROM dual;
	
	INSERT INTO eon_order_received (
        order_received_id,
        order_id,
        sku_id,
        --updated_by,
        update_timestamp,
        quantity,
        --unit_price,
        order_received_id_old)

    select 
     EON_ORDER_RECEIVED_SEQ.nextval,
     ordr.order_id,
     sku.sku_id,
     receive.UPDATE_TIMESTAMP,
     receive.QUANTITY,
     receive.id

    from
     eon_order ordr,
     oldeon.fft_order_received receive,
     eon_sku sku

    where
     ordr.buyer_id = v_userid_buyer and
     sku.seller_id = v_userid_seller and
     ordr.order_id_old = receive.fft_order_id and
     sku.sku_id_old = receive.fft_sku_id;
	
  COMMIT;

  log_timestamp('proc_populate_order_received', v_userid_seller, v_userid_buyer, start_time);
  
END;
/