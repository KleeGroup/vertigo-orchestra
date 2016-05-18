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
        token: "Token d'identification",
        actId: "Activity",
        preId: "Processus",
        nodId: "Node",
        estCd: "ExecutionState"
    },
    oActivityLog: {
        aclId: "Id du log",
        log: "Contenu du log",
        logFile: "Fichier de log",
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
        activeVersion: "Version active",
        active: "Processus actif",
        rescuePeriod: "Temps de validité d'une planification",
        metadatas: "Meta-données du processus",
        needUpdate: "Doit être mise à jour lors du démarrage",
        trtCd: "TriggerType",
        prtCd: "ProcessType"
    },
    oProcessExecution: {
        preId: "Id de l'execution d'un processus",
        beginTime: "Date de début",
        endTime: "Date de fin",
        engine: "Implémentation effective de l'execution",
        checked: "Pris en charge",
        checkingDate: "Date de prise en charge",
        checkingComment: "Commentaire",
        proId: "Processus",
        estCd: "ExecutionState",
        usrId: "User"
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
    oUser: {
        usrId: "Id",
        firstName: "Nom",
        lastName: "Prénom",
        email: "Email",
        password: "Mot de passe",
        mailAlert: "Alerté en cas d'erreur",
        active: "Compte Actif"
    },
    triggerType: {
        trtCd: "Code",
        label: "Libellé"
    }
};
