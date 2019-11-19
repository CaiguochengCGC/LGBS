

/*==============================================================*/
/* Table: PUB_DATA_DICT                                         */
/*==============================================================*/
create table PUB_DATA_DICT  (
   PK_ID             VARCHAR(40)                    not null,
   CODE_TYPE         VARCHAR(40)                    not null,
   CODE_TYPE_NAME       VARCHAR(80)                    not null,
   CODE_VALUE        VARCHAR(50)                    not null,
   CODE_VALUE_NAME      VARCHAR(40),
   SORT_NO              INT,
   CODE_DESC            VARCHAR(128),
   OTHER_CODE_VALUE1    VARCHAR(200),
   OTHER_CODE_VALUE2    VARCHAR(200),
   IS_EDIT              INT                       not null,
   LAST_UPDATE_USERNAME VARCHAR(20),
   LAST_UPDATE_IP       VARCHAR(20),
   LAST_UPDATE_TIME     DATETIME,
   CREATE_TIME          DATETIME,
   constraint PK_PUB_DATA_DICT primary key (PK_ID)
);


/*==============================================================*/
/* Table: PUB_MODULE                                            */
/*==============================================================*/
create table PUB_MODULE  (
   PARENT_MODULE        VARCHAR(20),
   PK_MODULE_CODE       VARCHAR(20)                    not null,
   MODULE_NAME          VARCHAR(40),
   SORT                 INT,
   MODULE_LEVEL         INT,
   LAST_UPDATE_USERNAME VARCHAR(20),
   LAST_UPDATE_IP       VARCHAR(20),
   LAST_UPDATE_TIME     DATETIME,
   CREATE_TIME          DATETIME,
   constraint PK_PUB_MODULE primary key (PK_MODULE_CODE)
);

/*==============================================================*/
/* Table: PUB_ROLE                                              */
/*==============================================================*/
create table PUB_ROLE  (
   PK_ROLE_ID           VARCHAR(36)                    not null,
   ROLE_NAME            VARCHAR(20),
   ROLE_DESCRIPTION     VARCHAR(90),
   LAST_UPDATE_USERNAME VARCHAR(20),
   LAST_UPDATE_IP       VARCHAR(20),
   LAST_UPDATE_TIME     DATETIME,
   CREATE_TIME          DATETIME,
   constraint PK_PUB_ROLE primary key (PK_ROLE_ID)
);


/*==============================================================*/
/* Table: PUB_ROLE_PRIVILEGES                                   */
/*==============================================================*/
create table PUB_ROLE_PRIVILEGES  (
   PK_ROLE_ID           VARCHAR(36)                    not null,
   PK_PRIVILEGES_ID     VARCHAR(36)                    not null,
   LAST_UPDATE_USERNAME VARCHAR(20),
   LAST_UPDATE_IP       VARCHAR(20),
   LAST_UPDATE_TIME     DATETIME,
   CREATE_TIME          DATETIME,
   constraint PK_PUB_ROLE_PRIVILEGES primary key (PK_ROLE_ID, PK_PRIVILEGES_ID)
);


/*==============================================================*/
/* Table: PUB_USER                                              */
/*==============================================================*/
create table PUB_USER  (
   PK_USER_NAME         VARCHAR(20)                    not null,
   USER_CNAME           VARCHAR(20),
   USER_PWD             VARCHAR(100),
   USER_STATUS          INT                      default 1 not null,
   IS_UPDATE_PWD        INT                      default 0 not null,
   DEVISION             VARCHAR(100),
   LAST_UPDATE_USERNAME VARCHAR(20),
   LAST_UPDATE_IP       VARCHAR(20),
   LAST_UPDATE_TIME     DATETIME,
   CREATE_TIME          DATETIME,
   constraint PK_PUB_USER primary key (PK_USER_NAME)
);

