Insert into EON_ROLES
   (ROLE_ID, ROLE_NAME, SELLER_FLAG, SELLER_ADMIN_FLAG, BUYER_FLAG, 
    BUYER_ADMIN_FLAG, ADMIN_FLAG, CHOUAI_FLAG, COMPANY_TYPE_FLAG)
 Values
   (10, 'KEN', '0', '0', '0', 
    '0', '0', '0', '9');
Insert into EON_ROLES
   (ROLE_ID, ROLE_NAME, SELLER_FLAG, SELLER_ADMIN_FLAG, BUYER_FLAG, 
    BUYER_ADMIN_FLAG, ADMIN_FLAG, CHOUAI_FLAG, COMPANY_TYPE_FLAG)
 Values
   (6, 'CHOUAI', '0', '0', '0', 
    '0', '0', '1', '1');
Insert into EON_ROLES
   (ROLE_ID, ROLE_NAME, SELLER_FLAG, SELLER_ADMIN_FLAG, BUYER_FLAG, 
    BUYER_ADMIN_FLAG, ADMIN_FLAG, CHOUAI_FLAG, COMPANY_TYPE_FLAG)
 Values
   (5, 'ADMIN', '0', '0', '0', 
    '0', '1', '0', '2');
Insert into EON_ROLES
   (ROLE_ID, ROLE_NAME, SELLER_FLAG, SELLER_ADMIN_FLAG, BUYER_FLAG, 
    BUYER_ADMIN_FLAG, ADMIN_FLAG, CHOUAI_FLAG, COMPANY_TYPE_FLAG)
 Values
   (4, 'BUYER_ADMIN', '0', '0', '0', 
    '1', '0', '0', '0');
Insert into EON_ROLES
   (ROLE_ID, ROLE_NAME, SELLER_FLAG, SELLER_ADMIN_FLAG, BUYER_FLAG, 
    BUYER_ADMIN_FLAG, ADMIN_FLAG, CHOUAI_FLAG, COMPANY_TYPE_FLAG)
 Values
   (3, 'BUYER', '0', '0', '1', 
    '0', '0', '0', '0');
Insert into EON_ROLES
   (ROLE_ID, ROLE_NAME, SELLER_FLAG, SELLER_ADMIN_FLAG, BUYER_FLAG, 
    BUYER_ADMIN_FLAG, ADMIN_FLAG, CHOUAI_FLAG, COMPANY_TYPE_FLAG)
 Values
   (2, 'SELLER_ADMIN', '0', '1', '0', 
    '0', '0', '0', '1');
Insert into EON_ROLES
   (ROLE_ID, ROLE_NAME, SELLER_FLAG, SELLER_ADMIN_FLAG, BUYER_FLAG, 
    BUYER_ADMIN_FLAG, ADMIN_FLAG, CHOUAI_FLAG, COMPANY_TYPE_FLAG)
 Values
   (1, 'SELLER', '1', '0', '0', 
    '0', '0', '0', '1');
COMMIT;
