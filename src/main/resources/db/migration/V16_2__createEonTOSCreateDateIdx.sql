--------------------------------------------------------
--  Index used when querying the latest TOS
--------------------------------------------------------
 CREATE INDEX EON_TOS_CREATE_DATE_IDX ON EON_TERMS_OF_SERVICE(CREATEDDATE)
 TABLESPACE EON_INDX01;