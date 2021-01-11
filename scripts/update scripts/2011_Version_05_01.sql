-- Changes on eon show hide column Table
ALTER TABLE EON_SHOW_HIDE_COLUMN drop column GROSS_PROFIT_RATE;
ALTER TABLE EON_SHOW_HIDE_COLUMN drop column PIECE_PRICE;
ALTER TABLE EON_SHOW_HIDE_COLUMN add( PURCHASE_PRICE CHAR(1 BYTE)    DEFAULT '0');
ALTER TABLE EON_SHOW_HIDE_COLUMN modify( SKU_COMMENT CHAR(1 BYTE)    DEFAULT '0');
ALTER TABLE EON_SHOW_HIDE_COLUMN modify( SELLING_PRICE CHAR(1 BYTE)    DEFAULT '0');
ALTER TABLE EON_SHOW_HIDE_COLUMN modify( SELLING_UOM CHAR(1 BYTE)    DEFAULT '0');

-- Selling UOM Table
CREATE TABLE EON_SELLING_UOM
(
  ID_SELLING_UOM	NUMBER(9)		NOT NULL,
  CATEGORY_ID		NUMBER(9),
  SELLING_UOM_NAME	VARCHAR2 (15 Char)
)
TABLESPACE EON_DATA1 LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;

ALTER TABLE EON_SELLING_UOM ADD (
  CONSTRAINT EON_SELLING_UOM_PK
 PRIMARY KEY 
 (ID_SELLING_UOM)USING INDEX TABLESPACE EON_INDX01);
 
CREATE SEQUENCE EON_SELLING_UOM_SEQ
  START WITH 1
  MAXVALUE 999999999
  MINVALUE 0
  NOCYCLE
  NOCACHE
  NOORDER;

-- SKU BA Table
CREATE TABLE EON_SKU_BA
(
  SKU_BA_ID		NUMBER(9)		NOT NULL,
  FK_SKU_ID		NUMBER(9)		NOT NULL,
  PURCHASE_PRICE	NUMBER(9),
  SELLING_PRICE		NUMBER(9),
  FK_ID_SELLING_UOM	NUMBER(9),
  SKU_COMMENT		VARCHAR2(50 Char)
)
TABLESPACE EON_DATA1 LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;

CREATE SEQUENCE EON_SKU_BA_ID_SEQ
  START WITH 1
  MAXVALUE 999999999
  MINVALUE 0
  NOCYCLE
  NOCACHE
  NOORDER;

ALTER TABLE EON_SKU_BA ADD (
  CONSTRAINT EON_SKU_BA_PK
 PRIMARY KEY 
 (SKU_BA_ID)USING INDEX TABLESPACE EON_INDX01);

ALTER TABLE EON_SKU_BA ADD (
  CONSTRAINT EON_SKU_BA_FK1
 FOREIGN KEY (FK_SKU_ID) 
 REFERENCES EON_SKU (SKU_ID));

ALTER TABLE EON_SKU_BA ADD (
  CONSTRAINT EON_SKU_BA_FK2
 FOREIGN KEY (FK_ID_SELLING_UOM) 
 REFERENCES EON_SELLING_UOM (ID_SELLING_UOM));


-- Update Order Item Table to add sku_ba_id
ALTER TABLE EON_ORDER_ITEM 
  ADD SKU_BA_ID NUMBER(9);

ALTER TABLE EON_ORDER_ITEM ADD (
  CONSTRAINT EON_ORDER_ITEM_FK5
 FOREIGN KEY (SKU_BA_ID) 
 REFERENCES EON_SKU_BA (SKU_BA_ID));

-- Update Allocation Item Table to add sku_ba_id
ALTER TABLE EON_ORDER_ALLOCATION 
  ADD SKU_BA_ID NUMBER(9);

ALTER TABLE EON_ORDER_ALLOCATION ADD (
  CONSTRAINT EON_ORDER_ALLOCATION_FK5
 FOREIGN KEY (SKU_BA_ID) 
 REFERENCES EON_SKU_BA (SKU_BA_ID));


-- Update Received Item Table to add sku_ba_id
ALTER TABLE EON_ORDER_RECEIVED 
  ADD SKU_BA_ID NUMBER(9);

ALTER TABLE EON_ORDER_RECEIVED ADD (
  CONSTRAINT EON_ORDER_RECEIVED_FK5
 FOREIGN KEY (SKU_BA_ID) 
 REFERENCES EON_SKU_BA (SKU_BA_ID));


-- Update Billing Item Table to add sku_ba_id
ALTER TABLE EON_ORDER_BILLING 
  ADD SKU_BA_ID NUMBER(9);

ALTER TABLE EON_ORDER_BILLING ADD (
  CONSTRAINT EON_ORDER_BILLING_FK5
 FOREIGN KEY (SKU_BA_ID) 
 REFERENCES EON_SKU_BA (SKU_BA_ID));

--Create initial data for Selling UOM look up
Insert into EON_SELLING_UOM
   (ID_SELLING_UOM, CATEGORY_ID, SELLING_UOM_NAME)
 Values
   (EON_SELLING_UOM_SEQ.NEXTVAL, 1, 'ピース');
Insert into EON_SELLING_UOM
   (ID_SELLING_UOM, CATEGORY_ID, SELLING_UOM_NAME)
 Values
   (EON_SELLING_UOM_SEQ.NEXTVAL, 1, 'C/S');
Insert into EON_SELLING_UOM
   (ID_SELLING_UOM, CATEGORY_ID, SELLING_UOM_NAME)
 Values
   (EON_SELLING_UOM_SEQ.NEXTVAL, 2, 'ピース');
Insert into EON_SELLING_UOM
   (ID_SELLING_UOM, CATEGORY_ID, SELLING_UOM_NAME)
 Values
   (EON_SELLING_UOM_SEQ.NEXTVAL, 2, 'C/S');
Insert into EON_SELLING_UOM
   (ID_SELLING_UOM, CATEGORY_ID, SELLING_UOM_NAME)
 Values
   (EON_SELLING_UOM_SEQ.NEXTVAL, 3, 'ピース');
Insert into EON_SELLING_UOM
   (ID_SELLING_UOM, CATEGORY_ID, SELLING_UOM_NAME)
 Values
   (EON_SELLING_UOM_SEQ.NEXTVAL, 3, 'KG');
COMMIT;