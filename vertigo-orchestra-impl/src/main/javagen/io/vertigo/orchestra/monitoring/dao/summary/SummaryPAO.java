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
package io.vertigo.orchestra.monitoring.dao.summary;

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
 * SummaryPAO
 */
public final class SummaryPAO implements StoreServices {
	private final TaskManager taskManager;

	/**
	 * Constructeur.
	 * @param taskManager Manager des Task
	 */
	@Inject
	public SummaryPAO(final TaskManager taskManager) {
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
	 * Execute la tache TK_GET_EXECUTION_SUMMARIES_BY_DATE.
	 * @param dateMin java.util.Date 
	 * @param dateMax java.util.Date 
	 * @param status String 
	 * @return io.vertigo.dynamo.domain.model.DtList<io.vertigo.orchestra.monitoring.domain.summary.OExecutionSummary> dtcExecutionSummary
	*/
	public io.vertigo.dynamo.domain.model.DtList<io.vertigo.orchestra.monitoring.domain.summary.OExecutionSummary> getExecutionSummariesByDate(final java.util.Date dateMin, final java.util.Date dateMax, final String status) {
		final Task task = createTaskBuilder("TK_GET_EXECUTION_SUMMARIES_BY_DATE")
				.addValue("DATE_MIN", dateMin)
				.addValue("DATE_MAX", dateMax)
				.addValue("STATUS", status)
				.build();
		return getTaskManager()
				.execute(task)
				.getResult();
	}

	/**
	 * Execute la tache TK_GET_EXECUTION_SUMMARY_BY_DATE_AND_NAME.
	 * @param dateMin java.util.Date 
	 * @param dateMax java.util.Date 
	 * @param name String 
	 * @return io.vertigo.orchestra.monitoring.domain.summary.OExecutionSummary dtExecutionSummary
	*/
	public io.vertigo.orchestra.monitoring.domain.summary.OExecutionSummary getExecutionSummaryByDateAndName(final java.util.Date dateMin, final java.util.Date dateMax, final String name) {
		final Task task = createTaskBuilder("TK_GET_EXECUTION_SUMMARY_BY_DATE_AND_NAME")
				.addValue("DATE_MIN", dateMin)
				.addValue("DATE_MAX", dateMax)
				.addValue("NAME", name)
				.build();
		return getTaskManager()
				.execute(task)
				.getResult();
	}

	private TaskManager getTaskManager() {
		return taskManager;
	}
}
