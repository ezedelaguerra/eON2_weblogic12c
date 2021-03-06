--20 New columns in EON_SKU table
ALTER TABLE EON_SKU ADD (
COLUMN_01 VARCHAR2(42 Char),
COLUMN_02 VARCHAR2(42 Char),
COLUMN_03 VARCHAR2(42 Char),
COLUMN_04 VARCHAR2(42 Char),
COLUMN_05 VARCHAR2(42 Char),
COLUMN_06 VARCHAR2(42 Char),
COLUMN_07 VARCHAR2(42 Char),
COLUMN_08 VARCHAR2(42 Char),
COLUMN_09 VARCHAR2(42 Char),
COLUMN_10 VARCHAR2(42 Char),
COLUMN_11 VARCHAR2(42 Char),
COLUMN_12 VARCHAR2(42 Char),
COLUMN_13 VARCHAR2(42 Char),
COLUMN_14 VARCHAR2(42 Char),
COLUMN_15 VARCHAR2(42 Char),
COLUMN_16 VARCHAR2(42 Char),
COLUMN_17 VARCHAR2(42 Char),
COLUMN_18 VARCHAR2(42 Char),
COLUMN_19 VARCHAR2(42 Char),
COLUMN_20 VARCHAR2(42 Char));

-- 20 new columns in EON_SHOW_HIDE_COLUMN table
ALTER TABLE EON_SHOW_HIDE_COLUMN add( COLUMN_01 CHAR(1 BYTE)    DEFAULT '0');
ALTER TABLE EON_SHOW_HIDE_COLUMN add( COLUMN_02 CHAR(1 BYTE)    DEFAULT '0');
ALTER TABLE EON_SHOW_HIDE_COLUMN add( COLUMN_03 CHAR(1 BYTE)    DEFAULT '0');
ALTER TABLE EON_SHOW_HIDE_COLUMN add( COLUMN_04 CHAR(1 BYTE)    DEFAULT '0');
ALTER TABLE EON_SHOW_HIDE_COLUMN add( COLUMN_05 CHAR(1 BYTE)    DEFAULT '0');
ALTER TABLE EON_SHOW_HIDE_COLUMN add( COLUMN_06 CHAR(1 BYTE)    DEFAULT '0');
ALTER TABLE EON_SHOW_HIDE_COLUMN add( COLUMN_07 CHAR(1 BYTE)    DEFAULT '0');
ALTER TABLE EON_SHOW_HIDE_COLUMN add( COLUMN_08 CHAR(1 BYTE)    DEFAULT '0');
ALTER TABLE EON_SHOW_HIDE_COLUMN add( COLUMN_09 CHAR(1 BYTE)    DEFAULT '0');
ALTER TABLE EON_SHOW_HIDE_COLUMN add( COLUMN_10 CHAR(1 BYTE)    DEFAULT '0');
ALTER TABLE EON_SHOW_HIDE_COLUMN add( COLUMN_11 CHAR(1 BYTE)    DEFAULT '0');
ALTER TABLE EON_SHOW_HIDE_COLUMN add( COLUMN_12 CHAR(1 BYTE)    DEFAULT '0');
ALTER TABLE EON_SHOW_HIDE_COLUMN add( COLUMN_13 CHAR(1 BYTE)    DEFAULT '0');
ALTER TABLE EON_SHOW_HIDE_COLUMN add( COLUMN_14 CHAR(1 BYTE)    DEFAULT '0');
ALTER TABLE EON_SHOW_HIDE_COLUMN add( COLUMN_15 CHAR(1 BYTE)    DEFAULT '0');
ALTER TABLE EON_SHOW_HIDE_COLUMN add( COLUMN_16 CHAR(1 BYTE)    DEFAULT '0');
ALTER TABLE EON_SHOW_HIDE_COLUMN add( COLUMN_17 CHAR(1 BYTE)    DEFAULT '0');
ALTER TABLE EON_SHOW_HIDE_COLUMN add( COLUMN_18 CHAR(1 BYTE)    DEFAULT '0');
ALTER TABLE EON_SHOW_HIDE_COLUMN add( COLUMN_19 CHAR(1 BYTE)    DEFAULT '0');
ALTER TABLE EON_SHOW_HIDE_COLUMN add( COLUMN_20 CHAR(1 BYTE)    DEFAULT '0');


