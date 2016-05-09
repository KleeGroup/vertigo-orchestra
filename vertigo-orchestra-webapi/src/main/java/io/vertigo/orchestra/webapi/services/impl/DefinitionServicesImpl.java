package io.vertigo.orchestra.webapi.services.impl;

import javax.inject.Inject;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.dynamo.transaction.Transactional;
import io.vertigo.lang.Assertion;
import io.vertigo.lang.Option;
import io.vertigo.orchestra.dao.definition.OActivityDAO;
import io.vertigo.orchestra.definition.ProcessDefinitionManager;
import io.vertigo.orchestra.domain.definition.OActivity;
import io.vertigo.orchestra.webapi.dao.uidefinitions.UidefinitionsPAO;
import io.vertigo.orchestra.webapi.domain.uidefinitions.OProcessUi;
import io.vertigo.orchestra.webapi.services.DefinitionServices;
import io.vertigo.util.StringUtil;

/**
 * Implementation of access to orchestra process definitions.
 *
 * @author mlaroche.
 * @version $Id$
 */
@Transactional
public class DefinitionServicesImpl implements DefinitionServices {

	@Inject
	private UidefinitionsPAO uiDefinitionsPAO;
	@Inject
	private OActivityDAO activityDAO;
	@Inject
	private ProcessDefinitionManager processDefinitionManager;

	/** {@inheritDoc} */
	@Override
	public OProcessUi getProcessDefinitionById(final Long proId) {
		Assertion.checkNotNull(proId);
		// ---
		return uiDefinitionsPAO.getProcessByProId(proId);
	}

	/** {@inheritDoc} */
	@Override
	public DtList<OProcessUi> searchProcess(final String search) {
		return uiDefinitionsPAO.searchProcessByLabel("%" + search + "%");
	}

	/** {@inheritDoc} */
	@Override
	public DtList<OActivity> getActivitiesByProId(final Long proId) {
		Assertion.checkNotNull(proId);
		// ---
		return activityDAO.getActivitiesByProId(proId);
	}

	/** {@inheritDoc} */
	@Override
	public void updateProcessProperties(final Long id, final String cronExpression, final boolean multiExecution, final Long rescuePerdiod, final boolean active) {
		final String processName = getProcessDefinitionById(id).getName();
		final Option<String> cronExpressionOption = StringUtil.isEmpty(cronExpression) ? Option.<String> none() : Option.<String> some(cronExpression);
		processDefinitionManager.updateProcessDefinitionProperties(processName, cronExpressionOption, multiExecution, rescuePerdiod, active);

	}

	/** {@inheritDoc} */
	@Override
	public void updateProcessInitialParams(final Long id, final String initialParams) {
		final String processName = getProcessDefinitionById(id).getName();
		final Option<String> initialParamsOption = StringUtil.isEmpty(initialParams) ? Option.<String> none() : Option.<String> some(initialParams);
		processDefinitionManager.updateProcessDefinitionInitialParams(processName, initialParamsOption);

	}

}
