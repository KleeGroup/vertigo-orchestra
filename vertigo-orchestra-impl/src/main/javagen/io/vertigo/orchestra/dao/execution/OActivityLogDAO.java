/**
 * vertigo - simple java starter
 *
 * Copyright (C) 2013-2017, KleeGroup, direction.technique@kleegroup.com (http://www.kleegroup.com)
 * KleeGroup, Centre d'affaire la Boursidiere - BP 159 - 92357 Le Plessis Robinson Cedex - France
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.vertigo.orchestra.dao.execution;

import javax.inject.Inject;
import java.util.Optional;
import io.vertigo.app.Home;
import io.vertigo.dynamo.task.metamodel.TaskDefinition;
import io.vertigo.dynamo.task.model.Task;
import io.vertigo.dynamo.task.model.TaskBuilder;
import io.vertigo.dynamo.impl.store.util.DAO;
import io.vertigo.dynamo.store.StoreManager;
import io.vertigo.dynamo.store.StoreServices;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.orchestra.domain.execution.OActivityLog;

/**
 * DAO : Accès à un object (DTO, DTC). 
 * OActivityLogDAO
 */
public final class OActivityLogDAO extends DAO<OActivityLog, java.lang.Long> implements StoreServices {

	/**
	 * Contructeur.
	 * @param storeManager Manager de persistance
	 * @param taskManager Manager de Task
	 */
	@Inject
	public OActivityLogDAO(final StoreManager storeManager, final TaskManager taskManager) {
		super(OActivityLog.class, storeManager, taskManager);
	}


	/**
	 * Creates a taskBuilder.
	 * @param name  the name of the task
	 * @return the builder 
	 */
	private static TaskBuilder createTaskBuilder(final String name) {
		final TaskDefinition taskDefinition = Home.getApp().getDefinitionSpace().resolve(name, TaskDefinition.class);
		return new TaskBuilder(taskDefinition);
	}

	/**
	 * Execute la tache TK_GET_ACTIVITY_LOG_BY_ACE_ID.
	 * @param aceId Long 
	 * @return Option de io.vertigo.orchestra.domain.execution.OActivityLog dtcOActivityLog
	*/
	public Optional<io.vertigo.orchestra.domain.execution.OActivityLog> getActivityLogByAceId(final Long aceId) {
		final Task task = createTaskBuilder("TK_GET_ACTIVITY_LOG_BY_ACE_ID")
				.addValue("ACE_ID", aceId)
				.build();
		return Optional.ofNullable((io.vertigo.orchestra.domain.execution.OActivityLog) getTaskManager()
				.execute(task)
				.getResult());
	}

	/**
	 * Execute la tache TK_GET_LOG_BY_PRE_ID.
	 * @param preId Long 
	 * @return Option de io.vertigo.orchestra.domain.execution.OActivityLog dtActivityLog
	*/
	public Optional<io.vertigo.orchestra.domain.execution.OActivityLog> getLogByPreId(final Long preId) {
		final Task task = createTaskBuilder("TK_GET_LOG_BY_PRE_ID")
				.addValue("PRE_ID", preId)
				.build();
		return Optional.ofNullable((io.vertigo.orchestra.domain.execution.OActivityLog) getTaskManager()
				.execute(task)
				.getResult());
	}

}
