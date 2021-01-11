CREATE TABLE EON_ADMIN_MEMBER
(
  ADMIN_MEMBER_ID              NUMBER(9),
  ADMIN_ID                     NUMBER(9),
  MEMBER_ID                    NUMBER(9),
  DEALING_PATTERN_RELATION_ID  NUMBER(9),
  START_DATE                   CHAR(8 BYTE)     NOT NULL,
  END_DATE                     CHAR(8 BYTE),
  CREATE_TIMESTAMP             TIMESTAMP(6)     DEFAULT SYSDATE,
  CREATED_BY                   NUMBER(9),
  UPDATE_TIMESTAMP             TIMESTAMP(6),
  UPDATED_BY                   NUMBER(9)
)
TABLESPACE EON_DATA1 LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;


CREATE TABLE EON_AKADEN_SKU
(
  AKADEN_SKU_ID      NUMBER(9)                  NOT NULL,
  SKU_ID             NUMBER(9),
  SELLER_ID          NUMBER(9),
  SKU_CATEGORY_ID    NUMBER(9),
  SHEET_TYPE_ID      NUMBER(9),
  SKU_GROUP_ID       NUMBER(9),
  ORDER_UNIT_ID      NUMBER(9),
  PROPOSED_BY        NUMBER(9),
  SKU_NAME           VARCHAR2(64 CHAR),
  TYPE_FLAG          CHAR(1 BYTE),
  LOCATION           VARCHAR2(42 CHAR),
  MARKET             VARCHAR2(42 CHAR),
  GRADE              VARCHAR2(42 CHAR),
  CLASS              VARCHAR2(42 CHAR),
  PRICE1             NUMBER(9),
  PRICE2             NUMBER(9),
  PRICE_WITHOUT_TAX  NUMBER(9),
  PACKAGE_QUANTITY   NUMBER(9),
  PACKAGE_TYPE       VARCHAR2(42 CHAR),
  CREATE_TIMESTAMP   TIMESTAMP(6)     DEFAULT SYSDATE,
  CREATED_BY         NUMBER(9),
  UPDATE_TIMESTAMP   TIMESTAMP(6),
  UPDATED_BY         NUMBER(9)
)
TABLESPACE EON_DATA1 LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;


CREATE TABLE EON_CATEGORY
(
  SKU_CATEGORY_ID  NUMBER(9),
  DESCRIPTION      VARCHAR2(50 CHAR)
)
TABLESPACE EON_DATA1 LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;


CREATE TABLE EON_COMMENTS_INBOX
(
  COMMENTS_INBOX_ID    NUMBER(9),
  SENDER_ENTITY_ID     NUMBER(9),
  RECIPIENT_ENTITY_ID  NUMBER(9),
  RECEIVED_DATE        TIMESTAMP(6),
  COMMENT_SUBJECT      VARCHAR2(4000 CHAR),
  COMMENT_MESSAGE      CLOB,
  OPEN_STATUS          CHAR(1 BYTE),
  CREATE_TIMESTAMP     TIMESTAMP(6)     DEFAULT SYSDATE,
  CREATE_USER_ID       NUMBER(9),
  UPDATE_TIMESTAMP     TIMESTAMP(6),
  UPDATED_BY           NUMBER(9)
)
TABLESPACE EON_DATA1 LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;


CREATE TABLE EON_COMMENTS_OUTBOX
(
  COMMENTS_OUTBOX_ID  NUMBER(9),
  SENDER_ENTITY_ID    NUMBER(9),
  SENT_DATE           TIMESTAMP(6),
  RECIPIENTS_ADDRESS  VARCHAR2(1024 CHAR),
  COMMENT_SUBJECT     VARCHAR2(4000 CHAR),
  COMMENT_MESSAGE     CLOB,
  CREATE_TIMESTAMP    TIMESTAMP(6)     DEFAULT SYSDATE,
  CREATE_USER_ID      NUMBER(9),
  UPDATE_TIMESTAMP    TIMESTAMP(6),
  UPDATED_BY          NUMBER(9)
)
TABLESPACE EON_DATA1 LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;


