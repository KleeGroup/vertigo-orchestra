/**
 * Attention ce fichier est généré automatiquement !
 * DtDefinitionsLabel
 */

module.exports = {
    oActivity: {
        actId: "Id Activité",
        name: "Nom de l'activité",
        label: "Libellé de l'activité",
        number: "Numéro de l'activité",
        milestone: "Jalon",
        engine: "Implémentation de l'activité",
        proId: "Processus"
    },
    oActivityExecution: {
        aceId: "Id de l'execution d'un processus",
        creationTime: "Date de création",
        beginTime: "Date de début",
        endTime: "Date de fin",
        engine: "Implémentation effective de l'execution",
        actId: "Activity",
        preId: "Processus",
        nodId: "Node",
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
    oNode: {
        nodId: "Id du noeud",
        name: "Nom du noeud",
        heartbeat: "Date de dernière activité"
    },
    oPlanificationState: {
        pstCd: "Code",
        label: "Libellé"
    },
    oProcess: {
        proId: "Id de la definition du processus",
        name: "Nom du processus",
        label: "Libellé du processus",
        cronExpression: "Expression récurrence du processus",
        initialParams: "Paramètres initiaux sous forme de JSON",
        multiexecution: "Accepte la multi-execution",
        active: "Version active",
        rescuePeriod: "Temps de validité d'une planification",
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
        initialParams: "Paramètres initiaux sous forme de JSON",
        proId: "Processus",
        nodId: "Node",
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
