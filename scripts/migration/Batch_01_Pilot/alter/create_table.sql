CREATE TABLE migration_dealing_pattern
(batch_number   NUMBER(9)            NOT NULL,
 user1          VARCHAR2(32)		 NOT NULL,
 user2          VARCHAR2(32)		 NOT NULL,
 relation       VARCHAR2(128)		 NOT NULL,
 migration_status CHAR(1 BYTE)       DEFAULT 0                     NOT NULL,
 migration_date date default sysdate
 );
 
 ALTER TABLE migration_dealing_pattern ADD (
  UNIQUE (USER1, USER2, RELATION));

CREATE TABLE migration_user
(batch_number            NUMBER(9)    NOT NULL,
 username           VARCHAR2(32)      NOT NULL,
 password           VARCHAR2(32)      NOT NULL,
 company_name       VARCHAR2(200)     NOT NULL,
 migration_status   CHAR(1 BYTE)       DEFAULT 0                     NOT NULL,
 migration_date date default sysdate
 );
 
 ALTER TABLE migration_user ADD (
  UNIQUE (username));
 
CREATE TABLE migration_log_audit
(batch_number            NUMBER(9),
 procedure_name          VARCHAR2(32),
 parameter_01            VARCHAR2(32),
 parameter_02            VARCHAR2(32),
 time_start              TIMESTAMP,
 time_finish             TIMESTAMP,
 time_duration           INTERVAL DAY(3) TO SECOND(3)
 );