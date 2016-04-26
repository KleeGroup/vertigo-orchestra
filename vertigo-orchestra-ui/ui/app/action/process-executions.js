import executionsServices from '../services/executions';
import actionBuilder from 'focus-core/application/action-builder';
import listActionBuilder from 'focus-core/list/action-builder';
import summarylistStore from '../stores/summary-list';

const listActions = listActionBuilder({
    service: executionsServices.loadSummaries,
    identifier: 'summaryList',
    getListOptions: () => summarylistStore.getValue() } // Binding the store in the function call
);

export const loadSummaryList = listActions.load;
