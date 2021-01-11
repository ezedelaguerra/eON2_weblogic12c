spool 99_delete_old_data.log
conn oldeon/oldeon@&&var_dbname
SELECT CURRENT_TIMESTAMP FROM dual;

prompt The following are the tables involved in reducing the number of records:
prompt - FFT_SKU_META_INFO
prompt - FFT_SKU_GROUP_MEMBERS
prompt - FFT_VISIBILITY_SKU_PS
prompt - FFT_VISIBILITY_SKU_BA
prompt - FFT_VISIBILITY_BUYER_PS
prompt - FFT_VISIBILITY_SELLER_BA
prompt - FFT_VISIBILITY_BUYER_BA
prompt - FFT_SKU_I18N
prompt - FFT_DELETED_SKU
prompt - FFT_ORDER_VERSION
prompt - FFT_ORDER_ALLOCATIONS
prompt - FFT_ORDER_BILLING
prompt - FFT_ORDER_ITEM
prompt - FFT_ORDER_PROPOSED
prompt - FFT_ORDER_RECEIVED
prompt - FFT_ORDER
prompt - FFT_FINLAIZED
prompt - FFT_SKU
prompt
prompt Now starting deletion of records...

--FFT_SKU_META_INFO
DELETE
from
FFT_SKU_META_INFO 
where fft_sku_id in
(select id from fft_sku sku where sku.valid_to is not null and sku.valid_to < '20111101');
COMMIT;
prompt FFT_SKU_META_INFO Finished.
SELECT CURRENT_TIMESTAMP FROM dual;

--FFT_SKU_GROUP_MEMBERS
DELETE
from
FFT_SKU_GROUP_MEMBERS 
where fft_sku_id in
(select id from fft_sku sku where sku.valid_to is not null and sku.valid_to < '20111101');
COMMIT;
prompt FFT_SKU_GROUP_MEMBERS Finished.
SELECT CURRENT_TIMESTAMP FROM dual;

--FFT_VISIBILITY_SKU_PS
DELETE
from
FFT_VISIBILITY_SKU_PS 
where fft_sku_id in
(select id from fft_sku sku where sku.valid_to is not null and sku.valid_to < '20111101');
COMMIT;
prompt FFT_VISIBILITY_SKU_PS Finished.
SELECT CURRENT_TIMESTAMP FROM dual;

--FFT_VISIBILITY_SKU_BA
DELETE
from
FFT_VISIBILITY_SKU_BA 
where fft_sku_id in
(select id from fft_sku sku where sku.valid_to is not null and sku.valid_to < '20111101');
COMMIT;
prompt FFT_VISIBILITY_SKU_BA Finished.
SELECT CURRENT_TIMESTAMP FROM dual;

--FFT_VISIBILITY_BUYER_PS
DELETE
from
FFT_VISIBILITY_BUYER_PS visbuyerps
where visbuyerps.delivery_date < '20111101' and
visbuyerps.id not in (select FFT_VIS_BUYER_PS_ID from FFT_VISIBILITY_SKU_PS);
COMMIT;
prompt FFT_VISIBILITY_BUYER_PS Finished.
SELECT CURRENT_TIMESTAMP FROM dual;

--FFT_VISIBILITY_SELLER_BA
DELETE
from
FFT_VISIBILITY_SELLER_BA vissellerba
where vissellerba.FFT_VIS_BUYER_BA_ID in 
    (select ID
    from
    FFT_VISIBILITY_BUYER_BA visbuyerba
    where visbuyerba.delivery_date < '20111101' and
    visbuyerba.id not in (select FFT_VIS_BUYER_BA_ID from FFT_VISIBILITY_SKU_BA));
COMMIT;
prompt FFT_VISIBILITY_SELLER_BA Finished.
SELECT CURRENT_TIMESTAMP FROM dual;

--FFT_VISIBILITY_BUYER_BA
DELETE
from
FFT_VISIBILITY_BUYER_BA visbuyerba
where visbuyerba.delivery_date < '20111101' and
visbuyerba.id not in (select FFT_VIS_BUYER_BA_ID from FFT_VISIBILITY_SKU_BA);
COMMIT;
prompt FFT_VISIBILITY_BUYER_BA Finished.
SELECT CURRENT_TIMESTAMP FROM dual;