CREATE TABLE EON_COMPANY
(
  COMPANY_ID        NUMBER(9),
  COMPANY_NAME      VARCHAR2(50 CHAR)           NOT NULL,
  SHORT_NAME        VARCHAR2(50 CHAR)           NOT NULL,
  COMPANY_TYPE_ID   NUMBER(9)           		NOT NULL,
  CONTACT_PERSON    VARCHAR2(50 CHAR),
  SOX_FLAG          CHAR(1 BYTE)                NOT NULL,
  COMPANY_ADDRESS1  VARCHAR2(100 CHAR),
  COMPANY_ADDRESS2  VARCHAR2(100 CHAR),
  COMPANY_ADDRESS3  VARCHAR2(100 CHAR),
  TELEPHONE_NUMBER  VARCHAR2(20 CHAR),
  FAX_NUMBER        VARCHAR2(20 CHAR),
  EMAIL_ADDRESS     VARCHAR2(50 CHAR),
  COMMENTS          VARCHAR2(1000 CHAR),
  CREATE_TIMESTAMP  TIMESTAMP(6)     DEFAULT SYSDATE,
  CREATED_BY        NUMBER(9),
  UPDATE_TIMESTAMP  TIMESTAMP(6),
  UPDATED_BY        NUMBER(9)
)
TABLESPACE EON_DATA1 LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;


CREATE TABLE EON_COMPANY_TYPE
(
  COMPANY_TYPE_ID  			   NUMBER(9),
  DESCRIPTION                  VARCHAR2(50 CHAR)
)
TABLESPACE EON_DATA1 LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;


CREATE TABLE EON_COMPANY_DEALING_PATTERN
(
  COMPANY_DEALING_PATTERN_ID   NUMBER(9),
  DEALING_PATTERN_RELATION_ID  NUMBER(9),
  COMPANY_01                   NUMBER(9),
  COMPANY_02                   NUMBER(9),
  ISACTIVE                     CHAR(1 BYTE)     DEFAULT '1'                   NOT NULL,
  CREATE_TIMESTAMP             TIMESTAMP(6)     DEFAULT SYSDATE,
  CREATED_BY                   NUMBER(9),
  UPDATE_TIMESTAMP             TIMESTAMP(6),
  UPDATED_BY                   NUMBER(9)
)
TABLESPACE EON_DATA1 LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;


CREATE TABLE EON_DEALING_PATTERN_RELATION
(
  DEALING_PATTERN_RELATION_ID  NUMBER(9),
  DESCRIPTION                  VARCHAR2(50 CHAR)
)
TABLESPACE EON_DATA1 LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;


CREATE TABLE EON_LOGIN_INQUIRY_REQUEST
(
  INQUIRY_REQUEST_ID              NUMBER(9),
  INQUIRY_ITEMS_EON               CHAR(1 BYTE),
  INQUIRY_ITEMS_NSYSTEM           CHAR(1 BYTE),
  INQUIRY_ITEMS_OTHERS            CHAR(1 BYTE),
  DETAILS_OF_INQUIRY_APPLY        CHAR(1 BYTE),
  DETAILS_OF_INQUIRY_DOCS         CHAR(1 BYTE),
  DETAILS_OF_INQUIRY_EXPLANATION  CHAR(1 BYTE),
  DETAILS_OF_INQUIRY_OTHERS       CHAR(1 BYTE),
  NAME_FIRST                      VARCHAR2(50 CHAR),
  NAME_LAST                       VARCHAR2(50 CHAR),
  FURIGANA_FIRST                  VARCHAR2(50 CHAR),
  FURIGANA_LAST                   VARCHAR2(50 CHAR),
  COMPANY_NAME                    VARCHAR2(50 CHAR),
  STORE_NAME                      VARCHAR2(50 CHAR),
  DEPARTMENT_NAME                 VARCHAR2(50 CHAR),
  TELEPHONE_NUMBER                NUMBER(11),
  MOBILE_NUMBER                   NUMBER(11),
  EMAIL_ADDRESS                   VARCHAR2(50 CHAR),
  ZIPCODE                         NUMBER(7),
  ADDRESS                         VARCHAR2(50 CHAR),
  DATE_CREATED                    TIMESTAMP(6)
)
TABLESPACE EON_DATA1 LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;


