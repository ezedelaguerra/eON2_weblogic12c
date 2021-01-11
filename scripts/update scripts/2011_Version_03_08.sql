--Update EON_ORDER_RECEIVED.ISAPPROVED, set to 0 where value is null
UPDATE EON_ORDER_RECEIVED SET ISAPPROVED = 0 WHERE ISAPPROVED IS NULL;

--Alter ISAPPROVED in EON_ORDER_RECEIVED, set default value to 0 (zero)
ALTER TABLE EON_ORDER_RECEIVED MODIFY ISAPPROVED DEFAULT 0;

--Add ISAPPROVED in EON_ORDER_ALLOCATION
ALTER TABLE EON_ORDER_ALLOCATION ADD (ISAPPROVED  CHAR(1 BYTE) DEFAULT 0);

--Update EON_ORDER_ALLOCATION.ISAPPROVED, set to 1 where EON_ORDER_RECEIVED.ISAPPROVED is equal to 1
UPDATE EON_ORDER_ALLOCATION SET ISAPPROVED = '1'
  WHERE ORDER_ALLOCATION_ID IN (
    SELECT ORDER_ALLOCATION_ID FROM EON_ORDER_ALLOCATION alloc, 
      (SELECT ORDER_ID, SKU_ID FROM EON_ORDER_RECEIVED WHERE ISAPPROVED = '1') tablea
    WHERE alloc.ORDER_ID = tablea.ORDER_ID AND alloc.SKU_ID = tablea.SKU_ID
    );

COMMIT;