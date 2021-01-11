alter table eon_sku modify (SKU_NAME         VARCHAR2(128 CHAR));              
alter table eon_sku modify (LOCATION         VARCHAR2(64 CHAR) );
alter table eon_sku modify (MARKET           VARCHAR2(64 CHAR) );
alter table eon_sku modify (GRADE            VARCHAR2(64 CHAR) );
alter table eon_sku modify (CLASS            VARCHAR2(64 CHAR) );
alter table eon_sku modify (PACKAGE_TYPE 	   VARCHAR2(64 CHAR) ); 


alter table EON_COMPANY	modify (COMPANY_NAME	VARCHAR2(50 CHAR));   

alter table eon_users modify (USERNAME	VARCHAR2(32 CHAR));
alter table eon_users modify (PASSWORD	VARCHAR2(32 CHAR));
alter table eon_users modify (NAME			VARCHAR2(96 CHAR));    
alter table eon_users modify (SHORTNAME	VARCHAR2(96 CHAR));