SET DEFINE OFF;
Insert into EON_ORDER_UNIT
   (ORDER_UNIT_ID, CATEGORY_ID, ORDER_UNIT_NAME)
 Values
   (EON_ORDER_UNIT_SEQ.NEXTVAL, 1, 'ピース');
Insert into EON_ORDER_UNIT
   (ORDER_UNIT_ID, CATEGORY_ID, ORDER_UNIT_NAME)
 Values
   (EON_ORDER_UNIT_SEQ.NEXTVAL, 1, 'C/S');
Insert into EON_ORDER_UNIT
   (ORDER_UNIT_ID, CATEGORY_ID, ORDER_UNIT_NAME)
 Values
   (EON_ORDER_UNIT_SEQ.NEXTVAL, 2, 'ピース');
Insert into EON_ORDER_UNIT
   (ORDER_UNIT_ID, CATEGORY_ID, ORDER_UNIT_NAME)
 Values
   (EON_ORDER_UNIT_SEQ.NEXTVAL, 2, 'C/S');
Insert into EON_ORDER_UNIT
   (ORDER_UNIT_ID, CATEGORY_ID, ORDER_UNIT_NAME)
 Values
   (EON_ORDER_UNIT_SEQ.NEXTVAL, 3, 'ピース');
Insert into EON_ORDER_UNIT
   (ORDER_UNIT_ID, CATEGORY_ID, ORDER_UNIT_NAME)
 Values
   (EON_ORDER_UNIT_SEQ.NEXTVAL, 3, 'KG');
COMMIT;
