CREATE TABLE LK_RATE_PLAN
(
  ID    INTEGER,
  NAME  VARCHAR2(50 BYTE)                       NOT NULL
)
TABLESPACE USERS
RESULT_CACHE (MODE DEFAULT)
PCTUSED    0
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          64K
            NEXT             1M
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
            FLASH_CACHE      DEFAULT
            CELL_FLASH_CACHE DEFAULT
           )
LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;


CREATE TABLE TRANSACTIONS
(
  MSISDN          VARCHAR2(4000 BYTE)           NOT NULL,
  RATEPLAN        VARCHAR2(50 BYTE)             NOT NULL,
  BALANCE         VARCHAR2(100 BYTE),
  ENTRYDATE       TIMESTAMP(6)                  DEFAULT CURRENT_DATE,
  TRANSACTION_ID  INTEGER                       NOT NULL
)
NOCOMPRESS 
TABLESPACE USERS
RESULT_CACHE (MODE DEFAULT)
PCTUSED    0
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          64K
            NEXT             1M
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
            FLASH_CACHE      DEFAULT
            CELL_FLASH_CACHE DEFAULT
           )
LOGGING
PARTITION BY RANGE (ENTRYDATE)
(  
  PARTITION P2017_01_01 VALUES LESS THAN (TIMESTAMP' 2017-01-01 00:00:00')
    LOGGING
    NOCOMPRESS 
    TABLESPACE USERS
    PCTFREE    10
    INITRANS   1
    MAXTRANS   255
    STORAGE    (
                INITIAL          64K
                NEXT             1M
                MINEXTENTS       1
                MAXEXTENTS       UNLIMITED
                BUFFER_POOL      DEFAULT
                FLASH_CACHE      DEFAULT
                CELL_FLASH_CACHE DEFAULT
               ),  
  PARTITION P2017_02_01 VALUES LESS THAN (TIMESTAMP' 2017-02-01 00:00:00')
    LOGGING
    NOCOMPRESS 
    TABLESPACE USERS
    PCTFREE    10
    INITRANS   1
    MAXTRANS   255
    STORAGE    (
                INITIAL          64K
                NEXT             1M
                MINEXTENTS       1
                MAXEXTENTS       UNLIMITED
                BUFFER_POOL      DEFAULT
                FLASH_CACHE      DEFAULT
                CELL_FLASH_CACHE DEFAULT
               ),  
  PARTITION P2017_03_01 VALUES LESS THAN (TIMESTAMP' 2017-03-01 00:00:00')
    LOGGING
    NOCOMPRESS 
    TABLESPACE USERS
    PCTFREE    10
    INITRANS   1
    MAXTRANS   255
    STORAGE    (
                INITIAL          64K
                NEXT             1M
                MINEXTENTS       1
                MAXEXTENTS       UNLIMITED
                BUFFER_POOL      DEFAULT
                FLASH_CACHE      DEFAULT
                CELL_FLASH_CACHE DEFAULT
               )
)
NOCACHE
NOPARALLEL
MONITORING;


--BEGIN
--  SYS.DBMS_AQADM.CREATE_QUEUE_TABLE
--  (
--    QUEUE_TABLE           =>        'TRANSACTION_QUEUE_TABLE'
--   ,QUEUE_PAYLOAD_TYPE    =>        'SYS.AQ$_JMS_TEXT_MESSAGE'
--   ,COMPATIBLE            =>        '10.0.0'
--   ,STORAGE_CLAUSE        =>        'NOCOMPRESS
--                                     TABLESPACE USERS
--                                     RESULT_CACHE (MODE DEFAULT)
--                                     PCTUSED    0
--                                     PCTFREE    10
--                                     INITRANS   1
--                                     MAXTRANS   255
--                                     STORAGE    (
--                                                 INITIAL          64K
--                                                 NEXT             1M
--                                                 MINEXTENTS       1
--                                                 MAXEXTENTS       UNLIMITED
--                                                 PCTINCREASE      0
--                                                 BUFFER_POOL      DEFAULT
--                                                 FLASH_CACHE      DEFAULT
--                                                 CELL_FLASH_CACHE DEFAULT
--                                                )'
--   ,SORT_LIST             =>        'ENQ_TIME'
--   ,MULTIPLE_CONSUMERS    =>         FALSE
--   ,MESSAGE_GROUPING      =>         0
--   ,SECURE                =>         FALSE
--   );
--End;
--/


CREATE UNIQUE INDEX LK_RATE_PLAN_PK ON LK_RATE_PLAN
(ID)
LOGGING
TABLESPACE USERS
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          64K
            NEXT             1M
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
            FLASH_CACHE      DEFAULT
            CELL_FLASH_CACHE DEFAULT
           )
NOPARALLEL;


ALTER TABLE LK_RATE_PLAN ADD (
  CONSTRAINT LK_RATE_PLAN_PK
  PRIMARY KEY
  (ID)
  USING INDEX LK_RATE_PLAN_PK
  ENABLE VALIDATE);
BEGIN
  SYS.DBMS_AQADM.CREATE_QUEUE
  (
    QUEUE_NAME          =>   'TRANSACTION_QUEUE'
   ,QUEUE_TABLE         =>   'TRANSACTION_QUEUE_TABLE'
   ,QUEUE_TYPE          =>   SYS.DBMS_AQADM.NORMAL_QUEUE
   ,MAX_RETRIES         =>   5
   ,RETRY_DELAY         =>   0
   ,RETENTION_TIME      =>   0
   );
END;
/

