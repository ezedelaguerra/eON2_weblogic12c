-------------------------------------------------
-- Log file
--
-------------------------------------------------
spool 00_run_Batch_09.log

-- grant user access to oldeon schema for migration scripts
-------------------------------------------------
-- The following scripts should be
-- run as SYSDBA
--
-------------------------------------------------
conn sys/&&var_sys_pwd@&&var_dbname as sysdba
prompt 
prompt Note: Make sure OLDEON schema exists otherwise migration scripts will not work
@Batch_09\grant_user.sql


-------------------------------------------------
--
-- The following scripts should be
-- run after logging in to your newly
-- created user.
--
-------------------------------------------------
conn &&var_username/&&var_user_pwd@&&var_dbname
@Batch_09\Batch_09_Company.sql
@Batch_09\Batch_09_CompanyDealingPattern.sql
@Batch_09\Batch_09_UserDealingPattern.sql
@Batch_09\Batch_09_Users.sql
COMMIT;

-------- stored procedures -----------

-------- recompile stored procedures -------
exec dbms_utility.compile_schema('&&var_compile_schema');

-------- performance enchancements options -----------
prompt 
prompt Setting indexes of EON_ORDER_ITEM to REVERSE for faster insert.
ALTER INDEX EON_ORDER_ITEM_PK REBUILD REVERSE;
ALTER INDEX EON_ORDER_ITEM_UK1 REBUILD REVERSE;
prompt Done.

prompt Starting Flush Shared_pool...
alter system flush shared_pool;
prompt Done.


-------- delete old eON data -------
@99_delete_old_data.sql
conn &&var_username/&&var_user_pwd@&&var_dbname

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

-------- performance enchancements revertback to normal -----------
prompt 
prompt Setting indexes of EON_ORDER_ITEM to back to normal (NOREVERSE).
ALTER INDEX EON_ORDER_ITEM_PK REBUILD NOREVERSE;
ALTER INDEX EON_ORDER_ITEM_UK1 REBUILD NOREVERSE;
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