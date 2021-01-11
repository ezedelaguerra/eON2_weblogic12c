--------------------------------------------------------
--  DDL for Table ALLOC_RCVD_DIFF_QTY_ERROR_MST
--------------------------------------------------------

  CREATE TABLE ALLOC_RCVD_DIFF_QTY_ERROR_MST 
   (	
   	ALLOC_ORDER_ID NUMBER(9,0), 
	SELLER_ID NUMBER(9,0), 
	BUYER_ID NUMBER(9,0), 
	USERNAME VARCHAR2(32 CHAR), 
	PASS VARCHAR2(32 CHAR), 
	NAME VARCHAR2(96 CHAR), 
	DELIVERY_DATE NUMBER(9,0), 
	ALLOC_SKU_CNT NUMBER(9,0), 
	RCVD_ORDER_ID NUMBER(9,0), 
	RCVD_SKU_CNT NUMBER(9,0)
   )
	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;
--------------------------------------------------------
--  DDL for Table ALLOC_RCVD_DIFF_QTY_ERROR_TMP
--------------------------------------------------------

  CREATE TABLE ALLOC_RCVD_DIFF_QTY_ERROR_TMP 
   (	ALLOC_ORDER_ID NUMBER(9,0), 
	SELLER_ID NUMBER(9,0), 
	BUYER_ID NUMBER(9,0), 
	USERNAME VARCHAR2(32 CHAR), 
	PASS VARCHAR2(32 CHAR), 
	NAME VARCHAR2(96 CHAR), 
	DELIVERY_DATE NUMBER(9,0), 
	ALLOC_SKU_CNT NUMBER(9,0), 
	RCVD_ORDER_ID NUMBER(9,0), 
	RCVD_SKU_CNT NUMBER(9,0)
   )
	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;
--------------------------------------------------------
--  DDL for Table ALLOC_RCVD_DIFF_QTY_ERROR_TXN
--------------------------------------------------------

  CREATE TABLE ALLOC_RCVD_DIFF_QTY_ERROR_TXN 
   (	ALLOC_ORDER_ID NUMBER(9,0), 
	SELLER_ID NUMBER(9,0), 
	BUYER_ID NUMBER(9,0), 
	USERNAME VARCHAR2(32 CHAR), 
	PASS VARCHAR2(32 CHAR), 
	NAME VARCHAR2(96 CHAR), 
	DELIVERY_DATE NUMBER(9,0), 
	ALLOC_SKU_CNT NUMBER(9,0), 
	RCVD_ORDER_ID NUMBER(9,0), 
	RCVD_SKU_CNT NUMBER(9,0)
   ) 
	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;
--------------------------------------------------------
--  DDL for Table EON_ADMIN_MEMBER
--------------------------------------------------------

  CREATE TABLE EON_ADMIN_MEMBER 
   (	ADMIN_MEMBER_ID NUMBER(9,0), 
	ADMIN_ID NUMBER(9,0), 
	MEMBER_ID NUMBER(9,0), 
	DEALING_PATTERN_RELATION_ID NUMBER(9,0), 
	START_DATE CHAR(8 BYTE), 
	END_DATE CHAR(8 BYTE), 
	CREATE_TIMESTAMP TIMESTAMP (6) DEFAULT SYSDATE, 
	CREATED_BY NUMBER(9,0), 
	UPDATE_TIMESTAMP TIMESTAMP (6), 
	UPDATED_BY NUMBER(9,0), 
	ADMIN_MEMBER_ID_OLD NUMBER
   ) 
   	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;
--------------------------------------------------------
--  DDL for Table EON_AKADEN_SKU
--------------------------------------------------------

  CREATE TABLE EON_AKADEN_SKU 
   (	AKADEN_SKU_ID NUMBER(9,0), 
	SKU_ID NUMBER(9,0), 
	SELLER_ID NUMBER(9,0), 
	SKU_CATEGORY_ID NUMBER(9,0), 
	SHEET_TYPE_ID NUMBER(9,0), 
	SKU_GROUP_ID NUMBER(9,0), 
	ORDER_UNIT_ID NUMBER(9,0), 
	PROPOSED_BY NUMBER(9,0), 
	SKU_NAME VARCHAR2(64 CHAR), 
	TYPE_FLAG CHAR(1 BYTE), 
	LOCATION VARCHAR2(42 CHAR), 
	MARKET VARCHAR2(42 CHAR), 
	GRADE VARCHAR2(42 CHAR), 
	CLASS VARCHAR2(42 CHAR), 
	PRICE1 NUMBER(9,0), 
	PRICE2 NUMBER(9,0), 
	PRICE_WITHOUT_TAX NUMBER(9,0), 
	PACKAGE_QUANTITY NUMBER(9,0), 
	PACKAGE_TYPE VARCHAR2(42 CHAR), 
	CREATE_TIMESTAMP TIMESTAMP (6) DEFAULT SYSDATE, 
	CREATED_BY NUMBER(9,0), 
	UPDATE_TIMESTAMP TIMESTAMP (6), 
	UPDATED_BY NUMBER(9,0)
   ) 
   	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;
--------------------------------------------------------
--  DDL for Table EON_AUDIT_TRAIL
--------------------------------------------------------

  CREATE TABLE EON_AUDIT_TRAIL 
   (	ID NUMBER(9,0), 
	USER_ID NUMBER(9,0), 
	USERNAME VARCHAR2(32 CHAR), 
	PROCESS_ID NUMBER(9,0), 
	LOG_TYPE VARCHAR2(16 CHAR), 
	IP_ADDRESS VARCHAR2(15 CHAR), 
	URL_PATH VARCHAR2(300 CHAR), 
	URL_REFERER VARCHAR2(300 CHAR), 
	USER_AGENT VARCHAR2(300 CHAR), 
	SYSTEM_DATE VARCHAR2(30 CHAR), 
	DB_DATE TIMESTAMP (6)
   ) 
	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;
