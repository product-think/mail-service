create schema mail_service;

CREATE TABLE SPRING_SESSION (
                                PRIMARY_ID CHAR(36) NOT NULL,
                                SESSION_ID CHAR(36) NOT NULL,
                                CREATION_TIME BIGINT NOT NULL,
                                LAST_ACCESS_TIME BIGINT NOT NULL,
                                MAX_INACTIVE_INTERVAL INT NOT NULL,
                                EXPIRY_TIME BIGINT NOT NULL,
                                PRINCIPAL_NAME VARCHAR(100),
                                CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (PRIMARY_ID)
);

CREATE UNIQUE INDEX SPRING_SESSION_IX1 ON SPRING_SESSION (SESSION_ID);
CREATE INDEX SPRING_SESSION_IX2 ON SPRING_SESSION (EXPIRY_TIME);
CREATE INDEX SPRING_SESSION_IX3 ON SPRING_SESSION (PRINCIPAL_NAME);

CREATE TABLE SPRING_SESSION_ATTRIBUTES (
                                           SESSION_PRIMARY_ID CHAR(36) NOT NULL,
                                           ATTRIBUTE_NAME VARCHAR(200) NOT NULL,
                                           ATTRIBUTE_BYTES BLOB NOT NULL,
                                           CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
                                           CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES SPRING_SESSION(PRIMARY_ID) ON DELETE CASCADE
);

create table ACCOUNT
(
    ID                 bigint auto_increment primary key,
    LOGIN_ID           varchar(255) not null,
    PASSWORD           varchar(255) not null,
    ROLES              varchar(255),
    NAME               varchar(255),
    REFERER            varchar(255),
    SYSTEM_CREATE_DATE datetime(6)  not null,
    SYSTEM_ENABLE      bit          not null,
    SYSTEM_UPDATE_DATE datetime(6)  not null
);
INSERT INTO ACCOUNT (LOGIN_ID, NAME, PASSWORD, ROLES, SYSTEM_CREATE_DATE, SYSTEM_ENABLE, SYSTEM_UPDATE_DATE) VALUES ('admin@example.com', '管理者1', '{bcrypt}$2a$10$uhuqnvtjTayBhSjs7ezeB.2DG5GlIERAawzRoCROyTxWpzwKy7T.e', 'ROLE_ADMIN', current_timestamp, true, current_timestamp);

create table MAIL_LOG
(
    ID                 bigint auto_increment primary key,
    ADDRESS_FROM       varchar(255),
    ADDRESS_TO         varchar(255),
    ADDRESS_CC         varchar(255),
    ADDRESS_BCC        varchar(255),
    SUBJECT            varchar(255) null,
    BODY               longtext,
    SEND_FAILURE       bit,
    STATUS_CODE        int,
    SYSTEM_CREATE_DATE datetime(6)  not null,
    SYSTEM_ENABLE      bit          not null,
    SYSTEM_UPDATE_DATE datetime(6)  not null
);

