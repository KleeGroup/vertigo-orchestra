package io.vertigo.orchestra.webapi.ws.execution;

import java.util.Optional;

import javax.inject.Inject;

import io.vertigo.lang.Assertion;
import io.vertigo.lang.MessageText;
import io.vertigo.lang.VUserException;
import io.vertigo.orchestra.OrchestraManager;
import io.vertigo.orchestra.execution.ExecutionState;
import io.vertigo.orchestra.webapi.domain.uidefinitions.OProcessUi;
import io.vertigo.orchestra.webapi.services.DefinitionServices;
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
	@Inject
	private DefinitionServices definitionServices;

	/**
	 * Termine une exécution mise en attente.
	 * @param activityExecutionId l'id de l'activité en attente
	 * @param token le token de sécurité
	 * @param state l'etat final de l'activité
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
				throw new VUserException(new MessageText("Unknown execution state : {0} ", null, state));
		}
		orchestraManager.endPendingActivityExecution(activityExecutionId, token, executionState);
	}

	/**
	 * Lance l'execution d'un processus.
	 * @param processName le nom du processus à lancer
	 * @param initialParams des éventuels paramètres supplémentaire
	 */
	@POST("/execute")
	@AnonymousAccessAllowed
	public void executeNow(@InnerBodyParam("processName") final String processName, @InnerBodyParam("initialParams") final Optional<String> initialParams) {
		Assertion.checkNotNull(processName);
		// ---
		orchestraManager.scheduleNow(processName, initialParams);
	}

	/**
	 * Lance l'execution d'un processus avec son id.
	 * @param proId l'id du processus à lancer
	 */
	@POST("/executeNowWithId")
	@AnonymousAccessAllowed
	public void executeNowWithId(@InnerBodyParam("proId") final Long proId) {
		Assertion.checkNotNull(proId);
		// ---
		final OProcessUi processUi = definitionServices.getProcessDefinitionById(proId);
		orchestraManager.scheduleNow(processUi.getName(), Optional.<String> empty());
	}

}