--------------------------------------------------------
--  DDL for Table EON_AUTODWNLD_BUYERS
--------------------------------------------------------

  CREATE TABLE EON_AUTODWNLD_BUYERS 
   (	FK_ID_SCHEDULE_CSV NUMBER(9,0), 
	FK_ID_BUYER NUMBER(9,0)
   )
	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;
--------------------------------------------------------
--  DDL for Table EON_AUTODWNLD_EXCEPT_DATE
--------------------------------------------------------

  CREATE TABLE EON_AUTODWNLD_EXCEPT_DATE 
   (	FK_ID_SCHEDULE_CSV NUMBER(9,0), 
	EXCEPT_DATE TIMESTAMP (6)
   )
	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;
--------------------------------------------------------
--  DDL for Table EON_AUTODWNLD_SCHEDULE
--------------------------------------------------------

  CREATE TABLE EON_AUTODWNLD_SCHEDULE 
   (	ID_SCHEDULE_CSV NUMBER(9,0), 
	FK_ID_USER NUMBER(9,0), 
	EXECUTION_TIME VARCHAR2(5 BYTE), 
	DATE_LAST_DOWNLOAD TIMESTAMP (6), 
	LEAD_TIME NUMBER(3,0), 
	FK_ID_SHEET_TYPE NUMBER(9,0), 
	SHEET_CATEGORY NUMBER(9,0), 
	HAS_QTY CHAR(1 BYTE), 
	FTP_IP VARCHAR2(50 CHAR), 
	FTP_ID VARCHAR2(50 CHAR), 
	FTP_PW VARCHAR2(50 CHAR), 
	LAST_RUN_DATE TIMESTAMP (6)
   )
	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;
--------------------------------------------------------
--  DDL for Table EON_AUTODWNLD_SELLERS
--------------------------------------------------------

  CREATE TABLE EON_AUTODWNLD_SELLERS 
   (	FK_ID_SCHEDULE_CSV NUMBER(9,0), 
	FK_ID_SELLER NUMBER(9,0)
   )
	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;
--------------------------------------------------------
--  DDL for Table EON_CATEGORY
--------------------------------------------------------

  CREATE TABLE EON_CATEGORY 
   (	SKU_CATEGORY_ID NUMBER(9,0), 
	DESCRIPTION VARCHAR2(50 CHAR), 
	TAB_NAME VARCHAR2(50 CHAR)
   )
	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;
--------------------------------------------------------
--  DDL for Table EON_COLUMN_WIDTH
--------------------------------------------------------

  CREATE TABLE EON_COLUMN_WIDTH 
   (	FK_USER_ID NUMBER(9,0), 
	HEADER_KEY VARCHAR2(64 CHAR), 
	WIDTH NUMBER(9,0)
   ) 
	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;
--------------------------------------------------------
--  DDL for Table EON_COMMENTS_INBOX
--------------------------------------------------------

  CREATE TABLE EON_COMMENTS_INBOX 
   (	COMMENTS_INBOX_ID NUMBER(9,0), 
	SENDER_ENTITY_ID NUMBER(9,0), 
	RECIPIENT_ENTITY_ID NUMBER(9,0), 
	RECEIVED_DATE TIMESTAMP (6), 
	COMMENT_SUBJECT VARCHAR2(4000 CHAR), 
	COMMENT_MESSAGE CLOB, 
	OPEN_STATUS CHAR(1 BYTE), 
	CREATE_TIMESTAMP TIMESTAMP (6) DEFAULT SYSDATE, 
	CREATE_USER_ID NUMBER(9,0), 
	UPDATE_TIMESTAMP TIMESTAMP (6), 
	UPDATED_BY NUMBER(9,0), 
	COMMENTS_INBOX_ID_OLD NUMBER(9,0)
   )
	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;
 
--------------------------------------------------------
--  DDL for Table EON_COMMENTS_OUTBOX
--------------------------------------------------------

  CREATE TABLE EON_COMMENTS_OUTBOX 
   (	COMMENTS_OUTBOX_ID NUMBER(9,0), 
	SENDER_ENTITY_ID NUMBER(9,0), 
	SENT_DATE TIMESTAMP (6), 
	RECIPIENTS_ADDRESS_OLD VARCHAR2(1024 CHAR), 
	COMMENT_SUBJECT VARCHAR2(4000 CHAR), 
	COMMENT_MESSAGE CLOB, 
	CREATE_TIMESTAMP TIMESTAMP (6) DEFAULT SYSDATE, 
	CREATE_USER_ID NUMBER(9,0), 
	UPDATE_TIMESTAMP TIMESTAMP (6), 
	UPDATED_BY NUMBER(9,0), 
	RECIPIENTS_ADDRESS CLOB
   ) 
	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;
--------------------------------------------------------
--  DDL for Table EON_COMPANY
--------------------------------------------------------

  CREATE TABLE EON_COMPANY 
   (	COMPANY_ID NUMBER(9,0), 
	COMPANY_NAME VARCHAR2(50 CHAR), 
	SHORT_NAME VARCHAR2(50 CHAR), 
	COMPANY_TYPE_ID NUMBER(9,0), 
	CONTACT_PERSON VARCHAR2(50 CHAR), 
	SOX_FLAG CHAR(1 BYTE), 
	COMPANY_ADDRESS1 VARCHAR2(100 CHAR), 
	COMPANY_ADDRESS2 VARCHAR2(100 CHAR), 
	COMPANY_ADDRESS3 VARCHAR2(100 CHAR), 
	TELEPHONE_NUMBER VARCHAR2(20 CHAR), 
	FAX_NUMBER VARCHAR2(20 CHAR), 
	EMAIL_ADDRESS VARCHAR2(50 CHAR), 
	COMMENTS VARCHAR2(1000 CHAR), 
	CREATE_TIMESTAMP TIMESTAMP (6) DEFAULT SYSDATE, 
	CREATED_BY NUMBER(9,0), 
	UPDATE_TIMESTAMP TIMESTAMP (6), 
	UPDATED_BY NUMBER(9,0)
   )
	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;