-- EON_SKU_COLUMN for sku sheet/csv name mapping 
-- EON_SKU_COLUMN for Sorting Preference and header name reference
ALTER TABLE EON_SKU_COLUMN RENAME COLUMN SKU_COLUMN_KEY to HEADER_KEY;
ALTER TABLE EON_SKU_COLUMN RENAME COLUMN SKU_COLUMN_NAME_J to HEADER_SHEET;
ALTER TABLE EON_SKU_COLUMN RENAME COLUMN SKU_COLUMN_NAME to HEADER_CSV;

UPDATE EON_SKU_COLUMN SET HEADER_CSV = 'SKU ID' WHERE SKU_COLUMN_ID = 100;
UPDATE EON_SKU_COLUMN SET HEADER_CSV = 'COMPANY NAME' WHERE SKU_COLUMN_ID = 101;
UPDATE EON_SKU_COLUMN SET HEADER_CSV = 'SELLER NAME' WHERE SKU_COLUMN_ID = 102;
UPDATE EON_SKU_COLUMN SET HEADER_CSV = 'SKU GROUP NAME' WHERE SKU_COLUMN_ID = 103;
UPDATE EON_SKU_COLUMN SET HEADER_CSV = 'DESCR' WHERE SKU_COLUMN_ID = 104;
UPDATE EON_SKU_COLUMN SET HEADER_CSV = 'MARKET CONDITION', HEADER_KEY = 'marketcondition' WHERE SKU_COLUMN_ID = 105;
UPDATE EON_SKU_COLUMN SET HEADER_CSV = 'AREA OF PRODUCTION', HEADER_KEY = 'areaofproduction' WHERE SKU_COLUMN_ID = 106;
UPDATE EON_SKU_COLUMN SET HEADER_CSV = 'CLASS 1', HEADER_KEY = 'class1' WHERE SKU_COLUMN_ID = 107;
UPDATE EON_SKU_COLUMN SET HEADER_CSV = 'CLASS 2', HEADER_KEY = 'class2' WHERE SKU_COLUMN_ID = 108;
UPDATE EON_SKU_COLUMN SET HEADER_CSV = 'PACKAGING QUANTITY' WHERE SKU_COLUMN_ID = 109;
UPDATE EON_SKU_COLUMN SET HEADER_CSV = 'PACKING TYPE' WHERE SKU_COLUMN_ID = 110;
UPDATE EON_SKU_COLUMN SET HEADER_CSV = 'PRICE 1' WHERE SKU_COLUMN_ID = 111;
UPDATE EON_SKU_COLUMN SET HEADER_CSV = 'PRICE 2' WHERE SKU_COLUMN_ID = 112;
UPDATE EON_SKU_COLUMN SET HEADER_CSV = 'UNIT PRICE WITHOUT TAX' WHERE SKU_COLUMN_ID = 113;
UPDATE EON_SKU_COLUMN SET HEADER_CSV = 'UNIT PRICE WITH TAX' WHERE SKU_COLUMN_ID = 114;
UPDATE EON_SKU_COLUMN SET HEADER_CSV = 'UOM', HEADER_KEY = 'uom' WHERE SKU_COLUMN_ID = 115;
UPDATE EON_SKU_COLUMN SET HEADER_CSV = 'EXTERNAL SKU ID', HEADER_KEY = 'externalid', HEADER_SHEET = '�O���A�g�R�[�h'  WHERE SKU_COLUMN_ID = 116;
Update EON_SKU_COLUMN SET HEADER_CSV = 'SKU COMMENT', HEADER_KEY = 'B_skuComment', HEADER_SHEET = '���i����' WHERE SKU_COLUMN_ID = 117;

