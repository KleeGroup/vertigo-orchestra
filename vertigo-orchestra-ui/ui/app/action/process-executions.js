import executionsServices from '../services/executions';
import actionBuilder from 'focus-core/application/action-builder';
import listActionBuilder from 'focus-core/list/action-builder';
import summarylistStore from '../stores/summary-list';
import processExecutionsListStore from '../stores/process-executions-list';
import activityExecutionsListStore from '../stores/activity-executions-list';


export const caracteristicsActions = {
    load: actionBuilder({
        node: 'processExecution',
        service: executionsServices.loadProcessExecution,
        shouldDumpStoreOnActionCall: true,
        status: 'loaded'
    })
}

export const summaryAction = {
    load: actionBuilder({
        node: 'summary',
        service: executionsServices.loadProcessSummary,
        shouldDumpStoreOnActionCall: true,
        status: 'loaded'
    })
}

export const activityCaracteristicsActions = {
    load: actionBuilder({
        node: 'activityExecution',
        service: executionsServices.loadActivityExecution,
        shouldDumpStoreOnActionCall: true,
        status: 'loaded'
    })
}

const listActions = listActionBuilder({
    service: executionsServices.loadSummaries,
    identifier: 'summaryList',
    getListOptions: () => summarylistStore.getValue() } // Binding the store in the function call
);


export const listProcessExecutionsActions = (id, status) =>  listActionBuilder({
    service: executionsServices.loadProcessExecutions,
    identifier: 'processExecutionsList',
    getListOptions: () =>  { return { criteria: {id, status}, ...processExecutionsListStore.getValue()}} } // Binding the store in the function call
);

export const loadActivityExecutionsActions = (id) =>  listActionBuilder({
    service: executionsServices.loadActivityExecutions,
    identifier: 'activityExecutionsList',
    getListOptions: () =>  {
        return {criteria: {id},...activityExecutionsListStore.getValue()};
    } // Binding the store in the function call
});

export const loadSummaryList =listActions.load;
export const loadProcessExecutionsList = (id) => {return listProcessExecutionsActions(id).load};
