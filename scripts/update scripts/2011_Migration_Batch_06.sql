--Change size of EXTERNAL_SKU_ID Column from 42 to 64
ALTER TABLE
   EON_SKU
MODIFY
   (   EXTERNAL_SKU_ID varchar2(64 char)  );