Insert into EON_SKU_COLUMN (SKU_COLUMN_ID, HEADER_KEY, HEADER_CSV, HEADER_SHEET) Values (118, 'B_purchasePrice', 'PURCHASE PRICE', '�d���P��');
Insert into EON_SKU_COLUMN (SKU_COLUMN_ID, HEADER_KEY, HEADER_CSV, HEADER_SHEET) Values (119, 'B_sellingPrice', 'SELLING PRICE', '����');
Insert into EON_SKU_COLUMN (SKU_COLUMN_ID, HEADER_KEY, HEADER_CSV, HEADER_SHEET) Values (120, 'B_sellingUom', 'SELLING UOM', '�̔��P��');
Insert into EON_SKU_COLUMN (SKU_COLUMN_ID, HEADER_KEY, HEADER_CSV, HEADER_SHEET) Values (121, 'column01', 'CENTER', '�Z���^�[�[�i');
Insert into EON_SKU_COLUMN (SKU_COLUMN_ID, HEADER_KEY, HEADER_CSV, HEADER_SHEET) Values (122, 'column02', 'DELIVERY', '��');
Insert into EON_SKU_COLUMN (SKU_COLUMN_ID, HEADER_KEY, HEADER_CSV, HEADER_SHEET) Values (123, 'column03', 'SALE', '����');
Insert into EON_SKU_COLUMN (SKU_COLUMN_ID, HEADER_KEY, HEADER_CSV, HEADER_SHEET) Values (124, 'column04', 'JAN', 'JAN');
Insert into EON_SKU_COLUMN (SKU_COLUMN_ID, HEADER_KEY, HEADER_CSV, HEADER_SHEET) Values (125, 'column05', 'PACK FEE', '���H��');
Insert into EON_SKU_COLUMN (SKU_COLUMN_ID, HEADER_KEY, HEADER_CSV, HEADER_SHEET) Values (126, 'column06', 'COLUMN 1', '���l1');
Insert into EON_SKU_COLUMN (SKU_COLUMN_ID, HEADER_KEY, HEADER_CSV, HEADER_SHEET) Values (127, 'column07', 'COLUMN 2', '���l2');
Insert into EON_SKU_COLUMN (SKU_COLUMN_ID, HEADER_KEY, HEADER_CSV, HEADER_SHEET) Values (128, 'column08', 'COLUMN 3', '���l3');
Insert into EON_SKU_COLUMN (SKU_COLUMN_ID, HEADER_KEY, HEADER_CSV, HEADER_SHEET) Values (129, 'column09', 'COLUMN 4', '���l4');
Insert into EON_SKU_COLUMN (SKU_COLUMN_ID, HEADER_KEY, HEADER_CSV, HEADER_SHEET) Values (130, 'column10', 'COLUMN 5', '���l5');
Insert into EON_SKU_COLUMN (SKU_COLUMN_ID, HEADER_KEY, HEADER_CSV, HEADER_SHEET) Values (131, 'column11', 'COLUMN 6', '���l6');
Insert into EON_SKU_COLUMN (SKU_COLUMN_ID, HEADER_KEY, HEADER_CSV, HEADER_SHEET) Values (132, 'column12', 'COLUMN 7', '���l7');
Insert into EON_SKU_COLUMN (SKU_COLUMN_ID, HEADER_KEY, HEADER_CSV, HEADER_SHEET) Values (133, 'column13', 'COLUMN 8', '���l8');
Insert into EON_SKU_COLUMN (SKU_COLUMN_ID, HEADER_KEY, HEADER_CSV, HEADER_SHEET) Values (134, 'column14', 'COLUMN 9', '���l9');
Insert into EON_SKU_COLUMN (SKU_COLUMN_ID, HEADER_KEY, HEADER_CSV, HEADER_SHEET) Values (135, 'column15', 'COLUMN 10', '���l10');
Insert into EON_SKU_COLUMN (SKU_COLUMN_ID, HEADER_KEY, HEADER_CSV, HEADER_SHEET) Values (136, 'column16', 'COLUMN 11', '���l11');
Insert into EON_SKU_COLUMN (SKU_COLUMN_ID, HEADER_KEY, HEADER_CSV, HEADER_SHEET) Values (137, 'column17', 'COLUMN 12', '���l12');
Insert into EON_SKU_COLUMN (SKU_COLUMN_ID, HEADER_KEY, HEADER_CSV, HEADER_SHEET) Values (138, 'column18', 'COLUMN 13', '���l13');
Insert into EON_SKU_COLUMN (SKU_COLUMN_ID, HEADER_KEY, HEADER_CSV, HEADER_SHEET) Values (139, 'column19', 'COLUMN 14', '���l14');
Insert into EON_SKU_COLUMN (SKU_COLUMN_ID, HEADER_KEY, HEADER_CSV, HEADER_SHEET) Values (140, 'column20', 'COLUMN 15', '���l15');

ALTER TABLE EON_SKU_COLUMN MODIFY(HEADER_KEY VARCHAR2(42 CHAR));
ALTER TABLE EON_SKU_COLUMN MODIFY(HEADER_CSV VARCHAR2(42 CHAR));
ALTER TABLE EON_SKU_COLUMN MODIFY(HEADER_SHEET VARCHAR2(10 CHAR));

