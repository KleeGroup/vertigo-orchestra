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
package io.vertigo.orchestra.dao.definition;

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
import io.vertigo.orchestra.domain.definition.OProcess;

/**
 * DAO : Accès à un object (DTO, DTC). 
 * OProcessDAO
 */
public final class OProcessDAO extends DAO<OProcess, java.lang.Long> implements StoreServices {

	/**
	 * Contructeur.
	 * @param storeManager Manager de persistance
	 * @param taskManager Manager de Task
	 */
	@Inject
	public OProcessDAO(final StoreManager storeManager, final TaskManager taskManager) {
		super(OProcess.class, storeManager, taskManager);
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
	 * Execute la tache TK_GET_ACTIVE_PROCESS_BY_NAME.
	 * @param name String 
	 * @return Option de io.vertigo.orchestra.domain.definition.OProcess dtProcess
	*/
	public Optional<io.vertigo.orchestra.domain.definition.OProcess> getActiveProcessByName(final String name) {
		final Task task = createTaskBuilder("TK_GET_ACTIVE_PROCESS_BY_NAME")
				.addValue("NAME", name)
				.build();
		return Optional.ofNullable((io.vertigo.orchestra.domain.definition.OProcess) getTaskManager()
				.execute(task)
				.getResult());
	}

	/**
	 * Execute la tache TK_GET_ALL_ACTIVE_PROCESSES.
	 * @return io.vertigo.dynamo.domain.model.DtList<io.vertigo.orchestra.domain.definition.OProcess> dtcProcesses
	*/
	public io.vertigo.dynamo.domain.model.DtList<io.vertigo.orchestra.domain.definition.OProcess> getAllActiveProcesses() {
		final Task task = createTaskBuilder("TK_GET_ALL_ACTIVE_PROCESSES")
				.build();
		return getTaskManager()
				.execute(task)
				.getResult();
	}

}
