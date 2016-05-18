package io.vertigo.orchestra.webapi.domain;

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
			/** Objet de données OActivityExecutionUi. */
			OActivityExecutionUi(io.vertigo.orchestra.webapi.domain.uiexecutions.OActivityExecutionUi.class),
			/** Objet de données OExecutionSummary. */
			OExecutionSummary(io.vertigo.orchestra.webapi.domain.summary.OExecutionSummary.class),
			/** Objet de données OProcessExecutionUi. */
			OProcessExecutionUi(io.vertigo.orchestra.webapi.domain.uiexecutions.OProcessExecutionUi.class),
			/** Objet de données OProcessUi. */
			OProcessUi(io.vertigo.orchestra.webapi.domain.uidefinitions.OProcessUi.class),
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
	 * Enumération des champs de OActivityExecutionUi.
	 */
	public enum OActivityExecutionUiFields implements DtFieldName {
		/** Propriété 'Id de l'activité'. */
		ACE_ID,
		/** Propriété 'Libellé'. */
		LABEL,
		/** Propriété 'Nom du processus'. */
		BEGIN_TIME,
		/** Propriété 'Nom du processus'. */
		END_TIME,
		/** Propriété 'Durée'. */
		EXECUTION_TIME,
		/** Propriété 'Statut'. */
		STATUS,
		/** Propriété 'Paramètres entrants'. */
		WORKSPACE_IN,
		/** Propriété 'Paramètres sortants'. */
		WORKSPACE_OUT,
		/** Propriété 'Fichier de log'. */
		HAS_LOG_FILE,
	}

	/**
	 * Enumération des champs de OExecutionSummary.
	 */
	public enum OExecutionSummaryFields implements DtFieldName {
		/** Propriété 'Id du processus'. */
		PRO_ID,
		/** Propriété 'Nom du processus'. */
		PROCESS_NAME,
		/** Propriété 'Libellé du processus'. */
		PROCESS_LABEL,
		/** Propriété 'Dernière exécution le'. */
		LAST_EXECUTION_TIME,
		/** Propriété 'Prochaine exécution le'. */
		NEXT_EXECUTION_TIME,
		/** Propriété 'Nombre en erreur'. */
		ERRORS_COUNT,
		/** Propriété 'Nombre non executés'. */
		MISFIRED_COUNT,
		/** Propriété 'Nombre en succès'. */
		SUCCESSFUL_COUNT,
		/** Propriété 'Nombre en cours'. */
		RUNNING_COUNT,
		/** Propriété 'Durée moyenne d'exécution'. */
		AVERAGE_EXECUTION_TIME,
		/** Propriété 'Santé du processus'. */
		HEALTH,
	}

	/**
	 * Enumération des champs de OProcessExecutionUi.
	 */
	public enum OProcessExecutionUiFields implements DtFieldName {
		/** Propriété 'Id de l'activité'. */
		PRE_ID,
		/** Propriété 'Nom du processus'. */
		BEGIN_TIME,
		/** Propriété 'Nom du processus'. */
		END_TIME,
		/** Propriété 'Durée'. */
		EXECUTION_TIME,
		/** Propriété 'Statut'. */
		STATUS,
		/** Propriété 'Pris en charge'. */
		CHECKED,
		/** Propriété 'Date de prise en charge'. */
		CHECKING_DATE,
		/** Propriété 'Commentaire'. */
		CHECKING_COMMENT,
		/** Propriété 'Fichier de log'. */
		HAS_LOG_FILE,
	}

	/**
	 * Enumération des champs de OProcessUi.
	 */
	public enum OProcessUiFields implements DtFieldName {
		/** Propriété 'Id du processus'. */
		PRO_ID,
		/** Propriété 'Nom du processus'. */
		NAME,
		/** Propriété 'Libellé du processus'. */
		LABEL,
		/** Propriété 'Expression récurrence du processus'. */
		CRON_EXPRESSION,
		/** Propriété 'Paramètres initiaux sous forme de JSON'. */
		INITIAL_PARAMS,
		/** Propriété 'Accepte la multi-execution'. */
		MULTIEXECUTION,
		/** Propriété 'Processus actif'. */
		ACTIVE,
		/** Propriété 'Temps de validité d'une planification'. */
		RESCUE_PERIOD,
		/** Propriété 'Métadonnées du processus'. */
		METADATAS,
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
