  CREATE TABLE "EON01"."EON_LOCK_BUTTON_STATUS" 
   (
	"USER_ID" NUMBER(9,0), 
	"LOCK_BUTTON_STATUS" CHAR(1 BYTE) DEFAULT '1'
   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "EON_DATA1" ;