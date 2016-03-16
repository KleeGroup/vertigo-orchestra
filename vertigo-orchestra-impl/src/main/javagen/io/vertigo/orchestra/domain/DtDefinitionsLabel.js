/**
 * Attention ce fichier est généré automatiquement !
 * DtDefinitionsLabel
 */

module.exports = {
    oActivity: {
        actId: "Id Activité",
        name: "Nom de l'activité",
        number: "Numéro de l'activité",
        milestone: "Jalon",
        engine: "Implémentation de l'activité",
        proId: "Processus"
    },
    oActivityExecution: {
        aceId: "Id de l'execution d'un processus",
        beginTime: "Date de début",
        endTime: "Date de fin",
        engine: "Implémentation effective de l'execution",
        nodeName: "Nom du noeud",
        actId: "Activity",
        preId: "Processus",
        estCd: "ExecutionState"
    },
    oActivityLog: {
        aclId: "Id du log",
        log: "Contenu du log",
        aceId: "ActivityExecution"
    },
    oActivityWorkspace: {
        acwId: "Id de l'execution d'un processus",
        isIn: "Workspace in/out",
        workspace: "Contenu du workspace",
        aceId: "ActivityExecution"
    },
    oExecutionState: {
        estCd: "Code",
        label: "Libellé"
    },
    oPlanificationState: {
        pstCd: "Code",
        label: "Libellé"
    },
    oProcess: {
        proId: "Id de la definition du processus",
        name: "Nom du processus",
        cronExpression: "Expression récurrence du processus",
        initialParams: "Paramètres initiaux sous forme de JSON",
        multiexecution: "Accepte la multi-execution",
        active: "Version active",
        trtCd: "TriggerType",
        prtCd: "ProcessType"
    },
    oProcessExecution: {
        preId: "Id de l'execution d'un processus",
        beginTime: "Date de début",
        endTime: "Date de fin",
        engine: "Implémentation effective de l'execution",
        proId: "Processus",
        estCd: "ExecutionState"
    },
    oProcessPlanification: {
        prpId: "Id Planification",
        expectedTime: "Date d'execution prévue",
        state: "Etat de la planification",
        initialParams: "Paramètres initiaux sous forme de JSON",
        nodeName: "Nom du noeud",
        proId: "Processus",
        pstCd: "PlanificationState"
    },
    oProcessType: {
        prtCd: "Code",
        label: "Libellé"
    },
    triggerType: {
        trtCd: "Code",
        label: "Libellé"
    }
};
