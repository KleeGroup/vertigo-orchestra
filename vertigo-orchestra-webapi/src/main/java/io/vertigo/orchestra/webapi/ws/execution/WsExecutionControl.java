package io.vertigo.orchestra.webapi.ws.execution;

import javax.inject.Inject;

import io.vertigo.lang.Assertion;
import io.vertigo.lang.Option;
import io.vertigo.orchestra.OrchestraManager;
import io.vertigo.orchestra.execution.ExecutionState;
import io.vertigo.vega.webservice.WebServices;
import io.vertigo.vega.webservice.stereotype.AnonymousAccessAllowed;
import io.vertigo.vega.webservice.stereotype.InnerBodyParam;
import io.vertigo.vega.webservice.stereotype.POST;
import io.vertigo.vega.webservice.stereotype.PathPrefix;

/**
 * WebService API for managing Executions
 * @author mlaroche.
 * @version $Id$
 */
@PathPrefix("/executionsControl")
public class WsExecutionControl implements WebServices {

	@Inject
	private OrchestraManager orchestraManager;

	/**
	 * Get the processExecution by Id
	 */
	@POST("/endExecution")
	@AnonymousAccessAllowed
	public void endExecution(@InnerBodyParam("activityExecutionId") final Long activityExecutionId, @InnerBodyParam("token") final String token, @InnerBodyParam("state") final String state) {
		Assertion.checkNotNull(activityExecutionId);
		Assertion.checkNotNull(token);
		Assertion.checkNotNull(state);
		// ---
		final ExecutionState executionState;
		switch (state) {
			case "ERROR":
				executionState = ExecutionState.ERROR;
				break;
			case "SUCCESS":
				executionState = ExecutionState.DONE;
				break;
			default:
				throw new RuntimeException("Unknown execution state : " + state);
		}
		orchestraManager.endPendingActivityExecution(activityExecutionId, token, executionState);
	}

	/**
	 * Get the processExecution by Id
	 */
	@POST("/execute")
	@AnonymousAccessAllowed
	public void endExecution(@InnerBodyParam("processName") final String processName, @InnerBodyParam("initialParams") final String initialParams) {
		Assertion.checkNotNull(processName);
		// ---
		final Option<String> initialParamsOption = Option.<String> option(initialParams);
		orchestraManager.scheduleNow(processName, initialParamsOption);
	}

}