CREATE TABLE EON_ORDER
(
  ORDER_ID                   NUMBER(9),
  BUYER_ID                   NUMBER(9),
  SELLER_ID                  NUMBER(9),
  DELIVERY_DATE              CHAR(8 BYTE),
  ORDER_SAVED_BY             NUMBER(9),
  ORDER_PUBLISHED_BY         NUMBER(9),
  ORDER_FINALIZED_BY         NUMBER(9),
  ORDER_UNFINALIZED_BY       NUMBER(9),
  ORDER_LOCKED_BY            NUMBER(9),
  ORDER_UNLOCKED_BY          NUMBER(9),
  ALLOCATION_SAVED_BY        NUMBER(9),
  ALLOCATION_PUBLISHED_BY    NUMBER(9),
  ALLOCATION_FINALIZED_BY    NUMBER(9),
  ALLOCATION_UNFINALIZED_BY  NUMBER(9),
  RECEIVED_APPROVED_BY       NUMBER(9),
  RECEIVED_UNAPPROVED_BY     NUMBER(9),
  RECEIVED_SAVED_BY          NUMBER(9),
  BILLING_SAVED_BY           NUMBER(9),
  BILLING_FINALIZED_BY       NUMBER(9),
  BILLING_UNFINALIZED_BY     NUMBER(9),
  AKADEN_SAVED_BY            NUMBER(9),
  ORDER_PUBLISHED_BY_BA      NUMBER(9),
  LAST_SAVED_OS_TIMESTAMP    TIMESTAMP(6),
  COPIED_FROM_ORDER_ID		 NUMBER(9),
  COPIED_FROM_TIMESTAMP      TIMESTAMP(6),
  DATE_CREATED               TIMESTAMP(6)	     DEFAULT SYSDATE,
  CREATED_BY                 NUMBER(9),
  DATE_UPDATED               TIMESTAMP(6),
  UPDATED_BY                 NUMBER(9)
)
TABLESPACE EON_DATA1 LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;


CREATE TABLE EON_ORDER_AKADEN
(
  ORDER_AKADEN_ID   NUMBER(9),
  ORDER_ID          NUMBER(9),
  AKADEN_SKU_ID     NUMBER(9),
  COMMENTS          VARCHAR2(50 CHAR),
  ISNEWSKU          CHAR(1 BYTE),
  QUANTITY          NUMBER(12,3),
  TOTAL_QUANTITY    NUMBER(16,3),
  UNIT_PRICE        NUMBER(12),
  CREATE_TIMESTAMP  TIMESTAMP(6)     DEFAULT SYSDATE,
  CREATED_BY        NUMBER(9),
  UPDATE_TIMESTAMP  TIMESTAMP(6),
  UPDATE_BY         NUMBER(9)
)
TABLESPACE EON_DATA1 LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;


CREATE TABLE EON_ORDER_ALLOCATION
(
  ORDER_ALLOCATION_ID  NUMBER(9),
  ORDER_ID             NUMBER(9),
  SKU_ID               NUMBER(9),
  QUANTITY             NUMBER(12,3),
  CREATE_TIMESTAMP     TIMESTAMP(6)     DEFAULT SYSDATE,
  CREATED_BY           NUMBER(9),
  UPDATE_TIMESTAMP     TIMESTAMP(6),
  UPDATED_BY           NUMBER(9)
)
TABLESPACE EON_DATA1 LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;


