--------------------------------------------------------
--  Redmine 2050
--------------------------------------------------------

CREATE INDEX EON_ORDER_INDEX ON EON_ORDER(DELIVERY_DATE, BUYER_ID)
 TABLESPACE EON_INDX01;
 
CREATE INDEX EON_ORDER_ITEM_INDEX ON EON_ORDER_ITEM(ORDER_ID)
 TABLESPACE EON_INDX01;
 
CREATE INDEX EON_ORDER_ALLOCATION_INDEX ON EON_ORDER_ALLOCATION(SKU_ID)
 TABLESPACE EON_INDX01;