--------------------------------------------------------
--  DDL for Table EON_COMPANY_DEALING_PATTERN
--------------------------------------------------------

  CREATE TABLE EON_COMPANY_DEALING_PATTERN 
   (	COMPANY_DEALING_PATTERN_ID NUMBER(9,0), 
	DEALING_PATTERN_RELATION_ID NUMBER(9,0), 
	COMPANY_01 NUMBER(9,0), 
	COMPANY_02 NUMBER(9,0), 
	ISACTIVE CHAR(1 BYTE) DEFAULT '1', 
	CREATE_TIMESTAMP TIMESTAMP (6) DEFAULT SYSDATE, 
	CREATED_BY NUMBER(9,0), 
	UPDATE_TIMESTAMP TIMESTAMP (6), 
	UPDATED_BY NUMBER(9,0)
   ) 
	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;
--------------------------------------------------------
--  DDL for Table EON_COMPANY_TYPE
--------------------------------------------------------

  CREATE TABLE EON_COMPANY_TYPE 
   (	COMPANY_TYPE_ID NUMBER(9,0), 
	DESCRIPTION VARCHAR2(50 CHAR)
   ) 
	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;
--------------------------------------------------------
--  DDL for Table EON_DEALING_PATTERN_RELATION
--------------------------------------------------------

  CREATE TABLE EON_DEALING_PATTERN_RELATION 
   (	DEALING_PATTERN_RELATION_ID NUMBER(9,0), 
	DESCRIPTION VARCHAR2(50 CHAR)
   )
	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;
--------------------------------------------------------
--  DDL for Table EON_EXCEL_SETTING
--------------------------------------------------------

  CREATE TABLE EON_EXCEL_SETTING 
   (	USER_ID NUMBER(9,0), 
	WEEKLY_FLAG CHAR(1 BYTE), 
	DATE_OPTION CHAR(1 BYTE), 
	SELLER_OPTION CHAR(1 BYTE), 
	BUYER_OPTION CHAR(1 BYTE), 
	HAS_QTY CHAR(1 BYTE), 
	PRICE_FLAG CHAR(1 BYTE), 
	PRICE_COMPUTE_OPTION CHAR(1 BYTE)
   ) 
	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;
--------------------------------------------------------
--  DDL for Table EON_FORGOT_PASSWORD_LINK
--------------------------------------------------------

  CREATE TABLE EON_FORGOT_PASSWORD_LINK 
   (	FORGOT_PASSWORD_LINK_ID NUMBER(9,0), 
	USER_ID NUMBER(9,0), 
	STATUS NUMBER(1,0)
   ) 
	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;
--------------------------------------------------------
--  DDL for Table EON_LOGIN_INQUIRY_REQUEST
--------------------------------------------------------

  CREATE TABLE EON_LOGIN_INQUIRY_REQUEST 
   (	INQUIRY_REQUEST_ID NUMBER(9,0), 
	INQUIRY_ITEMS_EON CHAR(1 BYTE), 
	INQUIRY_ITEMS_NSYSTEM CHAR(1 BYTE), 
	INQUIRY_ITEMS_OTHERS CHAR(1 BYTE), 
	DETAILS_OF_INQUIRY_APPLY CHAR(1 BYTE), 
	DETAILS_OF_INQUIRY_DOCS CHAR(1 BYTE), 
	DETAILS_OF_INQUIRY_EXPLANATION CHAR(1 BYTE), 
	DETAILS_OF_INQUIRY_OTHERS CHAR(1 BYTE), 
	NAME_FIRST VARCHAR2(50 CHAR), 
	NAME_LAST VARCHAR2(50 CHAR), 
	FURIGANA_FIRST VARCHAR2(50 CHAR), 
	FURIGANA_LAST VARCHAR2(50 CHAR), 
	COMPANY_NAME VARCHAR2(50 CHAR), 
	STORE_NAME VARCHAR2(50 CHAR), 
	DEPARTMENT_NAME VARCHAR2(50 CHAR), 
	TELEPHONE_NUMBER NUMBER(11,0), 
	MOBILE_NUMBER NUMBER(11,0), 
	EMAIL_ADDRESS VARCHAR2(50 CHAR), 
	ZIPCODE NUMBER(7,0), 
	ADDRESS VARCHAR2(50 CHAR), 
	DATE_CREATED TIMESTAMP (6)
   ) 
	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;
--------------------------------------------------------
--  DDL for Table EON_ORDER
--------------------------------------------------------

  CREATE TABLE EON_ORDER 
   (	ORDER_ID NUMBER(9,0), 
	BUYER_ID NUMBER(9,0), 
	SELLER_ID NUMBER(9,0), 
	DELIVERY_DATE CHAR(8 BYTE), 
	ORDER_SAVED_BY NUMBER(9,0), 
	ORDER_PUBLISHED_BY NUMBER(9,0), 
	ORDER_FINALIZED_BY NUMBER(9,0), 
	ORDER_UNFINALIZED_BY NUMBER(9,0), 
	ORDER_LOCKED_BY NUMBER(9,0), 
	ORDER_UNLOCKED_BY NUMBER(9,0), 
	ALLOCATION_SAVED_BY NUMBER(9,0), 
	ALLOCATION_PUBLISHED_BY NUMBER(9,0), 
	ALLOCATION_FINALIZED_BY NUMBER(9,0), 
	ALLOCATION_UNFINALIZED_BY NUMBER(9,0), 
	RECEIVED_APPROVED_BY NUMBER(9,0), 
	RECEIVED_UNAPPROVED_BY NUMBER(9,0), 
	RECEIVED_SAVED_BY NUMBER(9,0), 
	BILLING_SAVED_BY NUMBER(9,0), 
	BILLING_FINALIZED_BY NUMBER(9,0), 
	BILLING_UNFINALIZED_BY NUMBER(9,0), 
	AKADEN_SAVED_BY NUMBER(9,0), 
	ORDER_PUBLISHED_BY_BA NUMBER(9,0), 
	LAST_SAVED_OS_TIMESTAMP TIMESTAMP (6), 
	COPIED_FROM_ORDER_ID NUMBER(9,0), 
	COPIED_FROM_TIMESTAMP TIMESTAMP (6), 
	DATE_CREATED TIMESTAMP (6) DEFAULT SYSDATE, 
	CREATED_BY NUMBER(9,0), 
	DATE_UPDATED TIMESTAMP (6), 
	UPDATED_BY NUMBER(9,0), 
	ORDER_ID_OLD NUMBER(9,0)
   )
	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;