CREATE TABLE EON_ORDER_BILLING
(
  ORDER_BILLING_ID  NUMBER(9),
  ORDER_ID          NUMBER(9),
  SKU_ID            NUMBER(9),
  QUANTITY          NUMBER(12,3),
  COMMENTS          VARCHAR2(50 CHAR),
  CREATE_TIMESTAMP  TIMESTAMP(6)     DEFAULT SYSDATE,
  CREATED_BY        NUMBER(9),
  UPDATE_TIMESTAMP  TIMESTAMP(6),
  UPDATED_BY        NUMBER(9)
)
TABLESPACE EON_DATA1 LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;


CREATE TABLE EON_ORDER_ITEM
(
  ORDER_ITEM_ID       NUMBER(9),
  ORDER_ID            NUMBER(9),
  SKU_ID              NUMBER(9),
  QUANTITY            NUMBER(12,3),
  SOS_VIS_FLAG        CHAR(1 BYTE)              DEFAULT '1'                   NOT NULL,
  BAOS_VIS_FLAG       CHAR(1 BYTE)              DEFAULT '1'                   NOT NULL,
  CREATE_TIMESTAMP    TIMESTAMP(6)              DEFAULT SYSDATE,
  CREATED_BY          NUMBER(9),
  UPDATE_TIMESTAMP    TIMESTAMP(6),
  UPDATE_BY_USERS_ID  NUMBER(9)
)
TABLESPACE EON_DATA1 LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;


CREATE TABLE EON_ORDER_RECEIVED
(
  ORDER_RECEIVED_ID  NUMBER(9),
  ORDER_ID           NUMBER(9),
  SKU_ID             NUMBER(9),
  QUANTITY           NUMBER(12,3),
  ISAPPROVED         CHAR(1 BYTE),
  CREATE_TIMESTAMP   TIMESTAMP(6)     DEFAULT SYSDATE,
  CREATED_BY         NUMBER(9),
  UPDATE_TIMESTAMP   TIMESTAMP(6),
  UPDATED_BY         NUMBER(9)
)
TABLESPACE EON_DATA1 LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;


CREATE TABLE EON_ORDER_UNIT
(
  ORDER_UNIT_ID    NUMBER(9)                    NOT NULL,
  CATEGORY_ID      NUMBER(9),
  ORDER_UNIT_NAME  VARCHAR2(15 CHAR)            NOT NULL
)
TABLESPACE EON_DATA1 LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;


CREATE TABLE EON_PRODUCT_MASTER_FILE
(
  PROD_MASTER_ID  NUMBER(9),
  USER_ID         NUMBER(9),
  NAME            VARCHAR2(32 CHAR)
)
TABLESPACE EON_DATA1 LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;


CREATE TABLE EON_PRODUCT_MASTER_FILE_SKU
(
  PROD_MASTER_ID       NUMBER(9),
  SKU_ID               NUMBER(9),
  SELLER_ID			   NUMBER(9),
  SKU_CATEGORY_ID      NUMBER(9),
  SKU_GROUP_ID         NUMBER(9),
  ORDER_UNIT_ID        NUMBER(9),
  SKU_NAME             VARCHAR2(64 CHAR),
  SELLER_PRODUCT_CODE  VARCHAR2(32 CHAR),
  BUYER_PRODUCT_CODE   VARCHAR2(32 CHAR),
  COMMENTA             VARCHAR2(64 CHAR),
  COMMENTB             VARCHAR2(64 CHAR),
  LOCATION             VARCHAR2(42 CHAR),
  MARKET               VARCHAR2(42 CHAR),
  GRADE                VARCHAR2(42 CHAR),
  CLASS                VARCHAR2(42 CHAR),
  PRICE1               NUMBER(9),
  PRICE2               NUMBER(9),
  PRICE_WITHOUT_TAX    NUMBER(9),
  PACKAGE_QUANTITY     NUMBER(9),
  PACKAGE_TYPE         VARCHAR2(42 CHAR),
  CREATE_TIMESTAMP     TIMESTAMP(6)     DEFAULT SYSDATE,
  CREATED_BY           NUMBER(9),
  UPDATE_TIMESTAMP     TIMESTAMP(6),
  UPDATED_BY           NUMBER(9)
)
TABLESPACE EON_DATA1 LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;