-- Column Width
CREATE TABLE EON_COLUMN_WIDTH
(
  FK_USER_ID     NUMBER(9)             NOT NULL,
  HEADER_KEY     VARCHAR2(64 CHAR)     NOT NULL,
  WIDTH          NUMBER(9)             NOT NULL
)
TABLESPACE EON_DATA1 LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;

ALTER TABLE EON_COLUMN_WIDTH ADD (CONSTRAINT EON_COLUMN_WIDTH_FK1 FOREIGN KEY (FK_USER_ID) REFERENCES EON_USERS (USER_ID));

--Sort Company Sellers Table
CREATE TABLE EON_SORT_COMPANY_SELLERS
(
  USER_ID           NUMBER(9)                   NOT NULL,
  COMPANY_ID        NUMBER(9),
  SORTING           NUMBER(4),
  CREATE_TIMESTAMP  TIMESTAMP(6)                DEFAULT SYSDATE,
  CREATED_BY        NUMBER(9),
  UPDATE_TIMESTAMP  TIMESTAMP(6),
  UPDATED_BY        NUMBER(9)
)
TABLESPACE EON_DATA1 LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;

ALTER TABLE EON_SORT_COMPANY_SELLERS ADD (
  CONSTRAINT EON_SORT_COMPANY_SELLERS_PK
 PRIMARY KEY
 (USER_ID, COMPANY_ID));

ALTER TABLE EON_SORT_COMPANY_SELLERS ADD (
  CONSTRAINT EON_SORT_COMPANY_SELLERS_FK4 
 FOREIGN KEY (UPDATED_BY) 
 REFERENCES EON_USERS (USER_ID),
  CONSTRAINT EON_SORT_COMPANY_SELLERS_FK3 
 FOREIGN KEY (CREATED_BY) 
 REFERENCES EON_USERS (USER_ID),
  CONSTRAINT EON_SORT_COMPANY_SELLERS_FK2 
 FOREIGN KEY (COMPANY_ID) 
 REFERENCES EON_COMPANY (COMPANY_ID),
  CONSTRAINT EON_SORT_COMPANY_SELLERS_FK1 
 FOREIGN KEY (USER_ID) 
 REFERENCES EON_USERS (USER_ID));

-- Sort Sellers Table
CREATE TABLE EON_SORT_SELLERS
(
  USER_ID           NUMBER(9),
  SELLER_ID          NUMBER(9),
  SORTING           NUMBER(4),
  CREATE_TIMESTAMP  TIMESTAMP(6)                DEFAULT SYSDATE,
  CREATED_BY        NUMBER(9),
  UPDATE_TIMESTAMP  TIMESTAMP(6),
  UPDATED_BY        NUMBER(9)
)
TABLESPACE EON_DATA1 LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;

ALTER TABLE EON_SORT_SELLERS ADD (
  CONSTRAINT EON_SORT_SELLERS_PK
 PRIMARY KEY
 (USER_ID, SELLER_ID));

ALTER TABLE EON_SORT_SELLERS ADD (
  CONSTRAINT EON_SORT_SELLERS_FK4 
 FOREIGN KEY (UPDATED_BY) 
 REFERENCES EON_USERS (USER_ID),
  CONSTRAINT EON_SORT_SELLERS_FK3 
 FOREIGN KEY (CREATED_BY) 
 REFERENCES EON_USERS (USER_ID),
  CONSTRAINT EON_SORT_SELLERS_FK2 
 FOREIGN KEY (SELLER_ID) 
 REFERENCES EON_USERS (USER_ID),
  CONSTRAINT EON_SORT_SELLERS_FK1 
 FOREIGN KEY (USER_ID) 
 REFERENCES EON_USERS (USER_ID));
 
-- Category Sorting
ALTER TABLE EON_USERS_CATEGORY ADD SORT_ID NUMBER(1);

-- Dynamic Category
ALTER TABLE EON_CATEGORY ADD TAB_NAME VARCHAR2(50 CHAR);
UPDATE EON_CATEGORY SET TAB_NAME = '�ʎ�' WHERE SKU_CATEGORY_ID = 1;
UPDATE EON_CATEGORY SET TAB_NAME = '���' WHERE SKU_CATEGORY_ID = 2;
UPDATE EON_CATEGORY SET TAB_NAME = '�N��' WHERE SKU_CATEGORY_ID = 3;