--------------------------------------------------------
--  DDL for Table EON_ORDER_AKADEN
--------------------------------------------------------

  CREATE TABLE EON_ORDER_AKADEN 
   (	ORDER_AKADEN_ID NUMBER(9,0), 
	ORDER_ID NUMBER(9,0), 
	AKADEN_SKU_ID NUMBER(9,0), 
	COMMENTS VARCHAR2(50 CHAR), 
	ISNEWSKU CHAR(1 BYTE), 
	QUANTITY NUMBER(12,3), 
	TOTAL_QUANTITY NUMBER(16,3), 
	UNIT_PRICE NUMBER(12,0), 
	CREATE_TIMESTAMP TIMESTAMP (6) DEFAULT SYSDATE, 
	CREATED_BY NUMBER(9,0), 
	UPDATE_TIMESTAMP TIMESTAMP (6), 
	UPDATE_BY NUMBER(9,0)
   ) 
	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;
--------------------------------------------------------
--  DDL for Table EON_ORDER_ALLOCATION
--------------------------------------------------------

  CREATE TABLE EON_ORDER_ALLOCATION 
   (	ORDER_ALLOCATION_ID NUMBER(9,0), 
	ORDER_ID NUMBER(9,0), 
	SKU_ID NUMBER(9,0), 
	QUANTITY NUMBER(12,3), 
	CREATE_TIMESTAMP TIMESTAMP (6) DEFAULT SYSDATE, 
	CREATED_BY NUMBER(9,0), 
	UPDATE_TIMESTAMP TIMESTAMP (6), 
	UPDATED_BY NUMBER(9,0), 
	ORDER_ALLOCATION_ID_OLD NUMBER(9,0), 
	ISAPPROVED CHAR(1 BYTE) DEFAULT 0, 
	SKU_BA_ID NUMBER(9,0)
   ) 
	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;
--------------------------------------------------------
--  DDL for Table EON_ORDER_BILLING
--------------------------------------------------------

  CREATE TABLE EON_ORDER_BILLING 
   (	ORDER_BILLING_ID NUMBER(9,0), 
	ORDER_ID NUMBER(9,0), 
	SKU_ID NUMBER(9,0), 
	QUANTITY NUMBER(12,3), 
	COMMENTS VARCHAR2(50 CHAR), 
	CREATE_TIMESTAMP TIMESTAMP (6) DEFAULT SYSDATE, 
	CREATED_BY NUMBER(9,0), 
	UPDATE_TIMESTAMP TIMESTAMP (6), 
	UPDATED_BY NUMBER(9,0), 
	ORDER_BILLING_ID_OLD NUMBER(9,0), 
	SKU_BA_ID NUMBER(9,0)
   ) 
	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;
--------------------------------------------------------
--  DDL for Table EON_ORDER_ITEM
--------------------------------------------------------

  CREATE TABLE EON_ORDER_ITEM 
   (	ORDER_ITEM_ID NUMBER(9,0), 
	ORDER_ID NUMBER(9,0), 
	SKU_ID NUMBER(9,0), 
	QUANTITY NUMBER(12,3), 
	SOS_VIS_FLAG CHAR(1 BYTE) DEFAULT '1', 
	BAOS_VIS_FLAG CHAR(1 BYTE) DEFAULT '1', 
	CREATE_TIMESTAMP TIMESTAMP (6) DEFAULT SYSDATE, 
	CREATED_BY NUMBER(9,0), 
	UPDATE_TIMESTAMP TIMESTAMP (6), 
	UPDATE_BY_USERS_ID NUMBER(9,0), 
	ORDER_ITEM_ID_OLD NUMBER(9,0), 
	SKU_BA_ID NUMBER(9,0)
   ) 
	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;
--------------------------------------------------------
--  DDL for Table EON_ORDER_RECEIVED
--------------------------------------------------------

  CREATE TABLE EON_ORDER_RECEIVED 
   (	ORDER_RECEIVED_ID NUMBER(9,0), 
	ORDER_ID NUMBER(9,0), 
	SKU_ID NUMBER(9,0), 
	QUANTITY NUMBER(12,3), 
	ISAPPROVED CHAR(1 BYTE) DEFAULT 0, 
	CREATE_TIMESTAMP TIMESTAMP (6) DEFAULT SYSDATE, 
	CREATED_BY NUMBER(9,0), 
	UPDATE_TIMESTAMP TIMESTAMP (6), 
	UPDATED_BY NUMBER(9,0), 
	ORDER_RECEIVED_ID_OLD NUMBER(9,0), 
	SKU_BA_ID NUMBER(9,0)
   ) 
	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;
--------------------------------------------------------
--  DDL for Table EON_ORDER_UNIT
--------------------------------------------------------

  CREATE TABLE EON_ORDER_UNIT 
   (	ORDER_UNIT_ID NUMBER(9,0), 
	CATEGORY_ID NUMBER(9,0), 
	ORDER_UNIT_NAME VARCHAR2(15 CHAR)
   ) 
	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;