CREATE TABLE EON_ROLES
(
  ROLE_ID            NUMBER(9),
  ROLE_NAME          VARCHAR2(32 CHAR),
  SELLER_FLAG        CHAR(1 BYTE),
  SELLER_ADMIN_FLAG  CHAR(1 BYTE),
  BUYER_FLAG         CHAR(1 BYTE),
  BUYER_ADMIN_FLAG   CHAR(1 BYTE),
  ADMIN_FLAG         CHAR(1 BYTE),
  CHOUAI_FLAG        CHAR(1 BYTE),
  COMPANY_TYPE_FLAG  CHAR(1 BYTE)
)
TABLESPACE EON_DATA1 LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;


CREATE TABLE EON_SHEET_TYPE
(
  SHEET_TYPE_ID  NUMBER(9),
  DESCRIPTION    VARCHAR2(70 CHAR),
  ROLE_ID        NUMBER(9)
)
TABLESPACE EON_DATA1 LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;


CREATE TABLE EON_SKU
(
  SKU_ID             NUMBER(9),
  ORIGINAL_SKU_ID    NUMBER(9),
  SELLER_ID          NUMBER(9)                  NOT NULL,
  SKU_GROUP_ID       NUMBER(9)                  NOT NULL,
  ORDER_UNIT_ID      NUMBER(9)                  NOT NULL,
  SHEET_TYPE_ID      NUMBER(9),
  SKU_CATEGORY_ID    NUMBER(9),
  PROPOSED_BY        NUMBER(9),
  SKU_NAME           VARCHAR2(64 CHAR)          NOT NULL,
  LOCATION           VARCHAR2(42 CHAR),
  MARKET             VARCHAR2(42 CHAR),
  GRADE              VARCHAR2(42 CHAR),
  CLASS              VARCHAR2(42 CHAR),
  PRICE1             NUMBER(9),
  PRICE2             NUMBER(9),
  PRICE_WITHOUT_TAX  NUMBER(9),
  PACKAGE_QUANTITY   NUMBER(9)					NOT NULL,
  PACKAGE_TYPE       VARCHAR2(42 CHAR),
  EXTERNAL_SKU_ID    VARCHAR2(42 CHAR),
  SKU_MAX_LIMIT		 NUMBER(12,3),
  CREATE_TIMESTAMP   TIMESTAMP(6)     DEFAULT SYSDATE,
  CREATED_BY         NUMBER(9),
  UPDATE_TIMESTAMP   TIMESTAMP(6),
  UPDATED_BY         NUMBER(9)
)
TABLESPACE EON_DATA1 LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;


CREATE TABLE EON_SKU_COLUMN
(
  SKU_COLUMN_ID      NUMBER(9),
  SKU_COLUMN_KEY     VARCHAR2(64 CHAR),
  SKU_COLUMN_NAME    VARCHAR2(64 CHAR),
  SKU_COLUMN_NAME_J  VARCHAR2(128 CHAR)
)
TABLESPACE EON_DATA1 LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;


CREATE TABLE EON_SKU_GROUP
(
  SKU_GROUP_ID       NUMBER(9),
  ORIG_SKU_GROUP_ID  NUMBER(9),
  SELLER_ID  		 NUMBER(9),
  SKU_CATEGORY_ID    NUMBER(9),
  DESCRIPTION        VARCHAR2(50 CHAR),
  START_DATE         CHAR(8 BYTE),
  END_DATE           CHAR(8 BYTE),
  CREATE_TIMESTAMP   TIMESTAMP(6)     DEFAULT SYSDATE,
  CREATED_BY         NUMBER(9),
  UPDATE_TIMESTAMP   TIMESTAMP(6),
  UPDATED_BY         NUMBER(9)
)
TABLESPACE EON_DATA1 LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;