/*==============================================================*/
/* Table: PUB_USER_ROLE                                         */
/*==============================================================*/
create table PUB_USER_ROLE  (
   PK_USER_NAME         VARCHAR(36)                    not null,
   PK_ROLE_ID           VARCHAR(36)                    not null,
   LAST_UPDATE_USERNAME VARCHAR(20),
   LAST_UPDATE_IP       VARCHAR(20),
   LAST_UPDATE_TIME     DATETIME,
   CREATE_TIME          DATETIME,
   constraint PK_PUB_USER_ROLE primary key (PK_USER_NAME, PK_ROLE_ID)
);


/*==============================================================*/
/* Table: PUB_PRIVILEGES                                        */
/*==============================================================*/
create table PUB_PRIVILEGES  (
   PK_PRIVILEGES_ID     VARCHAR(36)                    not null,
   PRIVILEGES_CODE      VARCHAR(20),
   TYPE                 VARCHAR(20),
   CONTENT              VARCHAR(40),
   DESCRIPTION          VARCHAR(90),
   CATEGORY             VARCHAR(20),
   I18N_RESOURCES       VARCHAR(90),
   LOCAL_IP             VARCHAR(15),
   SORT                 INT,
   LAST_UPDATE_USERNAME VARCHAR(20),
   LAST_UPDATE_IP       VARCHAR(20),
   LAST_UPDATE_TIME     DATETIME,
   CREATE_TIME          DATETIME,
   constraint PK_PUB_PRIVILEGES primary key (PK_PRIVILEGES_ID)
);

/*==============================================================*/
/* Table: SYS_PARAM                                             */
/*==============================================================*/
create table SYS_PARAM  (
   PARAM_CODE           VARCHAR(30)                    not null,
   PARAM_GROUP          VARCHAR(2),
   PARAM_NAME           VARCHAR(60),
   PARAM_VAL            VARCHAR(1024)                  not null,
   IS_EDIT              CHAR(1)                        default '0',
   PARAM_TYPE           VARCHAR(30),
   NOTE                 VARCHAR(255)                  ,
   LAST_UPDATE_USERNAME VARCHAR(20),
   LAST_UPDATE_IP       VARCHAR(20),
   LAST_UPDATE_TIME     DATETIME,
   CREATE_TIME          DATETIME,
   UDA_1                VARCHAR(50)          null,
   UDA_2                VARCHAR(50)          null,
   UDA_3                VARCHAR(50)          null,
   constraint AK_KEY_1_SYS_PARA unique (PARAM_CODE)
);

--ÎÄ¼þÉý¼¶
create table PUB_CLIENT_VERSION (
   PK_ID                varchar(40)          not null,
   VERSION_NO           varchar(10)          null,
   BUILD_NO             varchar(20)          null,
   VERSION_STATUS       INT                  null,
   USED_TIME            DATETIME             null,
   LAST_UPDATE_USERNAME VARCHAR(20)          null,
   LAST_UPDATE_TIME     DATETIME             null,
   CREATE_TIME          DATETIME             null,
   UDA_1                varchar(50)          null,
   UDA_2                varchar(50)          null,
   UDA_3                varchar(50)          null,
   UDA_4                varchar(50)          null,
   UDA_5                varchar(50)          null,
   constraint PK_PUB_CLIENT_VERSION primary key (PK_ID)
);

create table PUB_FILE_PROPERTY (
   PK_ID                VARCHAR(40)          not null,
   FILE_TYPE            varchar(20)          not null,
   FILE_NAME            varchar(100)         not null,
   FILE_EXTNAME         varchar(20)          null,
   FILE_SIZE            bigint               null,
   MD_CODE              varchar(50)          null,
   RELATE_ID            varchar(100)         not null,
   LAST_UPDATE_USERNAME varchar(50)          null,
   CREATE_TIME          datetime             null,
   LAST_UPDATE_TIME     datetime             null,
   UDA_1                varchar(50)          null,
   UDA_2                varchar(50)          null,
   UDA_3                varchar(50)          null,
   UDA_4                varchar(50)          null,
   UDA_5                varchar(50)          null,
   constraint PK_PUB_FILE_PROPERTY primary key (PK_ID)
);

create table PUB_FILE (
   PK_ID                VARCHAR(40)          not null,
   FILE_DATA            varbinary(Max)       null,
   constraint PK_PUB_FILE primary key (PK_ID)
);

