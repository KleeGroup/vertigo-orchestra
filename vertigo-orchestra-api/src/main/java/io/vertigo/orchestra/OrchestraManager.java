package io.vertigo.orchestra;

import java.util.Date;
import java.util.List;

import io.vertigo.lang.Manager;
import io.vertigo.lang.Option;
import io.vertigo.orchestra.definition.ProcessDefinition;

/**
 * Orchestra high-level services.
 *
 * @author KleeGroup.
 * @version $Id$
 */
public interface OrchestraManager extends Manager {

	void createDefinition(ProcessDefinition processDefinition);

	ProcessDefinition getProcessDefinition(String processName);

	boolean processDefinitionExist(String processName);

	List<ProcessDefinition> getAllProcesses();

	void scheduleNow(String processName, Option<String> initialParams);

	void scheduleAt(String processName, Date expectedTime, Option<String> initialParams);
}