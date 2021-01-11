-- Connect SELLER to BUYER Companies
Insert into EON_COMPANY_DEALING_PATTERN
   (COMPANY_DEALING_PATTERN_ID, DEALING_PATTERN_RELATION_ID, 
   COMPANY_01, COMPANY_02, ISACTIVE)
 Values
   (EON_COMPANY_DEALINGPATTERN_SEQ.NEXTVAL, 10000, 
   2, 4, 1);
Insert into EON_COMPANY_DEALING_PATTERN
   (COMPANY_DEALING_PATTERN_ID, DEALING_PATTERN_RELATION_ID, 
   COMPANY_01, COMPANY_02, ISACTIVE)
 Values
   (EON_COMPANY_DEALINGPATTERN_SEQ.NEXTVAL, 10000, 
   2, 5, 1);
Insert into EON_COMPANY_DEALING_PATTERN
   (COMPANY_DEALING_PATTERN_ID, DEALING_PATTERN_RELATION_ID, 
   COMPANY_01, COMPANY_02, ISACTIVE)
 Values
   (EON_COMPANY_DEALINGPATTERN_SEQ.NEXTVAL, 10000, 
   3, 5, 1);
   
-- Connect SELLER ADMIN to SELLER Users
Insert into EON_ADMIN_MEMBER
   (ADMIN_MEMBER_ID, ADMIN_ID, MEMBER_ID,
   DEALING_PATTERN_RELATION_ID, START_DATE)
 Values
   (EON_ADMIN_MEMBER_SEQ.NEXTVAL, 5, 2,
   10003, '20100101');
Insert into EON_ADMIN_MEMBER
   (ADMIN_MEMBER_ID, ADMIN_ID, MEMBER_ID,
   DEALING_PATTERN_RELATION_ID, START_DATE)
 Values
   (EON_ADMIN_MEMBER_SEQ.NEXTVAL, 5, 3,
   10003, '20100101');
Insert into EON_ADMIN_MEMBER
   (ADMIN_MEMBER_ID, ADMIN_ID, MEMBER_ID,
   DEALING_PATTERN_RELATION_ID, START_DATE)
 Values
   (EON_ADMIN_MEMBER_SEQ.NEXTVAL, 6, 4,
   10003, '20100101');

Insert into EON_ADMIN_MEMBER
   (ADMIN_MEMBER_ID, ADMIN_ID, MEMBER_ID,
   DEALING_PATTERN_RELATION_ID, START_DATE)
 Values
   (EON_ADMIN_MEMBER_SEQ.NEXTVAL, 11, 8,
   10003, '20100101');
Insert into EON_ADMIN_MEMBER
   (ADMIN_MEMBER_ID, ADMIN_ID, MEMBER_ID,
   DEALING_PATTERN_RELATION_ID, START_DATE)
 Values
   (EON_ADMIN_MEMBER_SEQ.NEXTVAL, 11, 9,
   10003, '20100101');
Insert into EON_ADMIN_MEMBER
   (ADMIN_MEMBER_ID, ADMIN_ID, MEMBER_ID,
   DEALING_PATTERN_RELATION_ID, START_DATE)
 Values
   (EON_ADMIN_MEMBER_SEQ.NEXTVAL, 12, 10,
   10003, '20100101');
   
-- Connect BUYER ADMIN to BUYER Users
Insert into EON_ADMIN_MEMBER
   (ADMIN_MEMBER_ID, ADMIN_ID, MEMBER_ID,
   DEALING_PATTERN_RELATION_ID, START_DATE)
 Values
   (EON_ADMIN_MEMBER_SEQ.NEXTVAL, 17, 14,
   10002, '20100101');
Insert into EON_ADMIN_MEMBER
   (ADMIN_MEMBER_ID, ADMIN_ID, MEMBER_ID,
   DEALING_PATTERN_RELATION_ID, START_DATE)
 Values
   (EON_ADMIN_MEMBER_SEQ.NEXTVAL, 17, 15,
   10002, '20100101');
Insert into EON_ADMIN_MEMBER
   (ADMIN_MEMBER_ID, ADMIN_ID, MEMBER_ID,
   DEALING_PATTERN_RELATION_ID, START_DATE)
 Values
   (EON_ADMIN_MEMBER_SEQ.NEXTVAL, 18, 16,
   10002, '20100101');
   
