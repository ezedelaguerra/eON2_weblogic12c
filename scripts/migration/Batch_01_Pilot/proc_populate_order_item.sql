CREATE OR REPLACE PROCEDURE proc_populate_order_item (v_entityid_seller IN NUMBER,
                                               v_entityid_buyer IN NUMBER,
                                               v_userid_seller IN NUMBER,
                                               v_userid_buyer IN NUMBER)
IS

	start_time    TIMESTAMP;

BEGIN

	SELECT CURRENT_TIMESTAMP INTO start_time FROM dual;

----order item

	INSERT INTO eon_order_item (
	    order_item_id,
	    order_id,
	    sku_id,
	    update_timestamp,
	    quantity,
	    --total_quantity,
	    --unit_price,
	    --sku_max_limit,
	    --sos_vis_flag,
	    --baos_vis_flag,
	    order_item_id_old)

	select
           eon_order_item_seq.nextval,
           order_sku.ORDER_ID,
           order_sku.sku_id,
           oi.UPDATE_TIMESTAMP,
           oi.QUANTITY,

--            (NVL((SELECT distinct FLAG
--                 FROM oldeon.fft_visibility_buyer_ps a,
--                      oldeon.fft_visibility_sku_ps b
--                 WHERE a.seller_entity_id=v_entityid_seller
--                   AND a.buyer_entity_id=v_entityid_buyer
--                   AND a.id=b.fft_vis_buyer_ps_id
--                   AND b.fft_sku_id=order_sku.fft_sku_id),1)) as sos,

--            (NVL((SELECT distinct FLAG
--                 FROM oldeon.fft_visibility_buyer_ba a,
--                      oldeon.fft_visibility_sku_ba b
--                 WHERE a.buyer_entity_id=v_entityid_buyer
--                   AND a.id=b.fft_vis_buyer_ba_id
--                   AND b.fft_sku_id=order_sku.fft_sku_id),1)) as baos,

            oi.ID

    from
    (
		select ordr.ORDER_ID_OLD, ordr.ORDER_ID, ordr.DELIVERY_DATE, ordr.SELLER_ID, ordr.BUYER_ID, eonSKU.*
		from eon_order ordr,

        (
	        select * from
	        (
	        select oSku.id as old_sku_id, oSku.VALID_FROM as skuFrom, oSku.VALID_TO as skuTo, oMeta.meta_value, oMeta.VALID_FROM, oMeta.VALID_TO
	        from oldeon.fft_sku oSku, oldeon.fft_sku_meta_info oMeta
	        where
	        oSku.id = oMeta.fft_sku_id and
	        oMeta.meta_type_id=10019
	        AND oSku.FFT_ENTITY_ID_SELLER = v_entityid_seller
	        AND oSku.fft_sheet_type_id = 10002
	        ) old_sku

	        join

	        (
	        select sku.SKU_ID, sku.SKU_ID_OLD, grp.DESCRIPTION
	        from eon_sku sku, eon_sku_group grp
	        where
	        sku.SKU_GROUP_ID = grp.SKU_GROUP_ID
	        ) new_sku

	        on old_sku.old_sku_id = new_sku.sku_id_old and
	        meta_value = description
	    )eonSKU

		where
	    ordr.SELLER_ID = v_userid_seller and ordr.BUYER_ID = v_userid_buyer and
	    ordr.DELIVERY_DATE >= COALESCE(eonSKU.skuFrom, '00000000') and
	    ordr.DELIVERY_DATE <= COALESCE(eonSKU.skuTo, '99999999') and
	    ordr.DELIVERY_DATE >= COALESCE(eonSKU.VALID_FROM, '00000000') and
	    ordr.DELIVERY_DATE <= COALESCE(eonSKU.VALID_TO, '99999999')

        --and ordr.DELIVERY_DATE = '20100306' and
        --eonSKU.old_sku_id = 943314
    ) order_sku

    left outer join

    oldeon.fft_order_item oi on order_sku.old_sku_id = oi.fft_sku_id
                            and order_sku.order_id_old = oi.fft_order_id;

commit;

log_timestamp('proc_populate_order_item', v_userid_seller, v_userid_buyer, start_time);

END;
/