--------------------------------------------------------
--  DDL for Table EON_PRODUCT_MASTER_FILE
--------------------------------------------------------

  CREATE TABLE EON_PRODUCT_MASTER_FILE 
   (	PROD_MASTER_ID NUMBER(9,0), 
	USER_ID NUMBER(9,0), 
	NAME VARCHAR2(32 CHAR)
   ) 
	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;
--------------------------------------------------------
--  DDL for Table EON_PRODUCT_MASTER_FILE_SKU
--------------------------------------------------------

  CREATE TABLE EON_PRODUCT_MASTER_FILE_SKU 
   (	PROD_MASTER_ID NUMBER(9,0), 
	SKU_ID NUMBER(9,0), 
	SELLER_ID NUMBER(9,0), 
	SKU_CATEGORY_ID NUMBER(9,0), 
	SKU_GROUP_ID NUMBER(9,0), 
	ORDER_UNIT_ID NUMBER(9,0), 
	SKU_NAME VARCHAR2(64 CHAR), 
	SELLER_PRODUCT_CODE VARCHAR2(32 CHAR), 
	BUYER_PRODUCT_CODE VARCHAR2(32 CHAR), 
	COMMENTA VARCHAR2(64 CHAR), 
	COMMENTB VARCHAR2(64 CHAR), 
	LOCATION VARCHAR2(42 CHAR), 
	MARKET VARCHAR2(42 CHAR), 
	GRADE VARCHAR2(42 CHAR), 
	CLASS VARCHAR2(42 CHAR), 
	PRICE1 NUMBER(9,0), 
	PRICE2 NUMBER(9,0), 
	PRICE_WITHOUT_TAX NUMBER(9,0), 
	PACKAGE_QUANTITY NUMBER(9,0), 
	PACKAGE_TYPE VARCHAR2(42 CHAR), 
	CREATE_TIMESTAMP TIMESTAMP (6) DEFAULT SYSDATE, 
	CREATED_BY NUMBER(9,0), 
	UPDATE_TIMESTAMP TIMESTAMP (6), 
	UPDATED_BY NUMBER(9,0)
   ) 
	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;
--------------------------------------------------------
--  DDL for Table EON_PROFIT_PREFERENCE
--------------------------------------------------------

  CREATE TABLE EON_PROFIT_PREFERENCE 
   (	FK_ID_USER NUMBER(9,0), 
	TOTAL_SELLING_PRICE CHAR(1 BYTE) DEFAULT '0', 
	TOTAL_PROFIT CHAR(1 BYTE) DEFAULT '0', 
	TOTAL_PROFIT_PERCENT CHAR(1 BYTE) DEFAULT '0', 
	PRICE_TAX_OPTION CHAR(1 BYTE) DEFAULT '1'
   ) 
	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;
--------------------------------------------------------
--  DDL for Table EON_ROLES
--------------------------------------------------------

  CREATE TABLE EON_ROLES 
   (	ROLE_ID NUMBER(9,0), 
	ROLE_NAME VARCHAR2(32 CHAR), 
	SELLER_FLAG CHAR(1 BYTE), 
	SELLER_ADMIN_FLAG CHAR(1 BYTE), 
	BUYER_FLAG CHAR(1 BYTE), 
	BUYER_ADMIN_FLAG CHAR(1 BYTE), 
	ADMIN_FLAG CHAR(1 BYTE), 
	CHOUAI_FLAG CHAR(1 BYTE), 
	COMPANY_TYPE_FLAG CHAR(1 BYTE)
   ) 
	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;
--------------------------------------------------------
--  DDL for Table EON_SELLING_UOM
--------------------------------------------------------

  CREATE TABLE EON_SELLING_UOM 
   (	ID_SELLING_UOM NUMBER(9,0), 
	CATEGORY_ID NUMBER(9,0), 
	SELLING_UOM_NAME VARCHAR2(15 CHAR)
   )
	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;
--------------------------------------------------------
--  DDL for Table EON_SHEET_TYPE
--------------------------------------------------------

  CREATE TABLE EON_SHEET_TYPE 
   (	SHEET_TYPE_ID NUMBER(9,0), 
	DESCRIPTION VARCHAR2(70 CHAR), 
	ROLE_ID NUMBER(9,0)
   ) 
	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;
