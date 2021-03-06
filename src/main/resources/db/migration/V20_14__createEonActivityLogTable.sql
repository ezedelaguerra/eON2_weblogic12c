
  CREATE TABLE "EON01"."EON_ACTIVITY_LOG" 
   (	"ACTIVITY_LOG_ID" NUMBER(9,0), 
	"USER_ID" NUMBER(9,0), 
	"USERNAME" VARCHAR2(32 CHAR), 
	"ACTIVITY_TIMESTAMP" TIMESTAMP (6), 
	"ACTIVITY_DATE" CHAR(8 BYTE), 
	"SHEET_NAME" VARCHAR2(11 CHAR), 
	"ACTION" VARCHAR2(11 CHAR), 
	"DELIVERY_DATE" CHAR(8 BYTE), 
	"TARGET_BUYER_ID" VARCHAR2(1000 BYTE), 
	"TARGET_SELLER_ID" VARCHAR2(1000 BYTE)
   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "EON_DATA1" ;
 

   CREATE SEQUENCE  "EON01"."EON_ACTIVITY_LOG_SEQ"  MINVALUE 1 MAXVALUE 999999999 INCREMENT BY 1 START WITH 11066 NOCACHE  ORDER  NOCYCLE ;