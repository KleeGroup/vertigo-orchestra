-- ============================================================
--   Nom de SGBD      :  PostgreSql                     
--   Date de création :  7 avr. 2016  18:27:38                     
-- ============================================================

-- ============================================================
--   Drop                                       
-- ============================================================
drop table O_ACTIVITY cascade;
drop table O_ACTIVITY_EXECUTION cascade;
drop table O_ACTIVITY_LOG cascade;
drop table O_ACTIVITY_WORKSPACE cascade;
drop table O_EXECUTION_STATE cascade;
drop table O_NODE cascade;
drop table O_PLANIFICATION_STATE cascade;
drop table O_PROCESS cascade;
drop table O_PROCESS_EXECUTION cascade;
drop table O_PROCESS_PLANIFICATION cascade;
drop table O_PROCESS_TYPE cascade;
drop table TRIGGER_TYPE cascade;



-- ============================================================
--   Sequences                                      
-- ============================================================
create sequence SEQ_O_ACTIVITY
	start with 1000 cache 20; 

create sequence SEQ_O_ACTIVITY_EXECUTION
	start with 1000 cache 20; 

create sequence SEQ_O_ACTIVITY_LOG
	start with 1000 cache 20; 

create sequence SEQ_O_ACTIVITY_WORKSPACE
	start with 1000 cache 20; 

create sequence SEQ_O_EXECUTION_STATE
	start with 1000 cache 20; 

create sequence SEQ_O_NODE
	start with 1000 cache 20; 

create sequence SEQ_O_PLANIFICATION_STATE
	start with 1000 cache 20; 

create sequence SEQ_O_PROCESS
	start with 1000 cache 20; 

create sequence SEQ_O_PROCESS_EXECUTION
	start with 1000 cache 20; 

create sequence SEQ_O_PROCESS_PLANIFICATION
	start with 1000 cache 20; 

create sequence SEQ_O_PROCESS_TYPE
	start with 1000 cache 20; 

create sequence SEQ_TRIGGER_TYPE
	start with 1000 cache 20; 


-- ============================================================
--   Table : O_ACTIVITY                                        
-- ============================================================
create table O_ACTIVITY
(
    ACT_ID      	 NUMERIC     	not null,
    NAME        	 VARCHAR(100)	,
    LABEL       	 VARCHAR(100)	,
    NUMBER      	 NUMERIC     	,
    MILESTONE   	 BOOL        	,
    ENGINE      	 VARCHAR(200)	,
    PRO_ID      	 NUMERIC     	,
);

comment on column O_ACTIVITY.ACT_ID is
'Id Activité';

comment on column O_ACTIVITY.NAME is
'Nom de l''activité';

comment on column O_ACTIVITY.LABEL is
'Libellé de l''activité';

comment on column O_ACTIVITY.NUMBER is
'Numéro de l''activité';

comment on column O_ACTIVITY.MILESTONE is
'Jalon';

comment on column O_ACTIVITY.ENGINE is
'Implémentation de l''activité';

comment on column O_ACTIVITY.PRO_ID is
'Processus';

create index O_ACTIVITY_PRO_ID_FK on O_ACTIVITY (PRO_ID asc);
-- ============================================================
--   Table : O_ACTIVITY_EXECUTION                                        
-- ============================================================
create table O_ACTIVITY_EXECUTION
(
    ACE_ID      	 NUMERIC     	not null,
    CREATION_TIME	 TIMESTAMP   	not null,
    BEGIN_TIME  	 TIMESTAMP   	,
    END_TIME    	 TIMESTAMP   	,
    ENGINE      	 VARCHAR(200)	,
    ACT_ID      	 NUMERIC     	,
    PRE_ID      	 NUMERIC     	,
    NOD_ID      	 NUMERIC     	,
    EST_CD      	 VARCHAR(20) 	,
);

comment on column O_ACTIVITY_EXECUTION.ACE_ID is
'Id de l''execution d''un processus';