--------------------------------------------------------
--  DDL for Table EON_SHOW_HIDE_COLUMN
--------------------------------------------------------

  CREATE TABLE EON_SHOW_HIDE_COLUMN 
   (	USER_ID NUMBER(9,0), 
	COMPANY_NAME CHAR(1 BYTE) DEFAULT '1', 
	SELLER_NAME CHAR(1 BYTE) DEFAULT '1', 
	GROUP_NAME CHAR(1 BYTE) DEFAULT '1', 
	MARKET_CONDITION CHAR(1 BYTE) DEFAULT '1', 
	SKU_NAME CHAR(1 BYTE) DEFAULT '1', 
	AREA_PRODUCTION CHAR(1 BYTE) DEFAULT '1', 
	CLASS_1 CHAR(1 BYTE) DEFAULT '1', 
	CLASS_2 CHAR(1 BYTE) DEFAULT '1', 
	PRICE_1 CHAR(1 BYTE) DEFAULT '1', 
	PRICE_2 CHAR(1 BYTE) DEFAULT '1', 
	PRICE_WO_TAX CHAR(1 BYTE) DEFAULT '1', 
	PRICE_W_TAX CHAR(1 BYTE) DEFAULT '1', 
	SELLING_PRICE CHAR(1 BYTE) DEFAULT '0', 
	SELLING_UOM CHAR(1 BYTE) DEFAULT '0', 
	PACKAGE_QUANTITY CHAR(1 BYTE) DEFAULT '1', 
	PACKAGE_TYPE CHAR(1 BYTE) DEFAULT '1', 
	UOM CHAR(1 BYTE) DEFAULT '1', 
	SKU_COMMENT CHAR(1 BYTE) DEFAULT '0', 
	SKU_MAX_LIMIT CHAR(1 BYTE) DEFAULT '1', 
	ROW_QTY CHAR(1 BYTE) DEFAULT '1', 
	IS_APPROVED CHAR(1 BYTE) DEFAULT '1', 
	PURCHASE_PRICE CHAR(1 BYTE) DEFAULT '0', 
	COLUMN_01 CHAR(1 BYTE) DEFAULT '0', 
	COLUMN_02 CHAR(1 BYTE) DEFAULT '0', 
	COLUMN_03 CHAR(1 BYTE) DEFAULT '0', 
	COLUMN_04 CHAR(1 BYTE) DEFAULT '0', 
	COLUMN_05 CHAR(1 BYTE) DEFAULT '0', 
	COLUMN_06 CHAR(1 BYTE) DEFAULT '0', 
	COLUMN_07 CHAR(1 BYTE) DEFAULT '0', 
	COLUMN_08 CHAR(1 BYTE) DEFAULT '0', 
	COLUMN_09 CHAR(1 BYTE) DEFAULT '0', 
	COLUMN_10 CHAR(1 BYTE) DEFAULT '0', 
	COLUMN_11 CHAR(1 BYTE) DEFAULT '0', 
	COLUMN_12 CHAR(1 BYTE) DEFAULT '0', 
	COLUMN_13 CHAR(1 BYTE) DEFAULT '0', 
	COLUMN_14 CHAR(1 BYTE) DEFAULT '0', 
	COLUMN_15 CHAR(1 BYTE) DEFAULT '0', 
	COLUMN_16 CHAR(1 BYTE) DEFAULT '0', 
	COLUMN_17 CHAR(1 BYTE) DEFAULT '0', 
	COLUMN_18 CHAR(1 BYTE) DEFAULT '0', 
	COLUMN_19 CHAR(1 BYTE) DEFAULT '0', 
	COLUMN_20 CHAR(1 BYTE) DEFAULT '0', 
	PROFIT_PERCENTAGE CHAR(1 BYTE) DEFAULT '0'
   ) 
	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;
--------------------------------------------------------
--  DDL for Table EON_SKU
--------------------------------------------------------

  CREATE TABLE EON_SKU 
   (	SKU_ID NUMBER(9,0), 
	ORIGINAL_SKU_ID NUMBER(9,0), 
	SELLER_ID NUMBER(9,0), 
	SKU_GROUP_ID NUMBER(9,0), 
	ORDER_UNIT_ID NUMBER(9,0), 
	SHEET_TYPE_ID NUMBER(9,0), 
	SKU_CATEGORY_ID NUMBER(9,0), 
	PROPOSED_BY NUMBER(9,0), 
	SKU_NAME VARCHAR2(128 CHAR), 
	LOCATION VARCHAR2(64 CHAR), 
	MARKET VARCHAR2(64 CHAR), 
	GRADE VARCHAR2(64 CHAR), 
	CLASS VARCHAR2(64 CHAR), 
	PRICE1 NUMBER(9,0), 
	PRICE2 NUMBER(9,0), 
	PRICE_WITHOUT_TAX NUMBER(9,0), 
	PACKAGE_QUANTITY NUMBER(9,0), 
	PACKAGE_TYPE VARCHAR2(64 CHAR), 
	EXTERNAL_SKU_ID VARCHAR2(64 CHAR), 
	SKU_MAX_LIMIT NUMBER(12,3), 
	CREATE_TIMESTAMP TIMESTAMP (6) DEFAULT SYSDATE, 
	CREATED_BY NUMBER(9,0), 
	UPDATE_TIMESTAMP TIMESTAMP (6), 
	UPDATED_BY NUMBER(9,0), 
	SKU_ID_OLD NUMBER(9,0), 
	ORIGINAL_SKU_ID_OLD NUMBER(9,0), 
	COLUMN_01 NUMBER(9,0), 
	COLUMN_02 VARCHAR2(42 CHAR), 
	COLUMN_03 VARCHAR2(42 CHAR), 
	COLUMN_04 VARCHAR2(42 CHAR), 
	COLUMN_05 VARCHAR2(42 CHAR), 
	COLUMN_06 VARCHAR2(42 CHAR), 
	COLUMN_07 VARCHAR2(42 CHAR), 
	COLUMN_08 VARCHAR2(42 CHAR), 
	COLUMN_09 VARCHAR2(42 CHAR), 
	COLUMN_10 VARCHAR2(42 CHAR), 
	COLUMN_11 VARCHAR2(42 CHAR), 
	COLUMN_12 VARCHAR2(42 CHAR), 
	COLUMN_13 VARCHAR2(42 CHAR), 
	COLUMN_14 VARCHAR2(42 CHAR), 
	COLUMN_15 VARCHAR2(42 CHAR), 
	COLUMN_16 VARCHAR2(42 CHAR), 
	COLUMN_17 VARCHAR2(42 CHAR), 
	COLUMN_18 VARCHAR2(42 CHAR), 
	COLUMN_19 VARCHAR2(42 CHAR), 
	COLUMN_20 VARCHAR2(42 CHAR)
   ) 
	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;
--------------------------------------------------------
--  DDL for Table EON_SKU_BA
--------------------------------------------------------

  CREATE TABLE EON_SKU_BA 
   (	SKU_BA_ID NUMBER(9,0), 
	FK_SKU_ID NUMBER(9,0), 
	PURCHASE_PRICE NUMBER(9,0), 
	SELLING_PRICE NUMBER(9,0), 
	FK_ID_SELLING_UOM NUMBER(9,0), 
	SKU_COMMENT VARCHAR2(50 CHAR)
   ) 
	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;