Insert into EON_ADMIN_MEMBER
   (ADMIN_MEMBER_ID, ADMIN_ID, MEMBER_ID,
   DEALING_PATTERN_RELATION_ID, START_DATE)
 Values
   (EON_ADMIN_MEMBER_SEQ.NEXTVAL, 22, 19,
   10002, '20100101');
Insert into EON_ADMIN_MEMBER
   (ADMIN_MEMBER_ID, ADMIN_ID, MEMBER_ID,
   DEALING_PATTERN_RELATION_ID, START_DATE)
 Values
   (EON_ADMIN_MEMBER_SEQ.NEXTVAL, 22, 20,
   10002, '20100101');
Insert into EON_ADMIN_MEMBER
   (ADMIN_MEMBER_ID, ADMIN_ID, MEMBER_ID,
   DEALING_PATTERN_RELATION_ID, START_DATE)
 Values
   (EON_ADMIN_MEMBER_SEQ.NEXTVAL, 23, 21,
   10002, '20100101');
   
-- Connect SELLER to BUYER Users
-- COMPANY A Sellers
Insert into EON_USER_DEALING_PATTERN
   (USER_DEALING_PATTERN_ID, DEALING_PATTERN_RELATION_ID, 
   COMPANY_DEALING_PATTERN_ID, USER_01, USER_02,
   START_DATE)
 Values
   (EON_USER_DEALING_PATTERN_SEQ.NEXTVAL, 10000, 
   1,  2, 14, 
   '20100101');
Insert into EON_USER_DEALING_PATTERN
   (USER_DEALING_PATTERN_ID, DEALING_PATTERN_RELATION_ID, 
   COMPANY_DEALING_PATTERN_ID, USER_01, USER_02,
   START_DATE)
 Values
   (EON_USER_DEALING_PATTERN_SEQ.NEXTVAL, 10000, 
   1,  2, 15, 
   '20100101');
Insert into EON_USER_DEALING_PATTERN
   (USER_DEALING_PATTERN_ID, DEALING_PATTERN_RELATION_ID, 
   COMPANY_DEALING_PATTERN_ID, USER_01, USER_02,
   START_DATE)
 Values
   (EON_USER_DEALING_PATTERN_SEQ.NEXTVAL, 10000, 
   1,  2, 16, 
   '20100101');
Insert into EON_USER_DEALING_PATTERN
   (USER_DEALING_PATTERN_ID, DEALING_PATTERN_RELATION_ID, 
   COMPANY_DEALING_PATTERN_ID, USER_01, USER_02,
   START_DATE)
 Values
   (EON_USER_DEALING_PATTERN_SEQ.NEXTVAL, 10000, 
   2,  2, 19, 
   '20100101');
Insert into EON_USER_DEALING_PATTERN
   (USER_DEALING_PATTERN_ID, DEALING_PATTERN_RELATION_ID, 
   COMPANY_DEALING_PATTERN_ID, USER_01, USER_02,
   START_DATE)
 Values
   (EON_USER_DEALING_PATTERN_SEQ.NEXTVAL, 10000, 
   2,  2, 20, 
   '20100101');
Insert into EON_USER_DEALING_PATTERN
   (USER_DEALING_PATTERN_ID, DEALING_PATTERN_RELATION_ID, 
   COMPANY_DEALING_PATTERN_ID, USER_01, USER_02,
   START_DATE)
 Values
   (EON_USER_DEALING_PATTERN_SEQ.NEXTVAL, 10000, 
   2,  2, 21, 
   '20100101');
   
Insert into EON_USER_DEALING_PATTERN
   (USER_DEALING_PATTERN_ID, DEALING_PATTERN_RELATION_ID, 
   COMPANY_DEALING_PATTERN_ID, USER_01, USER_02,
   START_DATE)
 Values
   (EON_USER_DEALING_PATTERN_SEQ.NEXTVAL, 10000, 
   1,  3, 14, 
   '20100101');
Insert into EON_USER_DEALING_PATTERN
   (USER_DEALING_PATTERN_ID, DEALING_PATTERN_RELATION_ID, 
   COMPANY_DEALING_PATTERN_ID, USER_01, USER_02,
   START_DATE)
 Values
   (EON_USER_DEALING_PATTERN_SEQ.NEXTVAL, 10000, 
   1,  3, 15, 
   '20100101');