CREATE TABLE EON_SORT_BUYERS
(
  USER_ID     		 NUMBER(9),
  BUYER_ID    		 NUMBER(9),
  SORTING     		 NUMBER(4),
  CREATE_TIMESTAMP   TIMESTAMP(6)     DEFAULT SYSDATE,
  CREATED_BY         NUMBER(9),
  UPDATE_TIMESTAMP   TIMESTAMP(6),
  UPDATED_BY         NUMBER(9)
)
TABLESPACE EON_DATA1 LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;


CREATE TABLE EON_SORT_COMPANY_BUYERS
(
  USER_ID     			NUMBER(9)		NOT NULL,
  COMPANY_ID  			NUMBER(9),
  SORTING     			NUMBER(4),
  CREATE_TIMESTAMP      TIMESTAMP(6)     DEFAULT SYSDATE,
  CREATED_BY            NUMBER(9),
  UPDATE_TIMESTAMP      TIMESTAMP(6),
  UPDATED_BY            NUMBER(9)
)
TABLESPACE EON_DATA1 LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;


CREATE TABLE EON_SORT_SKU
(
  USER_ID            NUMBER(9),
  SKU_COLUMN_IDS     VARCHAR2(256 CHAR),
  CREATE_TIMESTAMP   TIMESTAMP(6)     DEFAULT SYSDATE,
  CREATED_BY         NUMBER(9),
  UPDATE_TIMESTAMP   TIMESTAMP(6),
  UPDATED_BY         NUMBER(9)
)
TABLESPACE EON_DATA1 LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;


CREATE TABLE EON_SORT_SKU_GROUP
(
  USER_ID            NUMBER(9),
  SKU_GROUP_ID       NUMBER(9),
  SKU_CATEGORY_ID    NUMBER(9),
  SORTING            NUMBER(4),
  CREATE_TIMESTAMP   TIMESTAMP(6)     DEFAULT SYSDATE,
  CREATED_BY         NUMBER(9),
  UPDATE_TIMESTAMP   TIMESTAMP(6),
  UPDATED_BY         NUMBER(9)
)
TABLESPACE EON_DATA1 LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;


CREATE TABLE EON_USERS
(
  USER_ID               NUMBER(9),
  COMPANY_ID            NUMBER(9),
  ROLE_ID               NUMBER(9),
  USERNAME              VARCHAR2(32 CHAR)       NOT NULL,
  PASSWORD              VARCHAR2(32 CHAR)       NOT NULL,
  NAME                  VARCHAR2(96 CHAR)       NOT NULL,
  SHORTNAME             VARCHAR2(96 CHAR)       NOT NULL,
  ADDRESS1              VARCHAR2(100 CHAR),
  ADDRESS2              VARCHAR2(100 CHAR),
  ADDRESS3              VARCHAR2(100 CHAR),
  MOBILE_NUMBER         VARCHAR2(20 CHAR),
  TELEPHONE_NUMBER      VARCHAR2(20 CHAR),
  FAX_NUMBER            VARCHAR2(20 CHAR),
  MOBILE_EMAIL_ADDRESS  VARCHAR2(50 CHAR),
  PC_EMAIL_ADDRESS      VARCHAR2(50 CHAR),
  COMMENTS              VARCHAR2(1000 CHAR),
  USE_BMS               CHAR(1 BYTE)            NOT NULL,
  DATE_LAST_LOGIN       TIMESTAMP(6),
  DATE_PASSWORD_CHANGE  TIMESTAMP(6),
  CREATE_TIMESTAMP      TIMESTAMP(6)     DEFAULT SYSDATE,
  CREATED_BY            NUMBER(9),
  UPDATE_TIMESTAMP      TIMESTAMP(6),
  UPDATED_BY            NUMBER(9)
)
TABLESPACE EON_DATA1 LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;


