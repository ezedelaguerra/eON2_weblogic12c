-- grant user access to oldeon schema for migration scripts
-------------------------------------------------
-- The following scripts should be
-- run as SYSDBA
--
-------------------------------------------------
conn sys/&&var_sys_pwd@&&var_dbname as sysdba
prompt 
prompt Note: Make sure OLDEON schema exists otherwise migration scripts will not work
@Batch_01_Pilot\grant_user.sql

-------------------------------------------------
--
-- The following scripts should be
-- run after logging in to your newly
-- created user.
--
-------------------------------------------------
conn &&var_username/&&var_user_pwd@&&var_dbname
@Batch_01_Pilot\alter\create_db_obj.sql
@Batch_01_Pilot\alter\alter_table_datatype.sql
@Batch_01_Pilot\alter\create_table.sql
@Batch_01_Pilot\Batch_01_Company.sql
@Batch_01_Pilot\Batch_01_CompanyDealingPattern.sql
@Batch_01_Pilot\Batch_01_UserDealingPattern.sql
@Batch_01_Pilot\Batch_01_Users.sql
COMMIT;

-------- stored procedures -----------
@Batch_01_Pilot\log_timestamp.sql
@Batch_01_Pilot\proc_populate_users.sql
@Batch_01_Pilot\proc_call_populate_users.sql
@Batch_01_Pilot\proc_populate_sort_category.sql
@Batch_01_Pilot\proc_populate_sku_group.sql
@Batch_01_Pilot\proc_populate_sku.sql
@Batch_01_Pilot\proc_populate_sort_sku_group.sql
@Batch_01_Pilot\proc_populate_sort_sku.sql
@Batch_01_Pilot\proc_populate_sort_buyers_admin.sql
@Batch_01_Pilot\proc_populate_sort_buyers.sql
@Batch_01_Pilot\proc_populate_excel_settings.sql
@Batch_01_Pilot\proc_execute_once.sql
@Batch_01_Pilot\proc_populate_dealing_pattern.sql
@Batch_01_Pilot\proc_populate_comments_inbox.sql
@Batch_01_Pilot\proc_populate_order.sql
@Batch_01_Pilot\proc_populate_order_item.sql
@Batch_01_Pilot\proc_populate_order_item_visibility.sql
@Batch_01_Pilot\proc_populate_order_alloc.sql
@Batch_01_Pilot\proc_populate_order_received.sql
@Batch_01_Pilot\proc_populate_order_billing.sql
@Batch_01_Pilot\proc_execute_migration.sql

-------- execute procedures -----------
set serveroutput on format wrapped;
prompt 
prompt Start migration....
prompt 
exec proc_execute_once;
prompt 
prompt Migration in progress this may take 2-5 mins please wait....
prompt 
exec proc_execute_migration;