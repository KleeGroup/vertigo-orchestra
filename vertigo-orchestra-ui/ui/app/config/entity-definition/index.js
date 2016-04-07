/**
 * These metadata are generated automatically.
 * @type {Object}
 */
module.exports = {
        oActivity: {
        actId: {
            domain: "DO_IDENTIFIANT",
            required: true
        },
        name: {
            domain: "DO_LIBELLE",
            required: false
        },
        number: {
            domain: "DO_NOMBRE",
            required: false
        },
        milestone: {
            domain: "DO_BOOLEEN",
            required: false
        },
        engine: {
            domain: "DO_CLASSE",
            required: false
        },
        proId: {
            domain: "DO_IDENTIFIANT",
            required: false
        }
    },
    oActivityExecution: {
        aceId: {
            domain: "DO_IDENTIFIANT",
            required: true
        },
        beginTime: {
            domain: "DO_TIMESTAMP",
            required: true
        },
        endTime: {
            domain: "DO_TIMESTAMP",
            required: false
        },
        engine: {
            domain: "DO_CLASSE",
            required: false
        },
        nodeName: {
            domain: "DO_LIBELLE",
            required: false
        },
        actId: {
            domain: "DO_IDENTIFIANT",
            required: false
        },
        preId: {
            domain: "DO_IDENTIFIANT",
            required: false
        },
        estCd: {
            domain: "DO_CODE_IDENTIFIANT",
            required: false
        }
    },
    oActivityLog: {
        aclId: {
            domain: "DO_IDENTIFIANT",
            required: true
        },
        log: {
            domain: "DO_TEXT",
            required: false
        },
        aceId: {
            domain: "DO_IDENTIFIANT",
            required: false
        }
    },
    oActivityWorkspace: {
        acwId: {
            domain: "DO_IDENTIFIANT",
            required: true
        },
        isIn: {
            domain: "DO_BOOLEEN",
            required: true
        },
        workspace: {
            domain: "DO_JSON_TEXT",
            required: false
        },
        aceId: {
            domain: "DO_IDENTIFIANT",
            required: false
        }
    },
    oExecutionState: {
        estCd: {
            domain: "DO_CODE_IDENTIFIANT",
            required: true
        },
        label: {
            domain: "DO_LIBELLE",
            required: false
        }
    },
    oPlanificationState: {
        pstCd: {
            domain: "DO_CODE_IDENTIFIANT",
            required: true
        },
        label: {
            domain: "DO_LIBELLE",
            required: false
        }
    },
    oProcess: {
        proId: {
            domain: "DO_IDENTIFIANT",
            required: true
        },
        name: {
            domain: "DO_LIBELLE",
            required: false
        },
        cronExpression: {
            domain: "DO_LIBELLE",
            required: false
        },
        initialParams: {
            domain: "DO_JSON_TEXT",
            required: false
        },
        multiexecution: {
            domain: "DO_BOOLEEN",
            required: false
        },
        active: {
            domain: "DO_BOOLEEN",
            required: true
        },
        trtCd: {
            domain: "DO_CODE_IDENTIFIANT",
            required: false
        },
        prtCd: {
            domain: "DO_CODE_IDENTIFIANT",
            required: false
        }
    },
    oProcessExecution: {
        preId: {
            domain: "DO_IDENTIFIANT",
            required: true
        },
        beginTime: {
            domain: "DO_TIMESTAMP",
            required: true
        },
        endTime: {
            domain: "DO_TIMESTAMP",
            required: false
        },
        engine: {
            domain: "DO_CLASSE",
            required: false
        },
        proId: {
            domain: "DO_IDENTIFIANT",
            required: false
        },
        estCd: {
            domain: "DO_CODE_IDENTIFIANT",
            required: false
        }
    },
    oProcessPlanification: {
        prpId: {
            domain: "DO_IDENTIFIANT",
            required: true
        },
        expectedTime: {
            domain: "DO_TIMESTAMP",
            required: false
        },
        state: {
            domain: "DO_CODE_IDENTIFIANT",
            required: false
        },
        initialParams: {
            domain: "DO_JSON_TEXT",
            required: false
        },
        nodeName: {
            domain: "DO_LIBELLE",
            required: false
        },
        proId: {
            domain: "DO_IDENTIFIANT",
            required: false
        },
        pstCd: {
            domain: "DO_CODE_IDENTIFIANT",
            required: false
        }
    },
    oProcessType: {
        prtCd: {
            domain: "DO_CODE_IDENTIFIANT",
            required: true
        },
        label: {
            domain: "DO_LIBELLE",
            required: false
        }
    },
    triggerType: {
        trtCd: {
            domain: "DO_CODE_IDENTIFIANT",
            required: true
        },
        label: {
            domain: "DO_LIBELLE",
            required: false
        }
    }
};
