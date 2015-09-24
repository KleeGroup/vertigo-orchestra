insert into o_execution_state(est_cd, label) values ('WAITING', 'Waiting');
insert into o_execution_state(est_cd, label) values ('RESERVED', 'Reserved');
insert into o_execution_state(est_cd, label) values ('DONE', 'Done');
insert into o_execution_state(est_cd, label) values ('RUNNING', 'Running');
insert into o_execution_state(est_cd, label) values ('ERROR', 'Error');
insert into o_execution_state(est_cd, label) values ('CANCELED', 'Canceled');

insert into o_planification_state(pst_cd, label) values ('WAITING', 'Waiting');
insert into o_planification_state(pst_cd, label) values ('RESERVED', 'Reserved');
insert into o_planification_state(pst_cd, label) values ('TRIGGERED', 'Triggered');
insert into o_planification_state(pst_cd, label) values ('MISFIRED', 'Misfired');
insert into o_planification_state(pst_cd, label) values ('CANCELED', 'Canceled');

insert into o_process_type(prt_cd, label) values ('DUMB', 'Dumb');

insert into trigger_type(trt_cd, label) values ('RECURRENT', 'Recurrent');
insert into trigger_type(trt_cd, label) values ('MANUAL', 'Manuel');

insert into o_process(pro_id,name, delay, trt_cd, prt_cd) values (nextval('SEQ_O_PROCESS'),'TEST DUMB PROCESS', 100,'DUMB', 'DUMB');
insert into o_task(tsk_id, name, milestone, engine, pro_id) values (nextval('SEQ_O_TASK'), 'TEST_TASK', false, 'io.vertigo.orchestra.services.execution.engine.DumbOTaskEngine', currval('SEQ_O_PROCESS'));

insert into o_process(pro_id,name, delay, trt_cd, prt_cd) values (nextval('SEQ_O_PROCESS'),'TEST DUMB RECURRENT PROCESS', 100,'RECURRENT', 'DUMB');
insert into o_task(tsk_id, name, milestone, engine, pro_id) values (nextval('SEQ_O_TASK'), 'TEST_TASK', false, 'io.vertigo.orchestra.services.execution.engine.DumbOTaskEngine', currval('SEQ_O_PROCESS'));