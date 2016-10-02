package io.vertigo.orchestra.impl.scheduler;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

import io.vertigo.lang.Plugin;
import io.vertigo.orchestra.definition.ProcessDefinition;
import io.vertigo.orchestra.definition.ProcessType;

/**
 * Plugin de gestion de la planification.
 *
 * @author mlaroche.
 * @version $Id$
 */
public interface ProcessSchedulerPlugin extends Plugin {

	void scheduleAt(final ProcessDefinition processDefinition, final Date planifiedTime, final Optional<String> initialParamsOption);

	Map<ProcessDefinition, String> getProcessToExecute();

	ProcessType getHandledProcessType();

}
