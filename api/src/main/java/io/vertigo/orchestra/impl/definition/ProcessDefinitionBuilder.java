package io.vertigo.orchestra.impl.definition;

import io.vertigo.dynamo.domain.model.DtList;
import io.vertigo.lang.Assertion;
import io.vertigo.lang.Builder;
import io.vertigo.orchestra.definition.ProcessDefinition;
import io.vertigo.orchestra.domain.definition.OProcess;
import io.vertigo.orchestra.domain.definition.OTask;

/**
 * TODO : Description de la classe.
 *
 * @author mlaroche.
 * @version $Id$
 */
public class ProcessDefinitionBuilder implements Builder<ProcessDefinition> {

	private final OProcess process;
	private final DtList<OTask> tasks = new DtList<>(OTask.class);

	/**
	 * Constructeur.
	 */
	public ProcessDefinitionBuilder(final String processName) {
		process = new OProcess();

		process.setName(processName);

	}

	/**
	 * Processus a déclanchement automatique.
	 * @param delay
	 * @return this
	 */
	public ProcessDefinitionBuilder withRecurrence() {
		Assertion.checkNotNull(process);
		Assertion.checkState(process.getTrtCd() == null, "Le type de déclanchement est déjà renseigné");
		// ---
		process.setTrtCd("RECURRENT");
		return this;
	}

	/**
	 * Processus a déclanchement manuel.
	 * @param delay
	 * @return this
	 */
	public ProcessDefinitionBuilder withManual() {
		Assertion.checkNotNull(process);
		Assertion.checkState(process.getTrtCd() == null, "Le type de déclanchement est déjà renseigné");
		// ---
		process.setTrtCd("MANUAL");
		return this;
	}

	/**
	 * Ajoute un delai entre deux executions d'une tache récurrente.
	 * @param delay delay en secondes
	 * @return this
	 */
	public ProcessDefinitionBuilder withDelay(final long delay) {
		Assertion.checkNotNull(process);
		Assertion.checkState(process.getTrtCd() == "RECURRENT", "Le type de déclanchement doit être recurrent");
		// ---
		process.setDelay(delay);
		return this;
	}

	/**
	 * Ajoute un delai entre deux executions d'une tache récurrente.
	 * @param delay delay en secondes
	 * @return this
	 */
	public ProcessDefinitionBuilder withTask(final String taskName, final String engine, final boolean milestone) {
		Assertion.checkNotNull(tasks);
		// ---
		final OTask task = new OTask();
		task.setName(taskName);
		task.setEngine(engine);
		task.setMilestone(milestone);
		tasks.add(task);
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public ProcessDefinition build() {
		return new ProcessDefinition(process, tasks);
	}

}
