drop user &&var_username cascade;
create user &&var_username identified by &&var_user_pwd;
grant connect,resource, dba to &&var_username;
alter user &&var_username default tablespace EON_DATA1;
alter user &&var_username temporary tablespace temp;