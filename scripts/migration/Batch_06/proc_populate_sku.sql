CREATE OR REPLACE PROCEDURE proc_populate_sku (v_entityid_seller IN NUMBER,
                                               v_userid_seller IN NUMBER,
                                               v_company_id IN NUMBER)
IS

  start_time    TIMESTAMP;

BEGIN

SELECT CURRENT_TIMESTAMP INTO start_time FROM dual;

INSERT INTO eon_sku (sku_id,
				   sku_name,
				   sheet_type_id,
				   sku_category_id,
				   location,
				   market,
				   grade,
				   class,
				   price1,
				   price2,
				   price_without_tax,
				   package_quantity,
				   package_type,
				   external_sku_id,
				   order_unit_id,
				   sku_id_old,
				   sku_group_id,
				   seller_id)

select eon_sku_id_seq.nextval as seq, sku.*, sku_group.sku_group_id, v_userid_seller
from
(
	SELECT
           b.l_descr as name, --sku_name
           CASE
             WHEN a.fft_sheet_type_id=10002 THEN 10001
             ELSE a.fft_sheet_type_id
             END as sheet_type, --sheet_type_id
           CASE
             WHEN fft_sku_category_id=10000 THEN 1 --Fruits
             WHEN fft_sku_category_id=10001 THEN 2 --Vegetables
             WHEN fft_sku_category_id=10002 THEN 3 --FreshFish/Fish
            END as cat_id, --sku_category_id
          (SELECT meta_value FROM oldeon.fft_sku_meta_info WHERE fft_sku_id=a.id AND meta_type_id=10000) as loc, --location/area_of_prod
          (SELECT meta_value FROM oldeon.fft_sku_meta_info WHERE fft_sku_id=a.id AND meta_type_id=10008) as mar, --market/market_condition
          (SELECT meta_value FROM oldeon.fft_sku_meta_info WHERE fft_sku_id=a.id AND meta_type_id=10001) as grd, --grade/class_level_1
          (SELECT meta_value FROM oldeon.fft_sku_meta_info WHERE fft_sku_id=a.id AND meta_type_id=10002) as cla, --class/class_level_2
          (SELECT TO_NUMBER(TO_SINGLE_BYTE(meta_value)) FROM oldeon.fft_sku_meta_info WHERE fft_sku_id=a.id AND meta_type_id=10021) as pr1, --price1/alt_price_1
          (SELECT TO_NUMBER(TO_SINGLE_BYTE(meta_value)) FROM oldeon.fft_sku_meta_info WHERE fft_sku_id=a.id AND meta_type_id=10022) as pr2, --price2/alt_price_2
          NVL((SELECT TO_NUMBER(TO_SINGLE_BYTE(meta_value)) FROM oldeon.fft_sku_meta_info WHERE fft_sku_id=a.id AND meta_type_id=10011), 0) as pnt, --price_without_tax/price_no_tax
          (SELECT TO_NUMBER(TO_SINGLE_BYTE(meta_value)) FROM oldeon.fft_sku_meta_info WHERE fft_sku_id=a.id AND meta_type_id=10003) as pqn, --package_quantity/eon_pkg_quan
          (SELECT meta_value FROM oldeon.fft_sku_meta_info WHERE fft_sku_id=a.id AND meta_type_id=10009) as pty, --package_type/packing_type
		  (SELECT meta_value FROM oldeon.fft_sku_meta_info WHERE fft_sku_id=a.id AND meta_type_id=10020) as ext_sku_id, --external_sku_id		  
          (SELECT
           CASE
             WHEN meta_value='Piece' AND a.fft_sku_category_id=10000 THEN 1
             WHEN (meta_value='Case' OR meta_value='ƒP�[ƒX') AND a.fft_sku_category_id=10000 THEN 2
             WHEN meta_value='Piece' AND a.fft_sku_category_id=10001 THEN 3
             WHEN (meta_value='Case' OR meta_value='ƒP�[ƒX') AND a.fft_sku_category_id=10001 THEN 4
             WHEN meta_value='Piece' AND a.fft_sku_category_id=10002 THEN 5
             WHEN meta_value='Kilos' AND a.fft_sku_category_id=10002 THEN 6
           END as uom

           FROM oldeon.fft_sku_meta_info WHERE fft_sku_id=a.id AND meta_type_id=10005) as uom,
          a.id as sku_id
      FROM oldeon.fft_sku a,
           oldeon.fft_sku_i18n b

     WHERE a.fft_entity_id_seller=v_entityid_seller
       AND a.fft_sheet_type_id NOT IN (10008,10014,10016,10015)
       AND a.id=b.fft_sku_id
)sku
left join oldeon.fft_sku_meta_info meta on sku.sku_id = meta.fft_sku_id
left join (
	select max(SKU_GROUP_ID) as sku_group_id, description, sku_category_id, seller_id 
	from eon_sku_group 
	where seller_id = v_userid_seller
	group by description, sku_category_id, seller_id
	) sku_group on meta.meta_value = sku_group.description
    and sku.cat_id = sku_group.sku_category_id

where meta.meta_type_id=10019
and sku.sku_id not in ('1167903','1167904','1167905');

COMMIT;

  log_timestamp('proc_populate_sku', v_userid_seller, 0, start_time);

END;
/