--FFT_SKU_I18N
DELETE
from
FFT_SKU_I18N
where fft_sku_id in
(select id from fft_sku sku where sku.valid_to is not null and sku.valid_to < '20111101');
COMMIT;
prompt FFT_SKU_I18N Finished.
SELECT CURRENT_TIMESTAMP FROM dual;

--FFT_DELETED_SKU
DELETE
from
FFT_DELETED_SKU
where fft_sku_id in
(select id from fft_sku sku where sku.valid_to is not null and sku.valid_to < '20111101');
COMMIT;
prompt FFT_DELETED_SKU Finished.
SELECT CURRENT_TIMESTAMP FROM dual;

--FFT_ORDER_VERSION
DELETE
from
FFT_ORDER_VERSION
where DELIVERY_DATE < '20111101';
COMMIT;
prompt FFT_ORDER_VERSION Finished.
SELECT CURRENT_TIMESTAMP FROM dual;

COMMIT;


-- script from Loreen

CREATE TABLE TEMP_FFT_ORDER
	(ID NUMBER(9),
	 DELIVERY_DATE CHAR(8));
	
INSERT INTO TEMP_FFT_ORDER
  SELECT ID, delivery_date
    FROM fft_order
   WHERE delivery_date < '20111101' OR delivery_date is null;
   
commit;   
   
DELETE FROM FFT_ORDER_ALLOCATIONS 
      WHERE fft_order_id IN (select id from TEMP_FFT_ORDER);
COMMIT;
prompt FFT_ORDER_ALLOCATIONS Finished.
SELECT CURRENT_TIMESTAMP FROM dual;

DELETE FROM FFT_ORDER_BILLING
      WHERE fft_order_id IN (select id from TEMP_FFT_ORDER);
COMMIT;
prompt FFT_ORDER_BILLING Finished.
SELECT CURRENT_TIMESTAMP FROM dual;

DELETE FROM FFT_ORDER_ITEM
      WHERE fft_order_id IN (select id from TEMP_FFT_ORDER);
COMMIT;
prompt FFT_ORDER_ITEM Finished.
SELECT CURRENT_TIMESTAMP FROM dual;

DELETE FROM FFT_ORDER_PROPOSED
      WHERE fft_order_id IN (select id from TEMP_FFT_ORDER);
COMMIT;
prompt FFT_ORDER_PROPOSED Finished.
SELECT CURRENT_TIMESTAMP FROM dual;

DELETE FROM FFT_ORDER_RECEIVED
      WHERE fft_order_id IN (select id from TEMP_FFT_ORDER);
COMMIT;
prompt FFT_ORDER_RECEIVED Finished.
SELECT CURRENT_TIMESTAMP FROM dual;

DELETE FROM FFT_ORDER 
      WHERE id IN (select id from TEMP_FFT_ORDER);
COMMIT;
prompt FFT_ORDER Finished.
SELECT CURRENT_TIMESTAMP FROM dual;

delete from FFT_FINALIZED where delivery_date < '20111101';
prompt FFT_FINALIZED Finished.
SELECT CURRENT_TIMESTAMP FROM dual;

-- added by ogie
drop table TEMP_FFT_ORDER;

--FFT_SKU
DELETE
from FFT_SKU sku
where 
sku.id not in (select fft_sku_id from FFT_ORDER_ITEM oi, FFT_SKU skuu where oi.fft_sku_id = skuu.id and skuu.valid_to is not null and skuu.valid_to < '20111101') and
sku.valid_to is not null and sku.valid_to < '20111101';
COMMIT;
prompt FFT_SKU Finished.
SELECT CURRENT_TIMESTAMP FROM dual;
prompt
prompt Done.

prompt Starting Flush Shared_pool...
alter system flush shared_pool;
prompt Flush Shared_pool Finished.
SELECT CURRENT_TIMESTAMP FROM dual;

conn sys/&&var_sys_pwd@&&var_dbname as sysdba
prompt Starting Gather Schema Stats...
exec dbms_stats.gather_schema_stats('OLDEON');
prompt Gather Schema Stats Finished.

SELECT CURRENT_TIMESTAMP FROM dual;