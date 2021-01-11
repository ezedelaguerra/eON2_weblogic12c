CREATE OR REPLACE PROCEDURE proc_populate_order_item(v_entityid_seller IN NUMBER, 
                                               v_entityid_buyer IN NUMBER,
                                               v_userid_seller IN NUMBER,
                                               v_userid_buyer IN NUMBER)
IS

  v_order_id                NUMBER(9);
  v_delivery_date           CHAR(8);
  v_order_id_old            NUMBER(9);
  v_exists                  NUMBER(1);
  v_exists2                 NUMBER(1);
  c_order                   SYS_REFCURSOR;
  v_sku_id                  NUMBER(9);
  v_sku_id_old              NUMBER(9);
  v_valid_from              CHAR(8);
  v_valid_to                CHAR(8);
  start_time    TIMESTAMP;

  CURSOR c_sku IS
    SELECT sku_id, sku_id_old, b.valid_from, b.valid_to
      FROM eon_sku a,
           oldeon.fft_sku b
     WHERE seller_id=v_userid_seller
       AND a.sku_id_old=b.id;

BEGIN

  SELECT CURRENT_TIMESTAMP INTO start_time FROM dual;

  OPEN c_sku;
  LOOP
  FETCH c_sku INTO v_sku_id, v_sku_id_old, v_valid_from, v_valid_to;
  EXIT WHEN C_SKU%NOTFOUND;

    OPEN c_order FOR
       SELECT order_id, delivery_date, order_id_old
         FROM eon_order
        WHERE buyer_id=v_userid_buyer
          AND seller_id=v_userid_seller
          AND delivery_date between v_valid_from and v_valid_to;
    
    LOOP
    FETCH c_order INTO v_order_id, v_delivery_date, v_order_id_old;
    EXIT WHEN C_ORDER%NOTFOUND;
      
      
       BEGIN  
                SELECT 1
                  INTO v_exists2
                  FROM oldeon.fft_order_item
                 WHERE fft_order_id=v_order_id_old
                   AND fft_sku_id=v_sku_id_old;
                   
                EXCEPTION
                  WHEN NO_DATA_FOUND THEN
                       NULL;
       END;
                 
              IF v_exists2=1 THEN
                 INSERT INTO eon_order_item (order_item_id, order_id, sku_id, --update_by_users_id,
                                             update_timestamp, quantity, total_quantity, unit_price,
                                             sku_max_limit, 
                                             sos_vis_flag, 
                                             baos_vis_flag, 
                                             order_item_id_old)
                      SELECT eon_order_item_seq.nextval, v_order_id, v_sku_id,  --update_by_users_id,
                             update_timestamp, quantity, total_quantity, unit_price, 
                             1,
                             NVL((SELECT CASE  WHEN a.id is not null then '0'
                                               WHEN a.id is null then '1'
                                               END 
                                    FROM oldeon.fft_visibility_buyer_ps a,
                                         oldeon.fft_visibility_sku_ps b
                                   WHERE a.seller_entity_id=v_entityid_seller
                                     AND a.buyer_entity_id=v_entityid_buyer
                                     AND a.id=b.fft_vis_buyer_ps_id
                                     AND b.fft_sku_id=v_sku_id_old),1), --sos_vis_flag
                             NVL((SELECT CASE  WHEN a.id is not null then '0'
                                               WHEN a.id is null then '1'
                                               END 
                                    FROM oldeon.fft_visibility_buyer_ba a,
                                         oldeon.fft_visibility_sku_ba b
                                   WHERE a.buyer_entity_id=v_entityid_buyer
                                     AND a.id=b.fft_vis_buyer_ba_id
                                     AND b.fft_sku_id=v_sku_id_old),1), --baos_vis_flag
                             id
                        FROM oldeon.fft_order_item
                       WHERE fft_order_id=v_order_id_old
                         AND fft_sku_id=v_sku_id_old;
                         
              ELSE                         
                 INSERT INTO eon_order_item (order_item_id, order_id, sku_id, 
                                             sos_vis_flag, 
                                             baos_vis_flag)
                      SELECT eon_order_item_seq.nextval, v_order_id, v_sku_id, 
                             NVL((SELECT CASE  WHEN a.id is not null then '0'
                                           WHEN a.id is null then '1'
                                    END 
                             FROM oldeon.fft_visibility_buyer_ps a,
                                  oldeon.fft_visibility_sku_ps b
                             WHERE a.seller_entity_id=v_entityid_seller
                               AND a.buyer_entity_id=v_entityid_buyer
                               AND a.id=b.fft_vis_buyer_ps_id
                               AND b.fft_sku_id=v_sku_id_old),1),
                             NVL((SELECT CASE  WHEN a.id is not null then '0'
                                           WHEN a.id is null then '1'
                                    END 
                             FROM oldeon.fft_visibility_buyer_ba a,
                                  oldeon.fft_visibility_sku_ba b
                             WHERE a.buyer_entity_id=v_entityid_buyer
                               AND a.id=b.fft_vis_buyer_ba_id
                               AND b.fft_sku_id=v_sku_id_old),1)
                        FROM dual;       
                        
              END IF;
              
              COMMIT;
              
 
    END LOOP;
   
    CLOSE c_order;
    
  END LOOP;
  CLOSE c_sku;    
 
  log_timestamp('proc_populate_order_item', v_userid_seller, v_userid_buyer, start_time);
  
END;