CREATE OR REPLACE PROCEDURE proc_populate_order (v_entityid_seller IN NUMBER,
                                                 v_entityid_buyer IN NUMBER,
                                                 v_userid_seller IN NUMBER,
                                                 v_userid_buyer IN NUMBER)
IS

  v_order_id                    NUMBER(9);
  v_fft_buyer                   NUMBER(9);
  v_del_date                    CHAR(8 CHAR);
  v_fft_seller                  NUMBER(9);
  v_create_ts                   TIMESTAMP(6);
  v_create_users_id             NUMBER(9);
  v_update_ts                   TIMESTAMP(6);
  v_update_users_id             NUMBER(9);
  v_order_id_old                NUMBER(9);
  v_exists                      NUMBER(1);
  c_finalized                   SYS_REFCURSOR;
  v_order_published_by          NUMBER(9);
  v_order_finalized_by          NUMBER(9);
  v_allocation_published_by     NUMBER(9);
  v_allocation_finalized_by     NUMBER(9);
  v_billing_finalized_by        NUMBER(9);
  v_sheet_type_id               NUMBER(9);
  start_time    TIMESTAMP;


BEGIN

  SELECT CURRENT_TIMESTAMP INTO start_time FROM dual;
  
  -- update invalid dates
  UPDATE  oldeon.fft_sku sku SET sku.valid_to = to_char (to_date(sku.valid_to+1, 'yyyymmdd') - 1, 'yyyymmdd')
  WHERE sku.fft_entity_id_seller = v_entityid_seller AND sku.valid_to is not null AND sku.valid_to like '%00';


  --include dates that has changes on skus

  INSERT INTO eon_order (
        order_id,
        buyer_id,
        delivery_date,
        seller_id,
        order_saved_by,
        order_published_by,
        order_finalized_by,
        allocation_saved_by,
        allocation_published_by,
        allocation_finalized_by,
        billing_finalized_by,
        date_created,
        --created_by,
        date_updated,
        --updated_by,
        order_id_old)

  select
    eon_order_id_seq.nextval,
    v_userid_buyer,
    ordr.delivery_date,
    v_userid_seller,
    v_userid_seller,
    product.seller_id,
    seller_order.seller_id,
    seller_alloc.seller_id,
    seller_alloc.seller_id,
    buyer_receive.seller_id,
    seller_billing.seller_id,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    o.id

  from
	 (  (select distinct valid_from as delivery_date from oldeon.fft_sku sku
				where sku.fft_entity_id_seller = v_entityid_seller
		union
		select distinct to_char (to_date(sku.valid_to, 'yyyymmdd') + 1, 'yyyymmdd') as delivery_date  from oldeon.fft_sku sku
				  where sku.fft_entity_id_seller = v_entityid_seller and sku.valid_to is not null
		union
		select distinct vs.delivery_date from oldeon.fft_visibility_buyer_ps vs
				where vs.seller_entity_id = v_entityid_seller
		union
		select distinct ordr.delivery_date from oldeon.fft_order ordr, oldeon.fft_finalized f
				where f.DELIVERY_DATE = ordr.DELIVERY_DATE
					and ordr.fft_entity_id_buyer=v_entityid_buyer
					and f.finalized_with_entity_id = v_entityid_buyer
					and f.FINALIZED_BY_ENTITY_ID = v_entityid_seller
					and f.fft_sheet_type_id=10002
		)
	 )ordr

	left outer join
			oldeon.fft_order o on o.delivery_date = ordr.delivery_date and o.fft_entity_id_buyer = v_entityid_buyer

    left outer join
            (SELECT distinct delivery_date, v_userid_seller as seller_id
            FROM oldeon.fft_finalized
            WHERE finalized_by_entity_id=v_entityid_seller AND finalized_with_entity_id=v_entityid_buyer
            AND fft_sheet_type_id=10001
            )seller_order on seller_order.delivery_date = ordr.delivery_date

    left outer join
            (SELECT distinct delivery_date, v_userid_seller as seller_id
            FROM oldeon.fft_finalized
            WHERE finalized_by_entity_id=v_entityid_seller AND finalized_with_entity_id=v_entityid_buyer
            AND fft_sheet_type_id=10002
            )product on product.delivery_date = ordr.delivery_date

    left outer join
            (SELECT distinct delivery_date, v_userid_seller as seller_id
            FROM oldeon.fft_finalized
            WHERE finalized_by_entity_id=v_entityid_seller AND finalized_with_entity_id=v_entityid_buyer
            AND fft_sheet_type_id=10003
            )seller_alloc on seller_alloc.delivery_date = ordr.delivery_date

    left outer join
            (SELECT distinct delivery_date, v_userid_seller as seller_id
            FROM oldeon.fft_finalized
            WHERE finalized_by_entity_id=v_entityid_buyer AND finalized_with_entity_id=v_entityid_seller
            AND fft_sheet_type_id=10004
            )buyer_receive on buyer_receive.delivery_date = ordr.delivery_date

    left outer join
            (SELECT distinct delivery_date, v_userid_seller as seller_id
            FROM oldeon.fft_finalized
            WHERE finalized_by_entity_id=v_entityid_seller AND finalized_with_entity_id=v_entityid_buyer
            AND fft_sheet_type_id=10005
            )seller_billing    on seller_billing.delivery_date = ordr.delivery_date;

    COMMIT;

  log_timestamp('proc_populate_order', v_userid_seller, v_userid_buyer, start_time);

END;
/
