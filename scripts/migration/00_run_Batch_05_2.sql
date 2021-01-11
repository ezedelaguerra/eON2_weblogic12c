-------------------------------------------------
-- Log file
--
-------------------------------------------------
spool 00_run_Batch_05_2.log

-- grant user access to oldeon schema for migration scripts
-------------------------------------------------
-- The following scripts should be
-- run as SYSDBA
--
-------------------------------------------------
conn sys/&&var_sys_pwd@&&var_dbname as sysdba
prompt 
prompt Note: Make sure OLDEON schema exists otherwise migration scripts will not work
@Batch_05_2\grant_user.sql


-------------------------------------------------
--
-- The following scripts should be
-- run after logging in to your newly
-- created user.
--
-------------------------------------------------
conn &&var_username/&&var_user_pwd@&&var_dbname
@Batch_05_2\Batch_05_2_Company.sql
@Batch_05_2\Batch_05_2_CompanyDealingPattern.sql
@Batch_05_2\Batch_05_2_UserDealingPattern.sql
@Batch_05_2\Batch_05_2_Users.sql
COMMIT;

-------- stored procedures -----------
@Batch_05_2\proc_call_populate_users.sql
@Batch_05_2\proc_populate_sku.sql

-------- recompile stored procedures -------
exec dbms_utility.compile_schema('&&var_compile_schema');

-------- execute procedures -----------
set serveroutput on format wrapped;
prompt 
prompt Start migration....
prompt 
exec proc_execute_once;
prompt 
prompt Migration in progress this may take time please wait....
exec proc_execute_migration;
prompt Done.

-------- change chouai users to selleradmin -----------
--prompt 
--prompt Changing chouai users to selleradmin...
--prompt 
--@Batch_03\chouai_to_selleradmin.sql
--prompt Done.

prompt 
prompt Finished.
exit