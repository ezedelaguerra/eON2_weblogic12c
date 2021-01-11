DECLARE
vOwner   dba_indexes.owner%TYPE;
vIdxName dba_indexes.index_name%TYPE;
vReindex VARCHAR2(100);
vCursor  NUMBER;
vNumRows INTEGER;
--CURSOR cGetIdx IS SELECT owner,index_name FROM dba_indexes WHERE upper(OWNER) LIKE upper('%1');
--CURSOR cGetIdx IS SELECT owner,index_name FROM dba_indexes WHERE upper(OWNER) LIKE upper('%1') and index_name like ('EON%');
CURSOR cGetIdx IS SELECT owner,index_name FROM dba_indexes WHERE upper(OWNER) LIKE upper('%1') and index_name like ('EON_ORDER%');
BEGIN
DBMS_OUTPUT.PUT_LINE('Reindexing ');
OPEN cGetIdx;
LOOP
FETCH cGetIdx INTO vOwner,vIdxName;
EXIT WHEN cGetIdx%NOTFOUND;
vCursor := DBMS_SQL.OPEN_CURSOR;
DBMS_OUTPUT.PUT_LINE('Reindexing ' || vOwner || '.' || vIdxName );
vReindex := 'ALTER INDEX ' || vOwner || '.' || vIdxName || ' REBUILD';
DBMS_SQL.PARSE(vCursor,vReindex,DBMS_SQL.NATIVE);
vNumRows := DBMS_SQL.EXECUTE(vCursor);
DBMS_SQL.CLOSE_CURSOR(vCursor);
END LOOP;
CLOSE cGetIdx;
END;
/