comment on column O_ACTIVITY_EXECUTION.CREATION_TIME is
'Date de création';

comment on column O_ACTIVITY_EXECUTION.BEGIN_TIME is
'Date de début';

comment on column O_ACTIVITY_EXECUTION.END_TIME is
'Date de fin';

comment on column O_ACTIVITY_EXECUTION.ENGINE is
'Implémentation effective de l''execution';

comment on column O_ACTIVITY_EXECUTION.ACT_ID is
'Activity';

create index O_ACTIVITY_EXECUTION_ACT_ID_FK on O_ACTIVITY_EXECUTION (ACT_ID asc);
comment on column O_ACTIVITY_EXECUTION.PRE_ID is
'Processus';

create index O_ACTIVITY_EXECUTION_PRE_ID_FK on O_ACTIVITY_EXECUTION (PRE_ID asc);
comment on column O_ACTIVITY_EXECUTION.NOD_ID is
'Node';

create index O_ACTIVITY_EXECUTION_NOD_ID_FK on O_ACTIVITY_EXECUTION (NOD_ID asc);
comment on column O_ACTIVITY_EXECUTION.EST_CD is
'ExecutionState';

create index O_ACTIVITY_EXECUTION_EST_CD_FK on O_ACTIVITY_EXECUTION (EST_CD asc);
-- ============================================================
--   Table : O_ACTIVITY_LOG                                        
-- ============================================================
create table O_ACTIVITY_LOG
(
    ACL_ID      	 NUMERIC     	not null,
    LOG         	 TEXT        	,
    ACE_ID      	 NUMERIC     	,
);

comment on column O_ACTIVITY_LOG.ACL_ID is
'Id du log';

comment on column O_ACTIVITY_LOG.LOG is
'Contenu du log';

comment on column O_ACTIVITY_LOG.ACE_ID is
'ActivityExecution';

create index O_ACTIVITY_LOG_ACE_ID_FK on O_ACTIVITY_LOG (ACE_ID asc);
-- ============================================================
--   Table : O_ACTIVITY_WORKSPACE                                        
-- ============================================================
create table O_ACTIVITY_WORKSPACE
(
    ACW_ID      	 NUMERIC     	not null,
    IS_IN       	 BOOL        	not null,
    WORKSPACE   	 TEXT        	,
    ACE_ID      	 NUMERIC     	,
);

comment on column O_ACTIVITY_WORKSPACE.ACW_ID is
'Id de l''execution d''un processus';

comment on column O_ACTIVITY_WORKSPACE.IS_IN is
'Workspace in/out';

comment on column O_ACTIVITY_WORKSPACE.WORKSPACE is
'Contenu du workspace';

comment on column O_ACTIVITY_WORKSPACE.ACE_ID is
'ActivityExecution';

create index O_ACTIVITY_WORKSPACE_ACE_ID_FK on O_ACTIVITY_WORKSPACE (ACE_ID asc);
-- ============================================================
--   Table : O_EXECUTION_STATE                                        
-- ============================================================
create table O_EXECUTION_STATE
(
    EST_CD      	 VARCHAR(20) 	not null,
    LABEL       	 VARCHAR(100)	,
);

comment on column O_EXECUTION_STATE.EST_CD is
'Code';

comment on column O_EXECUTION_STATE.LABEL is
'Libellé';

-- ============================================================
--   Table : O_NODE                                        
-- ============================================================
create table O_NODE
(
    NOD_ID      	 NUMERIC     	not null,
    NAME        	 VARCHAR(100)	not null,
    HEARTBEAT   	 TIMESTAMP   	not null,
);

comment on column O_NODE.NOD_ID is
'Id du noeud';

comment on column O_NODE.NAME is
'Nom du noeud';

comment on column O_NODE.HEARTBEAT is
'Date de dernière activité';

-- ============================================================
--   Table : O_PLANIFICATION_STATE                                        
-- ============================================================
create table O_PLANIFICATION_STATE
(
    PST_CD      	 VARCHAR(20) 	not null,
    LABEL       	 VARCHAR(100)	,
);

