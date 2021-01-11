-- User Profit computation preference Table
-- For TOTAL_           >>> 1 = Show              0 = Hide
-- For PRICE_TAX_OPTION >>> 1 = Price with Tax    0 = Price without Tax

CREATE TABLE EON_PROFIT_PREFERENCE
(
  FK_ID_USER           	NUMBER(9)	NOT NULL,
  TOTAL_SELLING_PRICE  	CHAR(1 BYTE) DEFAULT '0',
  TOTAL_PROFIT        	CHAR(1 BYTE) DEFAULT '0',
  TOTAL_PROFIT_PERCENT  CHAR(1 BYTE) DEFAULT '0',
  PRICE_TAX_OPTION     	CHAR(1 BYTE) DEFAULT '1',
  CONSTRAINT EON_PROFIT_PREFERENCE_PK PRIMARY KEY (FK_ID_USER),
  CONSTRAINT EON_PROFIT_PREFERENCE_FK1 FOREIGN KEY (FK_ID_USER) REFERENCES EON_USERS (USER_ID)
)
TABLESPACE EON_DATA1 LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;

INSERT INTO EON_SKU_COLUMN (SKU_COLUMN_ID, HEADER_KEY, HEADER_CSV, HEADER_SHEET) VALUES (141, 'B_profitPercentage', 'Profit Percentage', '�e����');

ALTER TABLE EON_SHOW_HIDE_COLUMN add( PROFIT_PERCENTAGE CHAR(1 BYTE)    DEFAULT '0');

COMMIT;