Insert into EON_USER_DEALING_PATTERN
   (USER_DEALING_PATTERN_ID, DEALING_PATTERN_RELATION_ID, 
   COMPANY_DEALING_PATTERN_ID, USER_01, USER_02,
   START_DATE)
 Values
   (EON_USER_DEALING_PATTERN_SEQ.NEXTVAL, 10000, 
   2,  3, 19, 
   '20100101');
Insert into EON_USER_DEALING_PATTERN
   (USER_DEALING_PATTERN_ID, DEALING_PATTERN_RELATION_ID, 
   COMPANY_DEALING_PATTERN_ID, USER_01, USER_02,
   START_DATE)
 Values
   (EON_USER_DEALING_PATTERN_SEQ.NEXTVAL, 10000, 
   2,  3, 20, 
   '20100101');
   
Insert into EON_USER_DEALING_PATTERN
   (USER_DEALING_PATTERN_ID, DEALING_PATTERN_RELATION_ID, 
   COMPANY_DEALING_PATTERN_ID, USER_01, USER_02,
   START_DATE)
 Values
   (EON_USER_DEALING_PATTERN_SEQ.NEXTVAL, 10000, 
   1,  4, 14, 
   '20100101');
Insert into EON_USER_DEALING_PATTERN
   (USER_DEALING_PATTERN_ID, DEALING_PATTERN_RELATION_ID, 
   COMPANY_DEALING_PATTERN_ID, USER_01, USER_02,
   START_DATE)
 Values
   (EON_USER_DEALING_PATTERN_SEQ.NEXTVAL, 10000, 
   2,  4, 19, 
   '20100101');
   
-- COMPANY B Sellers
Insert into EON_USER_DEALING_PATTERN
   (USER_DEALING_PATTERN_ID, DEALING_PATTERN_RELATION_ID, 
   COMPANY_DEALING_PATTERN_ID, USER_01, USER_02,
   START_DATE)
 Values
   (EON_USER_DEALING_PATTERN_SEQ.NEXTVAL, 10000, 
   1,  8, 19, 
   '20100101');
Insert into EON_USER_DEALING_PATTERN
   (USER_DEALING_PATTERN_ID, DEALING_PATTERN_RELATION_ID, 
   COMPANY_DEALING_PATTERN_ID, USER_01, USER_02,
   START_DATE)
 Values
   (EON_USER_DEALING_PATTERN_SEQ.NEXTVAL, 10000, 
   1,  8, 20, 
   '20100101');
Insert into EON_USER_DEALING_PATTERN
   (USER_DEALING_PATTERN_ID, DEALING_PATTERN_RELATION_ID, 
   COMPANY_DEALING_PATTERN_ID, USER_01, USER_02,
   START_DATE)
 Values
   (EON_USER_DEALING_PATTERN_SEQ.NEXTVAL, 10000, 
   1,  8, 21, 
   '20100101');

Insert into EON_USER_DEALING_PATTERN
   (USER_DEALING_PATTERN_ID, DEALING_PATTERN_RELATION_ID, 
   COMPANY_DEALING_PATTERN_ID, USER_01, USER_02,
   START_DATE)
 Values
   (EON_USER_DEALING_PATTERN_SEQ.NEXTVAL, 10000, 
   1,  9, 19, 
   '20100101');
Insert into EON_USER_DEALING_PATTERN
   (USER_DEALING_PATTERN_ID, DEALING_PATTERN_RELATION_ID, 
   COMPANY_DEALING_PATTERN_ID, USER_01, USER_02,
   START_DATE)
 Values
   (EON_USER_DEALING_PATTERN_SEQ.NEXTVAL, 10000, 
   1,  9, 20, 
   '20100101');

Insert into EON_USER_DEALING_PATTERN
   (USER_DEALING_PATTERN_ID, DEALING_PATTERN_RELATION_ID, 
   COMPANY_DEALING_PATTERN_ID, USER_01, USER_02,
   START_DATE)
 Values
   (EON_USER_DEALING_PATTERN_SEQ.NEXTVAL, 10000, 
   1,  10, 19, 
   '20100101');
COMMIT;

