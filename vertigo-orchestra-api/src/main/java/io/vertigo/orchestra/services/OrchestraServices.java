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
package io.vertigo.orchestra.services;

import io.vertigo.lang.Component;
import io.vertigo.orchestra.services.execution.ProcessExecutor;
import io.vertigo.orchestra.services.log.ProcessLogger;
import io.vertigo.orchestra.services.report.ProcessReport;
import io.vertigo.orchestra.services.schedule.ProcessScheduler;

/**
 * Interface (interne) de gestion des executions des processus Orchestra.
 *
 * @author KleeGroup.
 * @version $Id$
 */
public interface OrchestraServices extends Component {
	/**
	 * Executes the processes (scheduled or manual)
	 * @return the executor
	 */
	ProcessExecutor getExecutor();

	/**
	 * Only accessible if a plugin has been registered
	 * @return the report
	 */
	ProcessReport getReport();

	/**
	 * Only accessible if a plugin has been registered
	 * @return the processLogger
	 */
	ProcessLogger getLogger();

	/**
	 * @return the scheduler
	 */
	ProcessScheduler getScheduler();

}
