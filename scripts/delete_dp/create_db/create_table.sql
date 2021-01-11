CREATE TABLE migration_dealing_pattern
(fft_dealing_pattern_id    NUMBER(9),
 batch_number   NUMBER(9)            NOT NULL,
 user1          VARCHAR2(32)		 NOT NULL,
 user2          VARCHAR2(32)		 NOT NULL,
 relation       VARCHAR2(128)		 NOT NULL,
 dp_delete_status CHAR(1 BYTE)       DEFAULT 0                     NOT NULL,
 migration_date date default sysdate
 );
 
 ALTER TABLE migration_dealing_pattern ADD (
  UNIQUE (USER1, USER2, RELATION));