--------------------------------------------------------
--  DDL for Table EON_SKU_COLUMN
--------------------------------------------------------

  CREATE TABLE EON_SKU_COLUMN 
   (	SKU_COLUMN_ID NUMBER(9,0), 
	HEADER_KEY VARCHAR2(42 CHAR), 
	HEADER_CSV VARCHAR2(42 CHAR), 
	HEADER_SHEET VARCHAR2(10 CHAR)
   ) 
	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;
--------------------------------------------------------
--  DDL for Table EON_SKU_GROUP
--------------------------------------------------------

  CREATE TABLE EON_SKU_GROUP 
   (	SKU_GROUP_ID NUMBER(9,0), 
	ORIG_SKU_GROUP_ID NUMBER(9,0), 
	SELLER_ID NUMBER(9,0), 
	SKU_CATEGORY_ID NUMBER(9,0), 
	DESCRIPTION VARCHAR2(50 CHAR), 
	START_DATE CHAR(8 BYTE), 
	END_DATE CHAR(8 BYTE), 
	CREATE_TIMESTAMP TIMESTAMP (6) DEFAULT SYSDATE, 
	CREATED_BY NUMBER(9,0), 
	UPDATE_TIMESTAMP TIMESTAMP (6), 
	UPDATED_BY NUMBER(9,0), 
	SKU_GROUP_ID_OLD NUMBER(9,0)
   ) 
	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;
--------------------------------------------------------
--  DDL for Table EON_SORT_BUYERS
--------------------------------------------------------

  CREATE TABLE EON_SORT_BUYERS 
   (	USER_ID NUMBER(9,0), 
	BUYER_ID NUMBER(9,0), 
	SORTING NUMBER(4,0), 
	CREATE_TIMESTAMP TIMESTAMP (6) DEFAULT SYSDATE, 
	CREATED_BY NUMBER(9,0), 
	UPDATE_TIMESTAMP TIMESTAMP (6), 
	UPDATED_BY NUMBER(9,0)
   ) 
	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;
--------------------------------------------------------
--  DDL for Table EON_SORT_COMPANY_BUYERS
--------------------------------------------------------

  CREATE TABLE EON_SORT_COMPANY_BUYERS 
   (	USER_ID NUMBER(9,0), 
	COMPANY_ID NUMBER(9,0), 
	SORTING NUMBER(4,0), 
	CREATE_TIMESTAMP TIMESTAMP (6) DEFAULT SYSDATE, 
	CREATED_BY NUMBER(9,0), 
	UPDATE_TIMESTAMP TIMESTAMP (6), 
	UPDATED_BY NUMBER(9,0)
   )
	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;
--------------------------------------------------------
--  DDL for Table EON_SORT_COMPANY_SELLERS
--------------------------------------------------------

  CREATE TABLE EON_SORT_COMPANY_SELLERS 
   (	USER_ID NUMBER(9,0), 
	COMPANY_ID NUMBER(9,0), 
	SORTING NUMBER(4,0), 
	CREATE_TIMESTAMP TIMESTAMP (6) DEFAULT SYSDATE, 
	CREATED_BY NUMBER(9,0), 
	UPDATE_TIMESTAMP TIMESTAMP (6), 
	UPDATED_BY NUMBER(9,0)
   ) 
	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;
--------------------------------------------------------
--  DDL for Table EON_SORT_SELLERS
--------------------------------------------------------

  CREATE TABLE EON_SORT_SELLERS 
   (	USER_ID NUMBER(9,0), 
	SELLER_ID NUMBER(9,0), 
	SORTING NUMBER(4,0), 
	CREATE_TIMESTAMP TIMESTAMP (6) DEFAULT SYSDATE, 
	CREATED_BY NUMBER(9,0), 
	UPDATE_TIMESTAMP TIMESTAMP (6), 
	UPDATED_BY NUMBER(9,0)
   )
	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;
--------------------------------------------------------
--  DDL for Table EON_SORT_SKU
--------------------------------------------------------

  CREATE TABLE EON_SORT_SKU 
   (	USER_ID NUMBER(9,0), 
	SKU_COLUMN_IDS VARCHAR2(256 CHAR), 
	CREATE_TIMESTAMP TIMESTAMP (6) DEFAULT SYSDATE, 
	CREATED_BY NUMBER(9,0), 
	UPDATE_TIMESTAMP TIMESTAMP (6), 
	UPDATED_BY NUMBER(9,0)
   )
   	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;
--------------------------------------------------------
--  DDL for Table EON_SORT_SKU_GROUP
--------------------------------------------------------

  CREATE TABLE EON_SORT_SKU_GROUP 
   (	USER_ID NUMBER(9,0), 
	SKU_GROUP_ID NUMBER(9,0), 
	SKU_CATEGORY_ID NUMBER(9,0), 
	SORTING NUMBER(4,0), 
	CREATE_TIMESTAMP TIMESTAMP (6) DEFAULT SYSDATE, 
	CREATED_BY NUMBER(9,0), 
	UPDATE_TIMESTAMP TIMESTAMP (6), 
	UPDATED_BY NUMBER(9,0), 
	USER_ID_OLD NUMBER(9,0), 
	SKU_GROUP_ID_OLD NUMBER(9,0)
   ) 
	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;

--------------------------------------------------------
--  DDL for Table EON_USER_DEALING_PATTERN
--------------------------------------------------------

  CREATE TABLE EON_USER_DEALING_PATTERN 
   (	USER_DEALING_PATTERN_ID NUMBER(9,0), 
	DEALING_PATTERN_RELATION_ID NUMBER(9,0), 
	COMPANY_DEALING_PATTERN_ID NUMBER(9,0), 
	USER_01 NUMBER(9,0), 
	USER_02 NUMBER(9,0), 
	START_DATE CHAR(8 BYTE), 
	END_DATE CHAR(8 BYTE), 
	CREATE_TIMESTAMP TIMESTAMP (6) DEFAULT SYSDATE, 
	CREATED_BY NUMBER(9,0), 
	UPDATE_TIMESTAMP TIMESTAMP (6), 
	UPDATED_BY NUMBER(9,0), 
	USER_DEALING_PATTERN_ID_OLD NUMBER(9,0)
   ) 
	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;