BEGIN
  SYS.DBMS_AQADM.START_QUEUE
  (
    QUEUE_NAME => 'TRANSACTION_QUEUE'
   ,ENQUEUE => TRUE 
   ,DEQUEUE => TRUE 
   );
END;
/
BEGIN
  SYS.DBMS_AQADM.CREATE_QUEUE_TABLE
  (
    QUEUE_TABLE           =>        'TRANSACTION_QUEUE_TABLE'
   ,QUEUE_PAYLOAD_TYPE    =>        'SYS.AQ$_JMS_TEXT_MESSAGE'
   ,COMPATIBLE            =>        '10.0.0'
   ,STORAGE_CLAUSE        =>        'NOCOMPRESS
                                     TABLESPACE USERS
                                     RESULT_CACHE (MODE DEFAULT)
                                     PCTUSED    0
                                     PCTFREE    10
                                     INITRANS   1
                                     MAXTRANS   255
                                     STORAGE    (
                                                 INITIAL          64K
                                                 NEXT             1M
                                                 MINEXTENTS       1
                                                 MAXEXTENTS       UNLIMITED
                                                 PCTINCREASE      0
                                                 BUFFER_POOL      DEFAULT
                                                 FLASH_CACHE      DEFAULT
                                                 CELL_FLASH_CACHE DEFAULT
                                                )'
   ,SORT_LIST             =>        'ENQ_TIME'
   ,MULTIPLE_CONSUMERS    =>         FALSE
   ,MESSAGE_GROUPING      =>         0
   ,SECURE                =>         FALSE
   );
End;
/


-- Oracle will automatically create Exception Queue AQ$_TRANSACTION_QUEUE_TABLE_E 
-- when Queue Table TRANSACTION_QUEUE_TABLE is created.


BEGIN
  SYS.DBMS_AQADM.CREATE_QUEUE
  (
    QUEUE_NAME          =>   'TRANSACTION_QUEUE'
   ,QUEUE_TABLE         =>   'TRANSACTION_QUEUE_TABLE'
   ,QUEUE_TYPE          =>   SYS.DBMS_AQADM.NORMAL_QUEUE
   ,MAX_RETRIES         =>   5
   ,RETRY_DELAY         =>   0
   ,RETENTION_TIME      =>   0
   );
END;
/

BEGIN
  SYS.DBMS_AQADM.START_QUEUE
  (
    QUEUE_NAME => 'TRANSACTION_QUEUE'
   ,ENQUEUE => TRUE 
   ,DEQUEUE => TRUE 
   );
END;
/


CREATE OR REPLACE PROCEDURE deque_messages(read_count IN NUMBER)
is
BEGIN
   DECLARE
      MESSAGE                SYS.AQ$_JMS_TEXT_MESSAGE;
      dequeue_options        DBMS_AQ.DEQUEUE_OPTIONS_T;
      message_properties     DBMS_AQ.MESSAGE_PROPERTIES_T;
      msgid                  RAW (16);
      xml                    XMLTYPE;
      msisdn                 PLS_INTEGER;
      balance                PLS_INTEGER;
      rate_plan              PLS_INTEGER;
      transaction_id         PLS_INTEGER;
      queue_count            PLS_INTEGER;
      temp_rate_plan_id      PLS_INTEGER;
      skip                   BOOLEAN;
      
   BEGIN
      SELECT COUNT (*) INTO queue_count FROM TRANSACTION_QUEUE_TABLE;

      --DBMS_OUTPUT.PUT_LINE (queue_count);
      if queue_count > read_count
      then
        queue_count := read_count;
      end if;

      FOR i IN 0 .. queue_count - 1
      LOOP
         -- Dequeue this message from AQ queue using DBMS_AQ package
         DBMS_AQ.dequeue (queue_name           => 'TRANSACTION_QUEUE',
                          dequeue_options      => dequeue_options,
                          message_properties   => message_properties,
                          payload              => MESSAGE,
                          msgid                => msgid);
        -- DBMS_OUTPUT.PUT_LINE (MESSAGE.text_vc);
         xml := xmltype.createxml (MESSAGE.text_vc);
         msisdn := xml.EXTRACT ('/transaction/msisdn/text()').getNumberVal ();
         -- DBMS_OUTPUT.PUT_LINE (msisdn);
         balance :=
            xml.EXTRACT ('/transaction/balance/text()').getNumberVal ();
         --DBMS_OUTPUT.PUT_LINE (balance);
         rate_plan :=
            xml.EXTRACT ('/transaction/rate_plan/text()').getNumberVal ();
         --DBMS_OUTPUT.PUT_LINE (rate_plan);
         transaction_id :=
            xml.EXTRACT ('/transaction/transcation_id/text()').getNumberVal ();

         -- DBMS_OUTPUT.PUT_LINE (transaction_id);

         --validate
         IF (LENGTH (msisdn) != 10 )
         THEN
            skip := TRUE;
         END IF;

         IF (balance > 20)
         THEN
            skip := TRUE;
         END IF;

         BEGIN
            SELECT ID
              INTO temp_rate_plan_id
              FROM LK_RATE_PLAN
             WHERE id = rate_plan;
         EXCEPTION
            WHEN NO_DATA_FOUND
            THEN
               skip := TRUE;
         END;

         IF NOT skip
         THEN
         
           -- DBMS_OUTPUT.put_line ('insert in table');

            INSERT INTO TRANSACTIONS
                 VALUES (msisdn,
                         rate_plan,
                         balance,
                         CURRENT_TIMESTAMP,
                         transaction_id);
         END IF;

         skip := FALSE;
      END LOOP;
   END;
END;
/