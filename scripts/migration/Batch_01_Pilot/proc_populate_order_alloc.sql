CREATE OR REPLACE PROCEDURE proc_populate_order_alloc(v_entityid_seller IN NUMBER,
                                               v_entityid_buyer IN NUMBER,
                                               v_userid_seller IN NUMBER,
                                               v_userid_buyer IN NUMBER)
IS

	start_time    TIMESTAMP;
--ASSUMING ORDER AND SKU ALREADY LOADED FOR ALL BUYERS UNDER ONE SELLER

BEGIN

    SELECT CURRENT_TIMESTAMP INTO start_time FROM dual;

	INSERT INTO eon_order_allocation (
        order_allocation_id,
        order_id,
        sku_id,
        --updated_by,
        update_timestamp,
        quantity,
        --unit_price,
        order_allocation_id_old)

	select
	 eon_order_allocation_id_seq.nextval,
	 ordr.order_id,
	 sku.sku_id,
	 alloc.update_timestamp,
	 alloc.quantity,
	 alloc.id

	from
	 eon_order ordr,
	 (
      select oa.id, oa.quantity, oa.update_timestamp, oa.fft_sku_id,  oa.fft_order_id
      from oldeon.fft_order_allocations oa, oldeon.fft_sku s, oldeon.fft_order oo
      where
      oo.id = oa.fft_order_id and
      oa.fft_sku_id = s.id and
      s.fft_entity_id_seller = v_entityid_seller and
      oo.fft_entity_id_buyer = v_entityid_buyer and
      oa.status is null
     ) alloc,
	 eon_sku sku

	where
	 ordr.order_id_old = alloc.fft_order_id and
	 sku.sku_id_old = alloc.fft_sku_id and
	 ordr.seller_id = sku.seller_id and
	 ordr.buyer_id = v_userid_buyer and
	 sku.seller_id = v_userid_seller;

  COMMIT;

  log_timestamp('proc_populate_order_alloc', v_userid_seller, v_userid_buyer, start_time);

END;
/
