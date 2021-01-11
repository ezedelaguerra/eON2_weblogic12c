CREATE OR REPLACE PROCEDURE proc_populate_sku_group(v_entityid_seller NUMBER, v_userid_seller NUMBER)
IS

	start_time        TIMESTAMP;
	v_group_id_old    NUMBER(9);
	v_date            CHAR(8);
	c_group           SYS_REFCURSOR;

BEGIN
	SELECT CURRENT_TIMESTAMP INTO start_time FROM dual;

	INSERT INTO eon_sku_group (
		sku_group_id,
		orig_sku_group_id,
		seller_id,
		description,
		sku_category_id,
		start_date,
		end_date,
		sku_group_id_old)

        select
         eon_sku_group_id_seq.nextval,
		 eon_sku_group_id_seq.currval,
         skuGroupResult.*

        from
        (
    		select

    		 v_userid_seller,
    		 meta.meta_value,
    		 CASE
    			WHEN grp.fft_sku_category_id=10000 THEN 1
    			WHEN grp.fft_sku_category_id=10001 THEN 2
    			WHEN grp.fft_sku_category_id=10002 THEN 3
    		 END,
    		 NVL(meta.valid_from, '20040101'),
    		 meta.valid_to,
    		 grp.id

    		from
    		 oldeon.fft_sku sku,
    		 oldeon.fft_sku_group_members grp_mem,
    		 oldeon.fft_sku_group grp,
    		 oldeon.fft_sku_meta_info meta
    		where
    		 sku.id = grp_mem.fft_sku_id and
    		 grp.id = grp_mem.fft_sku_group_id and
    		 sku.id = meta.fft_sku_id and
    		 meta.meta_type_id = 10019 and
    		 sku.FFT_ENTITY_ID_SELLER = v_entityid_seller
			 group by grp.id, grp.descr, grp.fft_sku_category_id, meta.meta_value, meta.valid_from, meta.valid_to
        )skuGroupResult;

	COMMIT;

	INSERT INTO eon_sku_group (
		sku_group_id,
		orig_sku_group_id,
		seller_id,
		description,
		sku_category_id,
		start_date,
		sku_group_id_old)

		select
		 eon_sku_group_id_seq.nextval,
		 eon_sku_group_id_seq.currval,
		 v_userid_seller,
		 minusResult.descr,
		 minusResult.sku_category_id,
		 '20040101',
		 minusResult.sku_group_id_old

		from
			(
			select
			 grp.descr,
			 CASE
				WHEN grp.fft_sku_category_id=10000 THEN 1
				WHEN grp.fft_sku_category_id=10001 THEN 2
				WHEN grp.fft_sku_category_id=10002 THEN 3
			 END as sku_category_id,
			 grp.id as sku_group_id_old
			from
			 oldeon.fft_sku_group grp
			where
			 grp.FFT_ENTITY_ID_SELLER = v_entityid_seller and
			 (grp.status IS NULL OR grp.status = 0 )

			minus

			select description as descr, sku_category_id, sku_group_id_old
			from
			 eon_sku_group
			where
			 seller_id = v_userid_seller
			)minusResult;

	COMMIT;	
	
	OPEN c_group FOR
       select sku_group_id_old from
	    (select grp.sku_group_id_old, count(grp.sku_group_id_old) total 
		 from EON_SKU_GROUP grp
	     where grp.sku_group_id_old is not null  
		 and grp.seller_id = v_userid_seller
	     group by grp.sku_group_id_old
	    )
		where total > 1;
		  
	LOOP
    FETCH c_group INTO v_group_id_old;
    EXIT WHEN c_group%NOTFOUND;
			
		select min(nvl(end_date, '99999999')) into v_date
		from EON_SKU_GROUP 
		where sku_group_id_old = v_group_id_old;
		
		update EON_SKU_GROUP grp set orig_sku_group_id = 
			(select sku_group_id from EON_SKU_GROUP where end_date = v_date and sku_group_id_old = v_group_id_old and rownum = 1)
		where grp.sku_group_id_old = v_group_id_old;
	END LOOP;
	
	CLOSE c_group;
	
	COMMIT;

	log_timestamp('proc_populate_sku_group', v_userid_seller, 0, start_time);

END;
/
