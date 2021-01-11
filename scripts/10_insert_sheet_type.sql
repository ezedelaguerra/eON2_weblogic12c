-- Sheet Types For Seller
Insert into EON_SHEET_TYPE
   (SHEET_TYPE_ID, DESCRIPTION, ROLE_ID)
 Values
   (10001, 'Order Sheet', 1);
--   (10000, 'Order Sheet', 1);
Insert into EON_SHEET_TYPE
   (SHEET_TYPE_ID, DESCRIPTION, ROLE_ID)
 Values
   (10003, 'Allocation Sheet', 1);
--   (10001, 'Allocation Sheet', 1);
Insert into EON_SHEET_TYPE
   (SHEET_TYPE_ID, DESCRIPTION, ROLE_ID)
 Values
   (10005, 'Billing Sheet', 1);
--   (10002, 'Billing Sheet', 1);
Insert into EON_SHEET_TYPE
   (SHEET_TYPE_ID, DESCRIPTION, ROLE_ID)
 Values
   (10020, 'Akaden Sheet', 1); 
--   (10003, 'Akaden Sheet', 1); 

-- Sheet Types For Buyer
Insert into EON_SHEET_TYPE
   (SHEET_TYPE_ID, DESCRIPTION, ROLE_ID)
 Values
   (10000, 'Order Sheet', 3);
--   (10004, 'Order Sheet', 3);
Insert into EON_SHEET_TYPE
   (SHEET_TYPE_ID, DESCRIPTION, ROLE_ID)
 Values
   (10004, 'Received Sheet', 3);
--   (10005, 'Received Sheet', 3);
Insert into EON_SHEET_TYPE
   (SHEET_TYPE_ID, DESCRIPTION, ROLE_ID)
 Values
   (10006, 'Billing Sheet', 3);
--   (10006, 'Billing Sheet', 3);

-- Sheet Types For Seller Admin
Insert into EON_SHEET_TYPE
   (SHEET_TYPE_ID, DESCRIPTION, ROLE_ID)
 Values
   (10009, 'Order Sheet', 2);
--   (10007, 'Order Sheet', 2);
Insert into EON_SHEET_TYPE
   (SHEET_TYPE_ID, DESCRIPTION, ROLE_ID)
 Values
   (10010, 'Allocation Sheet', 2);
--   (10008, 'Allocation Sheet', 2);
Insert into EON_SHEET_TYPE
   (SHEET_TYPE_ID, DESCRIPTION, ROLE_ID)
 Values
   (10011, 'Billing Sheet', 2);
--   (10009, 'Billing Sheet', 2);
Insert into EON_SHEET_TYPE
   (SHEET_TYPE_ID, DESCRIPTION, ROLE_ID)
 Values
   (10021, 'Akaden Sheet', 2); 
--   (10010, 'Akaden Sheet', 2); 

-- Sheet Types For Buyer Admin
Insert into EON_SHEET_TYPE
   (SHEET_TYPE_ID, DESCRIPTION, ROLE_ID)
 Values
   (10007, 'Order Sheet', 4);
--   (10011, 'Order Sheet', 4);
Insert into EON_SHEET_TYPE
   (SHEET_TYPE_ID, DESCRIPTION, ROLE_ID)
 Values
   (10012, 'Received Sheet', 4);
--   (10012, 'Received Sheet', 4);
Insert into EON_SHEET_TYPE
   (SHEET_TYPE_ID, DESCRIPTION, ROLE_ID)
 Values
   (10013, 'Billing Sheet', 4);
--   (10013, 'Billing Sheet', 4);
COMMIT;