--UAT USERS

-- Connect SELLER to BUYER Companies
Insert into EON_COMPANY_DEALING_PATTERN
   (COMPANY_DEALING_PATTERN_ID, DEALING_PATTERN_RELATION_ID, 
   COMPANY_01, COMPANY_02, ISACTIVE)
 Values
   (EON_COMPANY_DEALINGPATTERN_SEQ.NEXTVAL, 10000, 
   6, 8, 1);
Insert into EON_COMPANY_DEALING_PATTERN
   (COMPANY_DEALING_PATTERN_ID, DEALING_PATTERN_RELATION_ID, 
   COMPANY_01, COMPANY_02, ISACTIVE)
 Values
   (EON_COMPANY_DEALINGPATTERN_SEQ.NEXTVAL, 10000, 
   6, 9, 1);
Insert into EON_COMPANY_DEALING_PATTERN
   (COMPANY_DEALING_PATTERN_ID, DEALING_PATTERN_RELATION_ID, 
   COMPANY_01, COMPANY_02, ISACTIVE)
 Values
   (EON_COMPANY_DEALINGPATTERN_SEQ.NEXTVAL, 10000, 
   7, 9, 1);
   
-- Connect SELLER ADMIN to SELLER Users
Insert into EON_ADMIN_MEMBER
   (ADMIN_MEMBER_ID, ADMIN_ID, MEMBER_ID,
   DEALING_PATTERN_RELATION_ID, START_DATE)
 Values
   (EON_ADMIN_MEMBER_SEQ.NEXTVAL, 27, 24,
   10003, '20100101');
Insert into EON_ADMIN_MEMBER
   (ADMIN_MEMBER_ID, ADMIN_ID, MEMBER_ID,
   DEALING_PATTERN_RELATION_ID, START_DATE)
 Values
   (EON_ADMIN_MEMBER_SEQ.NEXTVAL, 27, 25,
   10003, '20100101');
Insert into EON_ADMIN_MEMBER
   (ADMIN_MEMBER_ID, ADMIN_ID, MEMBER_ID,
   DEALING_PATTERN_RELATION_ID, START_DATE)
 Values
   (EON_ADMIN_MEMBER_SEQ.NEXTVAL, 28, 26,
   10003, '20100101');

Insert into EON_ADMIN_MEMBER
   (ADMIN_MEMBER_ID, ADMIN_ID, MEMBER_ID,
   DEALING_PATTERN_RELATION_ID, START_DATE)
 Values
   (EON_ADMIN_MEMBER_SEQ.NEXTVAL, 33, 30,
   10003, '20100101');
Insert into EON_ADMIN_MEMBER
   (ADMIN_MEMBER_ID, ADMIN_ID, MEMBER_ID,
   DEALING_PATTERN_RELATION_ID, START_DATE)
 Values
   (EON_ADMIN_MEMBER_SEQ.NEXTVAL, 33, 31,
   10003, '20100101');
Insert into EON_ADMIN_MEMBER
   (ADMIN_MEMBER_ID, ADMIN_ID, MEMBER_ID,
   DEALING_PATTERN_RELATION_ID, START_DATE)
 Values
   (EON_ADMIN_MEMBER_SEQ.NEXTVAL, 34, 32,
   10003, '20100101');
   
-- Connect BUYER ADMIN to BUYER Users
Insert into EON_ADMIN_MEMBER
   (ADMIN_MEMBER_ID, ADMIN_ID, MEMBER_ID,
   DEALING_PATTERN_RELATION_ID, START_DATE)
 Values
   (EON_ADMIN_MEMBER_SEQ.NEXTVAL, 39, 36,
   10002, '20100101');
Insert into EON_ADMIN_MEMBER
   (ADMIN_MEMBER_ID, ADMIN_ID, MEMBER_ID,
   DEALING_PATTERN_RELATION_ID, START_DATE)
 Values
   (EON_ADMIN_MEMBER_SEQ.NEXTVAL, 39, 37,
   10002, '20100101');
Insert into EON_ADMIN_MEMBER
   (ADMIN_MEMBER_ID, ADMIN_ID, MEMBER_ID,
   DEALING_PATTERN_RELATION_ID, START_DATE)
 Values
   (EON_ADMIN_MEMBER_SEQ.NEXTVAL, 18, 38,
   10002, '20100101');
   
