import executionsServices from '../services/ex';
import actionBuilder from 'focus-core/application/action-builder';

export const caracteristicsActions = {
    loadSummary: actionBuilder({
        node: 'processSummary',
        service: executionsServices.loadSummary,
        shouldDumpStoreOnActionCall: true,
        status: 'loaded'
    })
}