comment on column O_PLANIFICATION_STATE.PST_CD is
'Code';

comment on column O_PLANIFICATION_STATE.LABEL is
'Libellé';

-- ============================================================
--   Table : O_PROCESS                                        
-- ============================================================
create table O_PROCESS
(
    PRO_ID      	 NUMERIC     	not null,
    NAME        	 VARCHAR(100)	,
    LABEL       	 VARCHAR(100)	,
    CRON_EXPRESSION	 VARCHAR(100)	,
    INITIAL_PARAMS	 TEXT        	,
    MULTIEXECUTION	 BOOL        	,
    ACTIVE      	 BOOL        	not null,
    RESCUE_PERIOD	 NUMERIC     	not null,
    TRT_CD      	 VARCHAR(20) 	,
    PRT_CD      	 VARCHAR(20) 	,
);

comment on column O_PROCESS.PRO_ID is
'Id de la definition du processus';

comment on column O_PROCESS.NAME is
'Nom du processus';

comment on column O_PROCESS.LABEL is
'Libellé du processus';

comment on column O_PROCESS.CRON_EXPRESSION is
'Expression récurrence du processus';

comment on column O_PROCESS.INITIAL_PARAMS is
'Paramètres initiaux sous forme de JSON';

comment on column O_PROCESS.MULTIEXECUTION is
'Accepte la multi-execution';

comment on column O_PROCESS.ACTIVE is
'Version active';

comment on column O_PROCESS.RESCUE_PERIOD is
'Temps de validité d''une planification';

comment on column O_PROCESS.TRT_CD is
'TriggerType';

create index O_PROCESS_TRT_CD_FK on O_PROCESS (TRT_CD asc);
comment on column O_PROCESS.PRT_CD is
'ProcessType';

create index O_PROCESS_PRT_CD_FK on O_PROCESS (PRT_CD asc);
-- ============================================================
--   Table : O_PROCESS_EXECUTION                                        
-- ============================================================
create table O_PROCESS_EXECUTION
(
    PRE_ID      	 NUMERIC     	not null,
    BEGIN_TIME  	 TIMESTAMP   	not null,
    END_TIME    	 TIMESTAMP   	,
    ENGINE      	 VARCHAR(200)	,
    PRO_ID      	 NUMERIC     	,
    EST_CD      	 VARCHAR(20) 	,
);

comment on column O_PROCESS_EXECUTION.PRE_ID is
'Id de l''execution d''un processus';

comment on column O_PROCESS_EXECUTION.BEGIN_TIME is
'Date de début';

comment on column O_PROCESS_EXECUTION.END_TIME is
'Date de fin';

comment on column O_PROCESS_EXECUTION.ENGINE is
'Implémentation effective de l''execution';

comment on column O_PROCESS_EXECUTION.PRO_ID is
'Processus';

create index O_PROCESS_EXECUTION_PRO_ID_FK on O_PROCESS_EXECUTION (PRO_ID asc);
comment on column O_PROCESS_EXECUTION.EST_CD is
'ExecutionState';

create index O_PROCESS_EXECUTION_EST_CD_FK on O_PROCESS_EXECUTION (EST_CD asc);
-- ============================================================
--   Table : O_PROCESS_PLANIFICATION                                        
-- ============================================================
create table O_PROCESS_PLANIFICATION
(
    PRP_ID      	 NUMERIC     	not null,
    EXPECTED_TIME	 TIMESTAMP   	,
    INITIAL_PARAMS	 TEXT        	,
    PRO_ID      	 NUMERIC     	,
    NOD_ID      	 NUMERIC     	,
    PST_CD      	 VARCHAR(20) 	,
);

comment on column O_PROCESS_PLANIFICATION.PRP_ID is
'Id Planification';

comment on column O_PROCESS_PLANIFICATION.EXPECTED_TIME is
'Date d''execution prévue';

