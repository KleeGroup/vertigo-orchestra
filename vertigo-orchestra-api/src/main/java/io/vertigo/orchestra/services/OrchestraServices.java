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
