--------------------------------------------------------
--  DDL for Table schema_version
--------------------------------------------------------

  CREATE TABLE "schema_version" 
   (    "version_rank" NUMBER(38,0), 
    "installed_rank" NUMBER(38,0), 
    "version" VARCHAR2(50 BYTE), 
    "description" VARCHAR2(200 BYTE), 
    "type" VARCHAR2(20 BYTE), 
    "script" VARCHAR2(1000 BYTE), 
    "checksum" NUMBER(38,0), 
    "installed_by" VARCHAR2(30 BYTE), 
    "installed_on" TIMESTAMP (6) DEFAULT CURRENT_TIMESTAMP, 
    "execution_time" NUMBER(38,0), 
    "success" NUMBER(1,0)
   ) 
   TABLESPACE EON_DATA1 LOGGING 
    NOCOMPRESS 
    NOCACHE
    NOPARALLEL
    MONITORING;
--------------------------------------------------------
--  DDL for Index schema_version_vr_idx
--------------------------------------------------------

  CREATE INDEX "schema_version_vr_idx" ON "schema_version" ("version_rank") 
  TABLESPACE "EON_INDX01" ;
--------------------------------------------------------
--  DDL for Index schema_version_ir_idx
--------------------------------------------------------

  CREATE INDEX "schema_version_ir_idx" ON "schema_version" ("installed_rank") 
  TABLESPACE "EON_INDX01" ;

--------------------------------------------------------
--  DDL for Index schema_version_s_idx
--------------------------------------------------------

  CREATE INDEX "schema_version_s_idx" ON "schema_version" ("success") 
  TABLESPACE "EON_INDX01" ;

--------------------------------------------------------
--  Constraints for Table schema_version
--------------------------------------------------------

  ALTER TABLE "schema_version" MODIFY ("version_rank" NOT NULL ENABLE);
 
  ALTER TABLE "schema_version" MODIFY ("installed_rank" NOT NULL ENABLE);
 
  ALTER TABLE "schema_version" MODIFY ("version" NOT NULL ENABLE);
 
  ALTER TABLE "schema_version" MODIFY ("description" NOT NULL ENABLE);
 
  ALTER TABLE "schema_version" MODIFY ("type" NOT NULL ENABLE);
 
  ALTER TABLE "schema_version" MODIFY ("script" NOT NULL ENABLE);
 
  ALTER TABLE "schema_version" MODIFY ("installed_by" NOT NULL ENABLE);
 
  ALTER TABLE "schema_version" MODIFY ("installed_on" NOT NULL ENABLE);
 
  ALTER TABLE "schema_version" MODIFY ("execution_time" NOT NULL ENABLE);
 
  ALTER TABLE "schema_version" MODIFY ("success" NOT NULL ENABLE);

  