Insert into EON_ADMIN_MEMBER
   (ADMIN_MEMBER_ID, ADMIN_ID, MEMBER_ID,
   DEALING_PATTERN_RELATION_ID, START_DATE)
 Values
   (EON_ADMIN_MEMBER_SEQ.NEXTVAL, 44, 41,
   10002, '20100101');
Insert into EON_ADMIN_MEMBER
   (ADMIN_MEMBER_ID, ADMIN_ID, MEMBER_ID,
   DEALING_PATTERN_RELATION_ID, START_DATE)
 Values
   (EON_ADMIN_MEMBER_SEQ.NEXTVAL, 44, 42,
   10002, '20100101');
Insert into EON_ADMIN_MEMBER
   (ADMIN_MEMBER_ID, ADMIN_ID, MEMBER_ID,
   DEALING_PATTERN_RELATION_ID, START_DATE)
 Values
   (EON_ADMIN_MEMBER_SEQ.NEXTVAL, 45, 43,
   10002, '20100101');
   
-- Connect SELLER to BUYER Users
-- COMPANY A Sellers
Insert into EON_USER_DEALING_PATTERN
   (USER_DEALING_PATTERN_ID, DEALING_PATTERN_RELATION_ID, 
   COMPANY_DEALING_PATTERN_ID, USER_01, USER_02,
   START_DATE)
 Values
   (EON_USER_DEALING_PATTERN_SEQ.NEXTVAL, 10000, 
   1,  24, 36, 
   '20100101');
Insert into EON_USER_DEALING_PATTERN
   (USER_DEALING_PATTERN_ID, DEALING_PATTERN_RELATION_ID, 
   COMPANY_DEALING_PATTERN_ID, USER_01, USER_02,
   START_DATE)
 Values
   (EON_USER_DEALING_PATTERN_SEQ.NEXTVAL, 10000, 
   1,  24, 37, 
   '20100101');
Insert into EON_USER_DEALING_PATTERN
   (USER_DEALING_PATTERN_ID, DEALING_PATTERN_RELATION_ID, 
   COMPANY_DEALING_PATTERN_ID, USER_01, USER_02,
   START_DATE)
 Values
   (EON_USER_DEALING_PATTERN_SEQ.NEXTVAL, 10000, 
   1,  24, 38, 
   '20100101');
Insert into EON_USER_DEALING_PATTERN
   (USER_DEALING_PATTERN_ID, DEALING_PATTERN_RELATION_ID, 
   COMPANY_DEALING_PATTERN_ID, USER_01, USER_02,
   START_DATE)
 Values
   (EON_USER_DEALING_PATTERN_SEQ.NEXTVAL, 10000, 
   2,  24, 41, 
   '20100101');
Insert into EON_USER_DEALING_PATTERN
   (USER_DEALING_PATTERN_ID, DEALING_PATTERN_RELATION_ID, 
   COMPANY_DEALING_PATTERN_ID, USER_01, USER_02,
   START_DATE)
 Values
   (EON_USER_DEALING_PATTERN_SEQ.NEXTVAL, 10000, 
   2,  24, 42, 
   '20100101');
Insert into EON_USER_DEALING_PATTERN
   (USER_DEALING_PATTERN_ID, DEALING_PATTERN_RELATION_ID, 
   COMPANY_DEALING_PATTERN_ID, USER_01, USER_02,
   START_DATE)
 Values
   (EON_USER_DEALING_PATTERN_SEQ.NEXTVAL, 10000, 
   2,  24, 43, 
   '20100101');
   
Insert into EON_USER_DEALING_PATTERN
   (USER_DEALING_PATTERN_ID, DEALING_PATTERN_RELATION_ID, 
   COMPANY_DEALING_PATTERN_ID, USER_01, USER_02,
   START_DATE)
 Values
   (EON_USER_DEALING_PATTERN_SEQ.NEXTVAL, 10000, 
   1,  25, 36, 
   '20100101');
Insert into EON_USER_DEALING_PATTERN
   (USER_DEALING_PATTERN_ID, DEALING_PATTERN_RELATION_ID, 
   COMPANY_DEALING_PATTERN_ID, USER_01, USER_02,
   START_DATE)
 Values
   (EON_USER_DEALING_PATTERN_SEQ.NEXTVAL, 10000, 
   1,  25, 37, 
   '20100101');
