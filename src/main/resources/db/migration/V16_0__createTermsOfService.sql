--------------------------------------------------------
--  DDL for Table EON_TERMS_OF_SERVICE
--------------------------------------------------------

  CREATE TABLE EON_TERMS_OF_SERVICE 
   (	ID VARCHAR2(32 BYTE), 
	VERSION NUMBER DEFAULT 1, 
	CONTENT CLOB, 
	EMAILLIST VARCHAR2(300 BYTE), 
	CREATEDDATE TIMESTAMP (6), 
	CREATEDBY VARCHAR2(32 CHAR), 
	MODIFIEDDATE TIMESTAMP (6), 
	MODIFIEDBY VARCHAR2(32 CHAR)
   ) 
	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;
	
--------------------------------------------------------
--  Constraints for Table EON_TERMS_OF_SERVICE
--------------------------------------------------------

  ALTER TABLE EON_TERMS_OF_SERVICE ADD CONSTRAINT EON_TERMS_OF_SERVICE_PK PRIMARY KEY (ID)
  USING INDEX TABLESPACE EON_INDX01;
  ALTER TABLE EON_TERMS_OF_SERVICE MODIFY (CREATEDBY NOT NULL ENABLE);
  ALTER TABLE EON_TERMS_OF_SERVICE MODIFY (CREATEDDATE NOT NULL ENABLE);
  ALTER TABLE EON_TERMS_OF_SERVICE MODIFY (EMAILLIST NOT NULL ENABLE);
  ALTER TABLE EON_TERMS_OF_SERVICE MODIFY (CONTENT NOT NULL ENABLE);
  ALTER TABLE EON_TERMS_OF_SERVICE MODIFY (VERSION NOT NULL ENABLE);
  ALTER TABLE EON_TERMS_OF_SERVICE MODIFY (ID NOT NULL ENABLE);
  
  
  --------------------------------------------------------
--  Ref Constraints for Table EON_TERMS_OF_SERVICE
--------------------------------------------------------

  ALTER TABLE EON_TERMS_OF_SERVICE ADD CONSTRAINT EON_TERMS_OF_SERVICE_EON__FK1 FOREIGN KEY (CREATEDBY)
      REFERENCES EON_USERS (USERNAME) ENABLE;