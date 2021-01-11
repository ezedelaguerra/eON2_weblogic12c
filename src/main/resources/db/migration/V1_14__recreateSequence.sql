CREATE OR REPLACE PROCEDURE PROC_RECREATE_SEQUENCE AS
BEGIN
  DECLARE
  
    sql_stmt  VARCHAR2(200);
    v_sku_id  EON01.EON_SKU.SKU_ID%TYPE;
    v_sku_ba_id  EON01.EON_SKU_BA.SKU_BA_ID%TYPE;
    v_order_id  EON01.EON_ORDER.ORDER_ID%TYPE;
    v_order_item_id EON01.EON_ORDER_ITEM.ORDER_ITEM_ID%TYPE;
    v_alloc_id  EON01.EON_ORDER_ALLOCATION.ORDER_ALLOCATION_ID%TYPE;
    v_recv_id   EON01.EON_ORDER_RECEIVED.ORDER_RECEIVED_ID%TYPE;
    v_bill_id   EON01.EON_ORDER_BILLING.ORDER_BILLING_ID%TYPE;
    v_inbox_id  EON01.EON_COMMENTS_INBOX.COMMENTS_INBOX_ID%TYPE;
    v_outbox_id EON01.EON_COMMENTS_OUTBOX.COMMENTS_OUTBOX_ID%TYPE;
    
  BEGIN
    
    SELECT MAX(SKU_ID)+1 INTO v_sku_id FROM EON01.EON_SKU; 
    EXECUTE IMMEDIATE 'DROP SEQUENCE EON01.EON_SKU_ID_SEQ';
    EXECUTE IMMEDIATE 'CREATE SEQUENCE EON01.EON_SKU_ID_SEQ MINVALUE 1 MAXVALUE 999999999 INCREMENT BY 1 START WITH ' || v_sku_id || ' CACHE 20 ORDER NOCYCLE';
    
    SELECT MAX(SKU_BA_ID)+1 INTO v_sku_ba_id FROM EON01.EON_SKU_BA; 
    EXECUTE IMMEDIATE 'DROP SEQUENCE EON01.EON_SKU_BA_ID_SEQ';
    EXECUTE IMMEDIATE 'CREATE SEQUENCE EON01.EON_SKU_BA_ID_SEQ MINVALUE 1 MAXVALUE 999999999 INCREMENT BY 1 START WITH ' || v_sku_ba_id || ' CACHE 20 ORDER NOCYCLE';
   
    SELECT MAX(ORDER_ID)+1 INTO v_order_id FROM EON01.EON_ORDER; 
    EXECUTE IMMEDIATE 'DROP SEQUENCE EON01.EON_ORDER_ID_SEQ';
    EXECUTE IMMEDIATE 'CREATE SEQUENCE EON01.EON_ORDER_ID_SEQ MINVALUE 1 MAXVALUE 999999999 INCREMENT BY 1 START WITH ' || v_order_id || ' CACHE 20 ORDER NOCYCLE';
    
    SELECT MAX(ORDER_ITEM_ID)+1 INTO v_order_item_id FROM EON01.EON_ORDER_ITEM; 
    EXECUTE IMMEDIATE 'DROP SEQUENCE EON01.EON_ORDER_ITEM_SEQ';
    EXECUTE IMMEDIATE 'CREATE SEQUENCE EON01.EON_ORDER_ITEM_SEQ MINVALUE 1 MAXVALUE 999999999 INCREMENT BY 1 START WITH ' || v_order_item_id || ' CACHE 20 ORDER NOCYCLE';
    
    SELECT MAX(ORDER_ALLOCATION_ID)+1 INTO v_alloc_id FROM EON01.EON_ORDER_ALLOCATION; 
    EXECUTE IMMEDIATE 'DROP SEQUENCE EON01.EON_ORDER_ALLOCATION_ID_SEQ';
    EXECUTE IMMEDIATE 'CREATE SEQUENCE EON01.EON_ORDER_ALLOCATION_ID_SEQ MINVALUE 1 MAXVALUE 999999999 INCREMENT BY 1 START WITH ' || v_alloc_id || ' CACHE 20 ORDER NOCYCLE';
    
    SELECT MAX(ORDER_RECEIVED_ID)+1 INTO v_recv_id FROM EON01.EON_ORDER_RECEIVED; 
    EXECUTE IMMEDIATE 'DROP SEQUENCE EON01.EON_ORDER_RECEIVED_SEQ';
    EXECUTE IMMEDIATE 'CREATE SEQUENCE EON01.EON_ORDER_RECEIVED_SEQ MINVALUE 1 MAXVALUE 999999999 INCREMENT BY 1 START WITH ' || v_recv_id || ' CACHE 20 ORDER NOCYCLE';
    
    SELECT MAX(ORDER_BILLING_ID)+1 INTO v_bill_id FROM EON01.EON_ORDER_BILLING; 
    EXECUTE IMMEDIATE 'DROP SEQUENCE EON01.EON_ORDER_BILLING_SEQ';
    EXECUTE IMMEDIATE 'CREATE SEQUENCE EON01.EON_ORDER_BILLING_SEQ MINVALUE 1 MAXVALUE 999999999 INCREMENT BY 1 START WITH ' || v_bill_id || ' CACHE 20 ORDER NOCYCLE';

    SELECT MAX(COMMENTS_INBOX_ID)+1 INTO v_inbox_id FROM EON01.EON_COMMENTS_INBOX; 
    EXECUTE IMMEDIATE 'DROP SEQUENCE EON01.EON_COMMENTS_INBOX_ID_SEQ';
    EXECUTE IMMEDIATE 'CREATE SEQUENCE EON01.EON_COMMENTS_INBOX_ID_SEQ MINVALUE 1 MAXVALUE 999999999 INCREMENT BY 1 START WITH ' || v_inbox_id || ' CACHE 20 ORDER NOCYCLE';

    SELECT MAX(COMMENTS_OUTBOX_ID)+1 INTO v_outbox_id FROM EON01.EON_COMMENTS_OUTBOX; 
    EXECUTE IMMEDIATE 'DROP SEQUENCE EON01.EON_COMMENTS_OUTBOX_ID_SEQ';
    EXECUTE IMMEDIATE 'CREATE SEQUENCE EON01.EON_COMMENTS_OUTBOX_ID_SEQ MINVALUE 1 MAXVALUE 999999999 INCREMENT BY 1 START WITH ' || v_outbox_id || ' CACHE 20 ORDER NOCYCLE';
    
  END;
END PROC_RECREATE_SEQUENCE;