Insert into "schema_version" ("version_rank","installed_rank","version","description","type","script","checksum","installed_by","installed_on","execution_time","success") values (1,1,'0','<< Flyway Init >>','INIT','<< Flyway Init >>',null,'EON01',to_timestamp('23-JAN-13 04.54.06.593000000 PM','DD-MON-RR HH.MI.SS.FF AM'),0,1);
Insert into "schema_version" ("version_rank","installed_rank","version","description","type","script","checksum","installed_by","installed_on","execution_time","success") values (2,2,'1.0','createSequence','SQL','V1_0__createSequence.sql',-1359224650,'EON01',to_timestamp('23-JAN-13 04.54.09.920000000 PM','DD-MON-RR HH.MI.SS.FF AM'),1736,1);
Insert into "schema_version" ("version_rank","installed_rank","version","description","type","script","checksum","installed_by","installed_on","execution_time","success") values (3,3,'1.1','createTables','SQL','V1_1__createTables.sql',1633873673,'EON01',to_timestamp('23-JAN-13 04.54.13.014000000 PM','DD-MON-RR HH.MI.SS.FF AM'),3083,1);
Insert into "schema_version" ("version_rank","installed_rank","version","description","type","script","checksum","installed_by","installed_on","execution_time","success") values (4,4,'1.2','setConstrainsts','SQL','V1_2__setConstrainsts.sql',-1767486456,'EON01',to_timestamp('23-JAN-13 04.54.28.854000000 PM','DD-MON-RR HH.MI.SS.FF AM'),15814,1);
Insert into "schema_version" ("version_rank","installed_rank","version","description","type","script","checksum","installed_by","installed_on","execution_time","success") values (5,5,'1.4','insertCategories','SQL','V1_4__insertCategories.sql',-356932669,'EON01',to_timestamp('23-JAN-13 04.54.28.923000000 PM','DD-MON-RR HH.MI.SS.FF AM'),36,1);
Insert into "schema_version" ("version_rank","installed_rank","version","description","type","script","checksum","installed_by","installed_on","execution_time","success") values (6,6,'1.5','insertDealingPatternRelations','SQL','V1_5__insertDealingPatternRelations.sql',73667826,'EON01',to_timestamp('23-JAN-13 04.54.28.980000000 PM','DD-MON-RR HH.MI.SS.FF AM'),46,1);
Insert into "schema_version" ("version_rank","installed_rank","version","description","type","script","checksum","installed_by","installed_on","execution_time","success") values (7,7,'1.6','insertRoles','SQL','V1_6__insertRoles.sql',-1600229656,'EON01',to_timestamp('23-JAN-13 04.54.29.128000000 PM','DD-MON-RR HH.MI.SS.FF AM'),98,1);
Insert into "schema_version" ("version_rank","installed_rank","version","description","type","script","checksum","installed_by","installed_on","execution_time","success") values (8,8,'1.7','insertSheetType','SQL','V1_7__insertSheetType.sql',803105511,'EON01',to_timestamp('23-JAN-13 04.54.29.367000000 PM','DD-MON-RR HH.MI.SS.FF AM'),222,1);
Insert into "schema_version" ("version_rank","installed_rank","version","description","type","script","checksum","installed_by","installed_on","execution_time","success") values (9,9,'1.8','insertSKUColumn','SQL','V1_8__insertSKUColumn.sql',477546132,'EON01',to_timestamp('23-JAN-13 04.54.30.050000000 PM','DD-MON-RR HH.MI.SS.FF AM'),637,1);
Insert into "schema_version" ("version_rank","installed_rank","version","description","type","script","checksum","installed_by","installed_on","execution_time","success") values (10,10,'1.9','insertUnitOfMeasurement','SQL','V1_9__insertUnitOfMeasurement.sql',611144121,'EON01',to_timestamp('23-JAN-13 04.54.30.285000000 PM','DD-MON-RR HH.MI.SS.FF AM'),220,1);
Insert into "schema_version" ("version_rank","installed_rank","version","description","type","script","checksum","installed_by","installed_on","execution_time","success") values (11,11,'1.10','insertCompanyType','SQL','V1_10__insertCompanyType.sql',1994913761,'EON01',to_timestamp('23-JAN-13 04.54.30.369000000 PM','DD-MON-RR HH.MI.SS.FF AM'),68,1);
Insert into "schema_version" ("version_rank","installed_rank","version","description","type","script","checksum","installed_by","installed_on","execution_time","success") values (12,12,'1.11','insertZipCode','SQL','V1_11__insertZipCode.sql',1906990018,'EON01',to_timestamp('23-JAN-13 04.57.10.758000000 PM','DD-MON-RR HH.MI.SS.FF AM'),160359,1);
Insert into "schema_version" ("version_rank","installed_rank","version","description","type","script","checksum","installed_by","installed_on","execution_time","success") values (13,13,'1.12','insertCompany','SQL','V1_12__insertCompany.sql',1675695448,'EON01',to_timestamp('23-JAN-13 04.57.10.899000000 PM','DD-MON-RR HH.MI.SS.FF AM'),109,1);
Insert into "schema_version" ("version_rank","installed_rank","version","description","type","script","checksum","installed_by","installed_on","execution_time","success") values (14,14,'1.13','insertUsers','SQL','V1_13__insertUsers.sql',-451489112,'EON01',to_timestamp('23-JAN-13 04.57.11.342000000 PM','DD-MON-RR HH.MI.SS.FF AM'),429,1);
Insert into "schema_version" ("version_rank","installed_rank","version","description","type","script","checksum","installed_by","installed_on","execution_time","success") values (15,15,'1.14','recreateSequence','SQL','V1_14__recreateSequence.sql',-547250907,'EON01',to_timestamp('23-JAN-13 04.57.11.997000000 PM','DD-MON-RR HH.MI.SS.FF AM'),644,1);
