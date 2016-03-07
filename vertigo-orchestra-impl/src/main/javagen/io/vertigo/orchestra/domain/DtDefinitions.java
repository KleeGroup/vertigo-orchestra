package io.vertigo.orchestra.domain;

import java.util.Arrays;
import java.util.Iterator;
import io.vertigo.dynamo.domain.metamodel.DtFieldName;

/**
 * Attention cette classe est générée automatiquement !
 */
public final class DtDefinitions implements Iterable<Class<?>> {
	
	/**
	 * Enumération des DtDefinitions.
	 */
	public enum Definitions {
			/** Objet de données OExecutionState. */
			OExecutionState(io.vertigo.orchestra.domain.referential.OExecutionState.class),
			/** Objet de données OPlanificationState. */
			OPlanificationState(io.vertigo.orchestra.domain.referential.OPlanificationState.class),
			/** Objet de données OProcess. */
			OProcess(io.vertigo.orchestra.domain.definition.OProcess.class),
			/** Objet de données OProcessExecution. */
			OProcessExecution(io.vertigo.orchestra.domain.execution.OProcessExecution.class),
			/** Objet de données OProcessPlanification. */
			OProcessPlanification(io.vertigo.orchestra.domain.planification.OProcessPlanification.class),
			/** Objet de données OProcessType. */
			OProcessType(io.vertigo.orchestra.domain.referential.OProcessType.class),
			/** Objet de données OTask. */
			OTask(io.vertigo.orchestra.domain.definition.OTask.class),
			/** Objet de données OTaskExecution. */
			OTaskExecution(io.vertigo.orchestra.domain.execution.OTaskExecution.class),
			/** Objet de données OTaskLog. */
			OTaskLog(io.vertigo.orchestra.domain.execution.OTaskLog.class),
			/** Objet de données OTaskWorkspace. */
			OTaskWorkspace(io.vertigo.orchestra.domain.execution.OTaskWorkspace.class),
			/** Objet de données TriggerType. */
			TriggerType(io.vertigo.orchestra.domain.referential.TriggerType.class),
		;
		
		private final Class<?> clazz;
		private Definitions(final Class<?> clazz) {
			this.clazz = clazz;
		}
		
		/** 
		  * Classe associée.
		  * @return Class d'implémentation de l'objet 
		  */
		public Class<?> getDtClass() {
			return clazz;
		}
    }

	/**
	 * Enumération des champs de OExecutionState.
	 */
	public enum OExecutionStateFields implements DtFieldName {
		/** Propriété 'Code'. */
		EST_CD,
		/** Propriété 'Libellé'. */
		LABEL,
	}

	/**
	 * Enumération des champs de OPlanificationState.
	 */
	public enum OPlanificationStateFields implements DtFieldName {
		/** Propriété 'Code'. */
		PST_CD,
		/** Propriété 'Libellé'. */
		LABEL,
	}

	/**
	 * Enumération des champs de OProcess.
	 */
	public enum OProcessFields implements DtFieldName {
		/** Propriété 'Id de la definition du processus'. */
		PRO_ID,
		/** Propriété 'Nom du processus'. */
		NAME,
		/** Propriété 'Expression récurrence du processus'. */
		CRON_EXPRESSION,
		/** Propriété 'Paramètres initiaux sous forme de JSON'. */
		INITIAL_PARAMS,
		/** Propriété 'Accepte la multi-execution'. */
		MULTIEXECUTION,
		/** Propriété 'Version active'. */
		ACTIVE,
		/** Propriété 'TriggerType'. */
		TRT_CD,
		/** Propriété 'ProcessType'. */
		PRT_CD,
	}

	/**
	 * Enumération des champs de OProcessExecution.
	 */
	public enum OProcessExecutionFields implements DtFieldName {
		/** Propriété 'Id de l'execution d'un processus'. */
		PRE_ID,
		/** Propriété 'Date de début'. */
		BEGIN_TIME,
		/** Propriété 'Date de fin'. */
		END_TIME,
		/** Propriété 'Implémentation effective de l'execution'. */
		ENGINE,
		/** Propriété 'Processus'. */
		PRO_ID,
		/** Propriété 'ExecutionState'. */
		EST_CD,
	}

