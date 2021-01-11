CREATE OR REPLACE PROCEDURE proc_populate_order_item_v (v_entityid_seller IN NUMBER,
                                               v_entityid_buyer IN NUMBER,
                                               v_userid_seller IN NUMBER,
                                               v_userid_buyer IN NUMBER)
IS

  v_order_item_id           NUMBER(9);
  v_delivery_date           CHAR(8);
  v_sku_id_old              NUMBER(9);
  v_sku_id                  NUMBER(9);

  v_sos_flag                CHAR(1);
  v_baos_flag               CHAR(1);

  start_time    TIMESTAMP;

  CURSOR c_oi IS
    SELECT sku.sku_id, sku.sku_id_old, ordr.delivery_date, oi.order_item_id
      FROM eon_order ordr,
           eon_order_item oi,
           eon_sku sku
     WHERE
           sku.sku_id = oi.sku_id AND
           sku.seller_id = v_userid_seller AND
           ordr.order_id = oi.order_id AND
           ordr.buyer_id = v_userid_buyer;

BEGIN

  SELECT CURRENT_TIMESTAMP INTO start_time FROM dual;

  OPEN c_oi;
  LOOP
  FETCH c_oi INTO v_sku_id, v_sku_id_old, v_delivery_date, v_order_item_id;
  EXIT WHEN C_OI%NOTFOUND;

  BEGIN

     SELECT COALESCE(vs_ps.flag, '1')
     INTO   v_sos_flag
      FROM
        (
         SELECT max(delivery_date) as max_date
         FROM oldeon.fft_visibility_buyer_ps
         WHERE delivery_date <= v_delivery_date and buyer_entity_id = v_entityid_buyer and seller_entity_id = v_entityid_seller
        ) yyyymmdd,
        oldeon.fft_visibility_buyer_ps vb_ps,
        oldeon.fft_visibility_sku_ps vs_ps

      WHERE
        vb_ps.delivery_date = yyyymmdd.max_date and
        vb_ps.buyer_entity_id = v_entityid_buyer and
        vb_ps.seller_entity_id = v_entityid_seller and
        vb_ps.id = vs_ps.fft_vis_buyer_ps_id and
        vs_ps.fft_sku_id=v_sku_id_old;

      EXCEPTION
              WHEN NO_DATA_FOUND THEN
              v_sos_flag := '1';
   END;


   BEGIN

     SELECT COALESCE(vs_ba.flag, '1')
     INTO   v_baos_flag
      FROM
        (
         SELECT max(delivery_date) as max_date
         FROM oldeon.fft_visibility_buyer_ba a, oldeon.fft_visibility_seller_ba b
         WHERE a.id = b.fft_vis_buyer_ba_id and
               a.delivery_date <= v_delivery_date and
               a.buyer_entity_id = v_entityid_buyer and
               b.seller_entity_id = v_entityid_seller
        ) yyyymmdd,
        oldeon.fft_visibility_buyer_ba vb_ba,
        oldeon.fft_visibility_sku_ba vs_ba

      WHERE
        vb_ba.delivery_date = yyyymmdd.max_date and
        buyer_entity_id = v_entityid_buyer and
        vb_ba.id = vs_ba.fft_vis_buyer_ba_id and
        vs_ba.fft_sku_id= v_sku_id_old;

      EXCEPTION
              WHEN NO_DATA_FOUND THEN
              v_baos_flag := '1';
   END;

      UPDATE eon_order_item
      SET sos_vis_flag = v_sos_flag,
          baos_vis_flag = v_baos_flag
      WHERE
          order_item_id = v_order_item_id;


  END LOOP;
  CLOSE c_oi;

COMMIT;

log_timestamp('proc_populate_order_item_v', v_userid_seller, v_userid_buyer, start_time);

END;
/