Insert into EON_USER_DEALING_PATTERN
   (USER_DEALING_PATTERN_ID, DEALING_PATTERN_RELATION_ID, 
   COMPANY_DEALING_PATTERN_ID, USER_01, USER_02,
   START_DATE)
 Values
   (EON_USER_DEALING_PATTERN_SEQ.NEXTVAL, 10000, 
   2,  25, 41, 
   '20100101');
Insert into EON_USER_DEALING_PATTERN
   (USER_DEALING_PATTERN_ID, DEALING_PATTERN_RELATION_ID, 
   COMPANY_DEALING_PATTERN_ID, USER_01, USER_02,
   START_DATE)
 Values
   (EON_USER_DEALING_PATTERN_SEQ.NEXTVAL, 10000, 
   2,  25, 42, 
   '20100101');
   
Insert into EON_USER_DEALING_PATTERN
   (USER_DEALING_PATTERN_ID, DEALING_PATTERN_RELATION_ID, 
   COMPANY_DEALING_PATTERN_ID, USER_01, USER_02,
   START_DATE)
 Values
   (EON_USER_DEALING_PATTERN_SEQ.NEXTVAL, 10000, 
   1,  26, 36, 
   '20100101');
Insert into EON_USER_DEALING_PATTERN
   (USER_DEALING_PATTERN_ID, DEALING_PATTERN_RELATION_ID, 
   COMPANY_DEALING_PATTERN_ID, USER_01, USER_02,
   START_DATE)
 Values
   (EON_USER_DEALING_PATTERN_SEQ.NEXTVAL, 10000, 
   2,  26, 41, 
   '20100101');
   
-- COMPANY B Sellers
Insert into EON_USER_DEALING_PATTERN
   (USER_DEALING_PATTERN_ID, DEALING_PATTERN_RELATION_ID, 
   COMPANY_DEALING_PATTERN_ID, USER_01, USER_02,
   START_DATE)
 Values
   (EON_USER_DEALING_PATTERN_SEQ.NEXTVAL, 10000, 
   1,  30, 41,
   '20100101');
Insert into EON_USER_DEALING_PATTERN
   (USER_DEALING_PATTERN_ID, DEALING_PATTERN_RELATION_ID, 
   COMPANY_DEALING_PATTERN_ID, USER_01, USER_02,
   START_DATE)
 Values
   (EON_USER_DEALING_PATTERN_SEQ.NEXTVAL, 10000, 
   1,  30, 42, 
   '20100101');
Insert into EON_USER_DEALING_PATTERN
   (USER_DEALING_PATTERN_ID, DEALING_PATTERN_RELATION_ID, 
   COMPANY_DEALING_PATTERN_ID, USER_01, USER_02,
   START_DATE)
 Values
   (EON_USER_DEALING_PATTERN_SEQ.NEXTVAL, 10000, 
   1,  30, 43, 
   '20100101');

Insert into EON_USER_DEALING_PATTERN
   (USER_DEALING_PATTERN_ID, DEALING_PATTERN_RELATION_ID, 
   COMPANY_DEALING_PATTERN_ID, USER_01, USER_02,
   START_DATE)
 Values
   (EON_USER_DEALING_PATTERN_SEQ.NEXTVAL, 10000, 
   1,  31, 41, 
   '20100101');
Insert into EON_USER_DEALING_PATTERN
   (USER_DEALING_PATTERN_ID, DEALING_PATTERN_RELATION_ID, 
   COMPANY_DEALING_PATTERN_ID, USER_01, USER_02,
   START_DATE)
 Values
   (EON_USER_DEALING_PATTERN_SEQ.NEXTVAL, 10000, 
   1,  31, 42, 
   '20100101');

Insert into EON_USER_DEALING_PATTERN
   (USER_DEALING_PATTERN_ID, DEALING_PATTERN_RELATION_ID, 
   COMPANY_DEALING_PATTERN_ID, USER_01, USER_02,
   START_DATE)
 Values
   (EON_USER_DEALING_PATTERN_SEQ.NEXTVAL, 10000, 
   1,  32, 41, 
   '20100101');
COMMIT;