-- Auto Download tables
CREATE TABLE EON_AUTODWNLD_SCHEDULE
(
  ID_SCHEDULE_CSV       NUMBER(9),
  FK_ID_USER            NUMBER(9),
  EXECUTION_TIME        VARCHAR2(5 BYTE),
  DATE_LAST_DOWNLOAD    TIMESTAMP(6),
  LEAD_TIME             NUMBER(3),
  FK_ID_SHEET_TYPE      NUMBER(9),
  SHEET_CATEGORY        NUMBER(9),
  HAS_QTY               CHAR(1 BYTE),
  FTP_IP                VARCHAR2(50 CHAR),
  FTP_ID                VARCHAR2(50 CHAR),
  FTP_PW                VARCHAR2(50 CHAR)
)
TABLESPACE EON_DATA1 LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;

CREATE TABLE EON_AUTODWNLD_EXCEPT_DATE
(
  FK_ID_SCHEDULE_CSV  NUMBER(9),
  EXCEPT_DATE         TIMESTAMP(6)
)
TABLESPACE EON_DATA1 LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;

CREATE TABLE EON_AUTODWNLD_SELLERS
(
  FK_ID_SCHEDULE_CSV  NUMBER(9),
  FK_ID_SELLER        NUMBER(9)
)
TABLESPACE EON_DATA1 LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;

CREATE TABLE EON_AUTODWNLD_BUYERS
(
  FK_ID_SCHEDULE_CSV  NUMBER(9),
  FK_ID_BUYER         NUMBER(9)
)
TABLESPACE EON_DATA1 LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;

ALTER TABLE EON_AUTODWNLD_SCHEDULE ADD (
  CONSTRAINT EON_AUTODWNLD_SCHEDULE_PK
 PRIMARY KEY 
 (ID_SCHEDULE_CSV) USING INDEX TABLESPACE EON_INDX01);

ALTER TABLE EON_AUTODWNLD_SCHEDULE ADD (
  CONSTRAINT EON_AUTODWNLD_SCHEDULE_FK1
 FOREIGN KEY (FK_ID_USER) 
 REFERENCES EON_USERS (USER_ID));

ALTER TABLE EON_AUTODWNLD_SCHEDULE ADD (
  CONSTRAINT EON_AUTODWNLD_SCHEDULE_FK2
 FOREIGN KEY (FK_ID_SHEET_TYPE) 
 REFERENCES EON_SHEET_TYPE (SHEET_TYPE_ID));

ALTER TABLE EON_AUTODWNLD_EXCEPT_DATE ADD (
  CONSTRAINT EON_AUTODWNLD_EXCEPT_DATE_FK
 FOREIGN KEY (FK_ID_SCHEDULE_CSV) 
 REFERENCES EON_AUTODWNLD_SCHEDULE (ID_SCHEDULE_CSV));

ALTER TABLE EON_AUTODWNLD_SELLERS ADD (
  CONSTRAINT EON_AUTODWNLD_SELLERS_FK1
 FOREIGN KEY (FK_ID_SCHEDULE_CSV) 
 REFERENCES EON_AUTODWNLD_SCHEDULE (ID_SCHEDULE_CSV));

ALTER TABLE EON_AUTODWNLD_SELLERS ADD (
  CONSTRAINT EON_AUTODWNLD_SELLERS_FK2
 FOREIGN KEY (FK_ID_SELLER) 
 REFERENCES EON_USERS (USER_ID));

ALTER TABLE EON_AUTODWNLD_BUYERS ADD (
  CONSTRAINT EON_AUTODWNLD_BUYERS_FK1
 FOREIGN KEY (FK_ID_SCHEDULE_CSV) 
 REFERENCES EON_AUTODWNLD_SCHEDULE (ID_SCHEDULE_CSV));

ALTER TABLE EON_AUTODWNLD_BUYERS ADD (
  CONSTRAINT EON_AUTODWNLD_BUYERS_FK2
 FOREIGN KEY (FK_ID_BUYER) 
 REFERENCES EON_USERS (USER_ID));

CREATE SEQUENCE EON_AUTODWNLD_SCHEDULE_SEQ
  START WITH 1
  MAXVALUE 999999999
  MINVALUE 0
  NOCYCLE
  NOCACHE
  NOORDER;
COMMIT;