	/**
	 * Enumération des champs de OProcessPlanification.
	 */
	public enum OProcessPlanificationFields implements DtFieldName {
		/** Propriété 'Id Planification'. */
		PRP_ID,
		/** Propriété 'Date d'execution prévue'. */
		EXPECTED_TIME,
		/** Propriété 'Etat de la planification'. */
		STATE,
		/** Propriété 'Paramètres initiaux sous forme de JSON'. */
		INITIAL_PARAMS,
		/** Propriété 'Nom du noeud'. */
		NODE_NAME,
		/** Propriété 'Processus'. */
		PRO_ID,
		/** Propriété 'PlanificationState'. */
		PST_CD,
	}

	/**
	 * Enumération des champs de OProcessType.
	 */
	public enum OProcessTypeFields implements DtFieldName {
		/** Propriété 'Code'. */
		PRT_CD,
		/** Propriété 'Libellé'. */
		LABEL,
	}

	/**
	 * Enumération des champs de OTask.
	 */
	public enum OTaskFields implements DtFieldName {
		/** Propriété 'Id Tache'. */
		TSK_ID,
		/** Propriété 'Nom de la tâche'. */
		NAME,
		/** Propriété 'Numéro de la tâche'. */
		NUMBER,
		/** Propriété 'Jalon'. */
		MILESTONE,
		/** Propriété 'Implémentation de la tâche'. */
		ENGINE,
		/** Propriété 'Processus'. */
		PRO_ID,
	}

	/**
	 * Enumération des champs de OTaskExecution.
	 */
	public enum OTaskExecutionFields implements DtFieldName {
		/** Propriété 'Id de l'execution d'un processus'. */
		TKE_ID,
		/** Propriété 'Date de début'. */
		BEGIN_TIME,
		/** Propriété 'Date de fin'. */
		END_TIME,
		/** Propriété 'Implémentation effective de l'execution'. */
		ENGINE,
		/** Propriété 'Nom du noeud'. */
		NODE_NAME,
		/** Propriété 'Tache'. */
		TSK_ID,
		/** Propriété 'Processus'. */
		PRE_ID,
		/** Propriété 'ExecutionState'. */
		EST_CD,
	}

	/**
	 * Enumération des champs de OTaskLog.
	 */
	public enum OTaskLogFields implements DtFieldName {
		/** Propriété 'Id du log'. */
		TKL_ID,
		/** Propriété 'Contenu du log'. */
		LOG,
		/** Propriété 'TaskExecution'. */
		TKE_ID,
	}

	/**
	 * Enumération des champs de OTaskWorkspace.
	 */
	public enum OTaskWorkspaceFields implements DtFieldName {
		/** Propriété 'Id de l'execution d'un processus'. */
		TKW_ID,
		/** Propriété 'Workspace in/out'. */
		IS_IN,
		/** Propriété 'Contenu du workspace'. */
		WORKSPACE,
		/** Propriété 'TaskExecution'. */
		TKE_ID,
	}

	/**
	 * Enumération des champs de TriggerType.
	 */
	public enum TriggerTypeFields implements DtFieldName {
		/** Propriété 'Code'. */
		TRT_CD,
		/** Propriété 'Libellé'. */
		LABEL,
	}

	    
    /** {@inheritDoc} */
    @Override
    public Iterator<Class<?>> iterator() {
        return new Iterator<Class<?>>() {
            private Iterator<Definitions> it = Arrays.asList(Definitions.values()).iterator();

            /** {@inheritDoc} */
            @Override
            public boolean hasNext() {
				return it.hasNext();
            }

            /** {@inheritDoc} */
            @Override
            public Class<?> next() {
            	return it.next().getDtClass();
            }

            /** {@inheritDoc} */
            @Override
            public void remove() {
            	//unsupported
            }
        };
    }                      
}
