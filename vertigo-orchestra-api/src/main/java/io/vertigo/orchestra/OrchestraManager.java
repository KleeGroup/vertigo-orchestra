package io.vertigo.orchestra;

import java.util.Date;
import java.util.List;

import io.vertigo.lang.Manager;
import io.vertigo.lang.Option;
import io.vertigo.orchestra.definition.Process;

/**
 * Orchestra high-level services.
 *
 * @author KleeGroup.
 * @version $Id$
 */
public interface OrchestraManager extends Manager {

	void createDefinition(Process processDefinition);

	Process getProcessDefinition(String processName);

	List<Process> getAllProcesses();

	void scheduleNow(String processName, Option<String> initialParams);

	void scheduleAt(String processName, Date expectedTime, Option<String> initialParams);
}