comment on column O_PROCESS_PLANIFICATION.INITIAL_PARAMS is
'Paramètres initiaux sous forme de JSON';

comment on column O_PROCESS_PLANIFICATION.PRO_ID is
'Processus';

create index O_PROCESS_PLANIFICATION_PRO_ID_FK on O_PROCESS_PLANIFICATION (PRO_ID asc);
comment on column O_PROCESS_PLANIFICATION.NOD_ID is
'Node';

create index O_PROCESS_PLANIFICATION_NOD_ID_FK on O_PROCESS_PLANIFICATION (NOD_ID asc);
comment on column O_PROCESS_PLANIFICATION.PST_CD is
'PlanificationState';

create index O_PROCESS_PLANIFICATION_PST_CD_FK on O_PROCESS_PLANIFICATION (PST_CD asc);
-- ============================================================
--   Table : O_PROCESS_TYPE                                        
-- ============================================================
create table O_PROCESS_TYPE
(
    PRT_CD      	 VARCHAR(20) 	not null,
    LABEL       	 VARCHAR(100)	,
);

comment on column O_PROCESS_TYPE.PRT_CD is
'Code';

comment on column O_PROCESS_TYPE.LABEL is
'Libellé';

-- ============================================================
--   Table : TRIGGER_TYPE                                        
-- ============================================================
create table TRIGGER_TYPE
(
    TRT_CD      	 VARCHAR(20) 	not null,
    LABEL       	 VARCHAR(100)	,
);

comment on column TRIGGER_TYPE.TRT_CD is
'Code';

comment on column TRIGGER_TYPE.LABEL is
'Libellé';



alter table O_ACTIVITY_EXECUTION
	add constraint FK_ACE_ACT foreign key (ACT_ID)
	references O_ACTIVITY (ACT_ID);

alter table O_ACTIVITY_EXECUTION
	add constraint FK_ACE_EST foreign key (EST_CD)
	references O_EXECUTION_STATE (EST_CD);

alter table O_ACTIVITY_EXECUTION
	add constraint FK_ACE_NOD foreign key (NOD_ID)
	references O_NODE (NOD_ID);

alter table O_ACTIVITY_EXECUTION
	add constraint FK_ACE_PRE foreign key (PRE_ID)
	references O_PROCESS_EXECUTION (PRE_ID);

alter table O_ACTIVITY_LOG
	add constraint FK_ACL_ACE foreign key (ACE_ID)
	references O_ACTIVITY_EXECUTION (ACE_ID);

alter table O_ACTIVITY
	add constraint FK_ACT_PRO foreign key (PRO_ID)
	references O_PROCESS (PRO_ID);

alter table O_PROCESS_EXECUTION
	add constraint FK_PRE_EST foreign key (EST_CD)
	references O_EXECUTION_STATE (EST_CD);

alter table O_PROCESS_EXECUTION
	add constraint FK_PRE_PRO foreign key (PRO_ID)
	references O_PROCESS (PRO_ID);

alter table O_PROCESS
	add constraint FK_PRO_PRT foreign key (PRT_CD)
	references O_PROCESS_TYPE (PRT_CD);

alter table O_PROCESS
	add constraint FK_PRO_TRT foreign key (TRT_CD)
	references TRIGGER_TYPE (TRT_CD);

alter table O_PROCESS_PLANIFICATION
	add constraint FK_PRP_NOD foreign key (NOD_ID)
	references O_NODE (NOD_ID);

alter table O_PROCESS_PLANIFICATION
	add constraint FK_PRP_PRO foreign key (PRO_ID)
	references O_PROCESS (PRO_ID);

alter table O_PROCESS_PLANIFICATION
	add constraint FK_PRP_PST foreign key (PST_CD)
	references O_PLANIFICATION_STATE (PST_CD);

alter table O_ACTIVITY_WORKSPACE
	add constraint FK_TKW_TKE foreign key (ACE_ID)
	references O_ACTIVITY_EXECUTION (ACE_ID);

