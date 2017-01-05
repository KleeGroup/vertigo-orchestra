package io.vertigo.orchestra.process;

import io.vertigo.lang.Manager;
import io.vertigo.orchestra.process.execution.ProcessExecutor;
import io.vertigo.orchestra.process.log.ProcessLogger;
import io.vertigo.orchestra.process.report.ProcessReport;
import io.vertigo.orchestra.process.schedule.ProcessScheduler;

/**
 * Interface (interne) de gestion des executions des processus Orchestra.
 *
 * @author KleeGroup.
 * @version $Id$
 */
public interface ProcessManager extends Manager {
	/**
	 * Executes the processes (scheduled or manual)
	 * @return the executor
	 */
	ProcessExecutor getExecutor();

	/**
	 * Only accessible if a plugin has been registered
	 * @return
	 */
	ProcessReport getReport();

	/**
	 * Only accessible if a plugin has been registered
	 * @return
	 */
	ProcessLogger getLogger();

	/**
	 * @return the scheduler
	 */
	ProcessScheduler getScheduler();

}
