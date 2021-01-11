conn &&var_username/&&var_username@&&var_dbname

@create_db\create_table.sql
@create_db\proc_delete_dealingpattern.sql

@Batch_01_Pilot\Batch_01_UserDealingPattern.sql
exec proc_delete_dealingpattern;