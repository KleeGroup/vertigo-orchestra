import definitionServices from '../services/definitions';
import actionBuilder from 'focus-core/application/action-builder';

export const caracteristicsActions = {
    load: actionBuilder({
        node: 'processCaracteristics',
        service: definitionServices.loadProcessDefinition,
        shouldDumpStoreOnActionCall: true,
        status: 'loaded'
    })
}
