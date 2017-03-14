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

import io.vertigo.app.Home;
import io.vertigo.lang.Assertion;
import io.vertigo.dynamo.task.TaskManager;
import io.vertigo.dynamo.task.metamodel.TaskDefinition;
import io.vertigo.dynamo.task.model.Task;
import io.vertigo.dynamo.task.model.TaskBuilder;
import io.vertigo.dynamo.store.StoreServices;

/**
 * PAO : Accès aux objects du package. 
 * ExecutionPAO
 */
public final class ExecutionPAO implements StoreServices {
	private final TaskManager taskManager;

	/**
	 * Constructeur.
	 * @param taskManager Manager des Task
	 */
	@Inject
	public ExecutionPAO(final TaskManager taskManager) {
		Assertion.checkNotNull(taskManager);
		//-----
		this.taskManager = taskManager;
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
	 * Execute la tache TK_HANDLE_PROCESSES_OF_DEAD_NODES.
	 * @param maxDate java.util.Date 
	*/
	public void handleProcessesOfDeadNodes(final java.util.Date maxDate) {
		final Task task = createTaskBuilder("TK_HANDLE_PROCESSES_OF_DEAD_NODES")
				.addValue("MAX_DATE", maxDate)
				.build();
		getTaskManager().execute(task);
	}

	/**
	 * Execute la tache TK_RESERVE_ACTIVITIES_TO_LAUNCH.
	 * @param nodId Long 
	 * @param maxNumber Integer 
	*/
	public void reserveActivitiesToLaunch(final Long nodId, final Integer maxNumber) {
		final Task task = createTaskBuilder("TK_RESERVE_ACTIVITIES_TO_LAUNCH")
				.addValue("NOD_ID", nodId)
				.addValue("MAX_NUMBER", maxNumber)
				.build();
		getTaskManager().execute(task);
	}

	/**
	 * Execute la tache TK_UPDATE_PROCESS_EXECUTION_TREATMENT.
	 * @param preId Long 
	 * @param checked Boolean 
	 * @param checkingDate java.util.Date 
	 * @param checkingComment String 
	*/
	public void updateProcessExecutionTreatment(final Long preId, final Boolean checked, final java.util.Date checkingDate, final String checkingComment) {
		final Task task = createTaskBuilder("TK_UPDATE_PROCESS_EXECUTION_TREATMENT")
				.addValue("PRE_ID", preId)
				.addValue("CHECKED", checked)
				.addValue("CHECKING_DATE", checkingDate)
				.addValue("CHECKING_COMMENT", checkingComment)
				.build();
		getTaskManager().execute(task);
	}

	private TaskManager getTaskManager() {
		return taskManager;
	}
}
