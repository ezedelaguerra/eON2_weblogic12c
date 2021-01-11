-------------------------------------------------
-- Steps
-- 1. Go to directory where the scripts are
-- 2. Run "sqlplus /nolog"
-- 3. Run "@00_run.sql"
-- 4. Enter value for db name (see TNSNAMES.ORA)
-- 5. Enter desired schema name
--
-------------------------------------------------

-------------------------------------------------
-- The following scripts should be
-- run as SYSDBA
--
-------------------------------------------------
spool run.log
conn sys/&&var_sys_pwd@&&var_dbname as sysdba
--@01_create_tablespace.sql
prompt running 02_create_user.sql
@02_create_user.sql

-------------------------------------------------
--
-- The following scripts should be
-- run after logging in to your newly
-- created user.
--
-------------------------------------------------
prompt
conn &&var_username/&&var_user_pwd@&&var_dbname
prompt running 3
@03_create_sequence.sql
prompt running 4
@04_create_table.sql
prompt running 5
@05_create_pk_uk.sql
prompt running 6
@06_create_fk.sql
prompt running 7
@07_insert_category.sql
prompt running 8
@08_insert_dealing_pattern_relation.sql
prompt running 9
@09_insert_roles.sql
prompt running 10
@10_insert_sheet_type.sql
prompt running 11
@11_insert_sku_column.sql
prompt running 12
@12_insert_unit_of_measurement.sql
prompt running 13
@13_insert_company_type.sql
prompt running 14
@14_insert_zip_code.sql
prompt running TEST_01
@TEST_01_insert_company.sql
prompt running TEST_02
@TEST_02_insert_users.sql
prompt running TEST_03
@TEST_03_insert_relationships.sql
prompt running TEST_04
@TEST_04_insert_user_category.sql
prompt running TEST_05
@TEST_05_insert_sku_group.sql
prompt running TEST_06
@TEST_06_insert_sku-s_ca01.sql
prompt running TEST_07
@TEST_07_insert_sku-s_ca02.sql
prompt running TEST_08
@TEST_08_insert_sku-s_ca03.sql
prompt running TEST_09
@TEST_09_insert_sku-s_cb01.sql
prompt running TEST_10
@TEST_10_insert_sku-s_cb02.sql
prompt running TEST_11
@TEST_11_insert_sku-s_cb03.sql
prompt running TEST_12
@TEST_12_insert_order.sql
prompt running TEST_13
@TEST_13_insert_order_item-s_ca01.sql
prompt running TEST_14
@TEST_14_insert_order_item-s_ca02.sql
prompt running TEST_15
@TEST_15_insert_order_item-s_ca03.sql
prompt running TEST_16
@TEST_16_insert_order_item-s_cb01.sql
prompt running TEST_17
@TEST_17_insert_order_item-s_cb02.sql
prompt running TEST_18
@TEST_18_insert_order_item-s_cb03.sql
prompt running TEST_19
@TEST_19_insert_pmf_users.sql
prompt running TEST_20
@TEST_20_insert_sku-uat_sa1.sql
prompt running TEST_21
@TEST_21_insert_sku-uat_sa2.sql
prompt running TEST_22
@TEST_22_insert_sku-uat_sa3.sql
prompt running TEST_23
@TEST_23_insert_sku-uat_sb1.sql
prompt running TEST_24
@TEST_24_insert_sku-uat_sb2.sql
prompt running TEST_25
@TEST_25_insert_sku-uat_sb3.sql
prompt running TEST_26
@TEST_26_insert_order_item-uat_sa1.sql
prompt running TEST_27
@TEST_27_insert_order_item-uat_sa2.sql
prompt running TEST_28
@TEST_28_insert_order_item-uat_sa3.sql
prompt running TEST_29
@TEST_29_insert_order_item-uat_sb1.sql
prompt running TEST_30
@TEST_30_insert_order_item-uat_sb2.sql
prompt running TEST_31
@TEST_31_insert_order_item-uat_sb3.sql
prompt end
spool off