CREATE TABLE EON_USERS_CATEGORY
(
  USER_ID             NUMBER(9),
  CATEGORY_ID  		  NUMBER(9)
)
TABLESPACE EON_DATA1 LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;


CREATE TABLE EON_USER_DEALING_PATTERN
(
  USER_DEALING_PATTERN_ID      NUMBER(9),
  DEALING_PATTERN_RELATION_ID  NUMBER(9),
  COMPANY_DEALING_PATTERN_ID   NUMBER(9),
  USER_01                      NUMBER(9),
  USER_02                      NUMBER(9),
  START_DATE                   CHAR(8 BYTE)     NOT NULL,
  END_DATE                     CHAR(8 BYTE),
  CREATE_TIMESTAMP             TIMESTAMP(6)     DEFAULT SYSDATE,
  CREATED_BY                   NUMBER(9),
  UPDATE_TIMESTAMP             TIMESTAMP(6),
  UPDATED_BY                   NUMBER(9)
)
TABLESPACE EON_DATA1 LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;


CREATE TABLE EON_USER_PREF_FLAGS
(
  USER_PREF_ID             NUMBER(9),
  USER_ID                  NUMBER(9),
  VIEW_UNPUBLISH_ALLOC     CHAR(1 BYTE)         DEFAULT NULL,
  VIEW_UNFINALIZE_BILLING  CHAR(1 BYTE)         DEFAULT NULL,
  ENABLE_BA_PUBLISH_ORDER  CHAR(1 BYTE)         DEFAULT NULL,
  CREATE_TIMESTAMP         TIMESTAMP(6)         DEFAULT SYSDATE,
  CREATED_BY               NUMBER(9),
  UPDATE_TIMESTAMP         TIMESTAMP(6),
  UPDATED_BY               NUMBER(9)
)
TABLESPACE EON_DATA1 LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;

CREATE TABLE EON_FORGOT_PASSWORD_LINK
(
  FORGOT_PASSWORD_LINK_ID   NUMBER(9),   
  USER_ID                   NUMBER(9), 
  STATUS                    NUMBER(1)
)
TABLESPACE EON_DATA1 LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;

CREATE TABLE EON_EXCEL_SETTING
(  USER_ID                 NUMBER(9)            NOT NULL,
   WEEKLY_FLAG             CHAR(1 BYTE),
   DATE_OPTION             CHAR(1 BYTE),
   SELLER_OPTION           CHAR(1 BYTE),
   BUYER_OPTION            CHAR(1 BYTE),
   HAS_QTY                 CHAR(1 BYTE),
   PRICE_FLAG              CHAR(1 BYTE),
   PRICE_COMPUTE_OPTION    CHAR(1 BYTE)
)
TABLESPACE EON_DATA1 LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;

CREATE TABLE EON_ZIP_CODE
(
  ZIP_CODE				    NUMBER(7),   
  ADDRESS			        VARCHAR2(50 CHAR)
)
TABLESPACE EON_DATA1 LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;

CREATE TABLE EON_AUDIT_TRAIL
(
  ID              NUMBER(9)               NOT NULL,  
  USER_ID         NUMBER(9)               NOT NULL,
  USERNAME        VARCHAR2(32 CHAR)       NOT NULL,
  PROCESS_ID      NUMBER(9)               NOT NULL,
  LOG_TYPE        VARCHAR2(16 CHAR)       NOT NULL,
  IP_ADDRESS      VARCHAR2(15 CHAR),
  URL_PATH        VARCHAR2(300 CHAR)      NOT NULL,
  URL_REFERER     VARCHAR2(300 CHAR),
  USER_AGENT      VARCHAR2(300 CHAR),
  SYSTEM_DATE     VARCHAR2(30 CHAR)       NOT NULL,
  DB_DATE         TIMESTAMP(6)            NOT NULL
)
TABLESPACE EON_DATA1 LOGGING  
NOCOMPRESS 
NOCACHE
NOPARALLEL
NOMONITORING;