--------------------------------------------------------
--  DDL for Table EON_USER_PREF_FLAGS
--------------------------------------------------------

  CREATE TABLE EON_USER_PREF_FLAGS 
   (	USER_PREF_ID NUMBER(9,0), 
	USER_ID NUMBER(9,0), 
	VIEW_UNPUBLISH_ALLOC CHAR(1 BYTE) DEFAULT NULL, 
	VIEW_UNFINALIZE_BILLING CHAR(1 BYTE) DEFAULT NULL, 
	ENABLE_BA_PUBLISH_ORDER CHAR(1 BYTE) DEFAULT NULL, 
	CREATE_TIMESTAMP TIMESTAMP (6) DEFAULT SYSDATE, 
	CREATED_BY NUMBER(9,0), 
	UPDATE_TIMESTAMP TIMESTAMP (6), 
	UPDATED_BY NUMBER(9,0), 
	AUTO_PUBLISH_ALLOC CHAR(1 BYTE) DEFAULT 0, 
	DISPLAY_ALLOC_QTY CHAR(1 BYTE) DEFAULT 0, 
	UNFINALIZE_RECEIVED CHAR(1 BYTE) DEFAULT 0
   )
	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;
--------------------------------------------------------
--  DDL for Table EON_USERS
--------------------------------------------------------

  CREATE TABLE EON_USERS 
   (	USER_ID NUMBER(9,0), 
	COMPANY_ID NUMBER(9,0), 
	ROLE_ID NUMBER(9,0), 
	USERNAME VARCHAR2(32 CHAR), 
	PASSWORD VARCHAR2(32 CHAR), 
	NAME VARCHAR2(96 CHAR), 
	SHORTNAME VARCHAR2(96 CHAR), 
	ADDRESS1 VARCHAR2(100 CHAR), 
	ADDRESS2 VARCHAR2(100 CHAR), 
	ADDRESS3 VARCHAR2(100 CHAR), 
	MOBILE_NUMBER VARCHAR2(20 CHAR), 
	TELEPHONE_NUMBER VARCHAR2(20 CHAR), 
	FAX_NUMBER VARCHAR2(20 CHAR), 
	MOBILE_EMAIL_ADDRESS VARCHAR2(50 CHAR), 
	PC_EMAIL_ADDRESS VARCHAR2(50 CHAR), 
	COMMENTS VARCHAR2(1000 CHAR), 
	USE_BMS CHAR(1 BYTE), 
	DATE_LAST_LOGIN TIMESTAMP (6), 
	DATE_PASSWORD_CHANGE TIMESTAMP (6), 
	CREATE_TIMESTAMP TIMESTAMP (6) DEFAULT SYSDATE, 
	CREATED_BY NUMBER(9,0), 
	UPDATE_TIMESTAMP TIMESTAMP (6), 
	UPDATED_BY NUMBER(9,0), 
	USER_ID_OLD NUMBER(9,0)
   ) 
	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;
--------------------------------------------------------
--  DDL for Table EON_USERS_CATEGORY
--------------------------------------------------------

  CREATE TABLE EON_USERS_CATEGORY 
   (	USER_ID NUMBER(9,0), 
	CATEGORY_ID NUMBER(9,0), 
	SORT_ID NUMBER(1,0)
   ) 
	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;
--------------------------------------------------------
--  DDL for Table EON_ZIP_CODE
--------------------------------------------------------

  CREATE TABLE EON_ZIP_CODE 
   (	ZIP_CODE NUMBER(7,0), 
	ADDRESS VARCHAR2(50 CHAR)
   )
	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;
--------------------------------------------------------
--  DDL for Table MIGRATION_DEALING_PATTERN
--------------------------------------------------------

  CREATE TABLE MIGRATION_DEALING_PATTERN 
   (	BATCH_NUMBER NUMBER(9,0), 
	USER1 VARCHAR2(32 BYTE), 
	USER2 VARCHAR2(32 BYTE), 
	RELATION VARCHAR2(128 BYTE), 
	MIGRATION_STATUS CHAR(1 BYTE) DEFAULT 0, 
	MIGRATION_DATE DATE DEFAULT sysdate
   ) 
	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;
--------------------------------------------------------
--  DDL for Table MIGRATION_LOG_AUDIT
--------------------------------------------------------

  CREATE TABLE MIGRATION_LOG_AUDIT 
   (	BATCH_NUMBER NUMBER(9,0), 
	PROCEDURE_NAME VARCHAR2(32 BYTE), 
	PARAMETER_01 VARCHAR2(32 BYTE), 
	PARAMETER_02 VARCHAR2(32 BYTE), 
	TIME_START TIMESTAMP (6), 
	TIME_FINISH TIMESTAMP (6), 
	TIME_DURATION INTERVAL DAY (3) TO SECOND (3)
   ) 
	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;
--------------------------------------------------------
--  DDL for Table MIGRATION_USER
--------------------------------------------------------

  CREATE TABLE MIGRATION_USER 
   (	BATCH_NUMBER NUMBER(9,0), 
	USERNAME VARCHAR2(32 BYTE), 
	PASSWORD VARCHAR2(32 BYTE), 
	COMPANY_NAME VARCHAR2(200 BYTE), 
	MIGRATION_STATUS CHAR(1 BYTE) DEFAULT 0, 
	MIGRATION_DATE DATE DEFAULT sysdate
   ) 
	TABLESPACE EON_DATA1 LOGGING 
	NOCOMPRESS 
	NOCACHE
	NOPARALLEL
	MONITORING;

