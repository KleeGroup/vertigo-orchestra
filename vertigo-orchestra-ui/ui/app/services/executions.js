import fetch from 'focus-core/network/fetch';
import executionsUrl from '../config/server/executions';
import omit from 'lodash/object/omit';

export default {
    loadProcessDefinition(id) {
        console.log(`[PROCESS] call loadProcessSummary(${id}) method`);
        return fetch(executionsUrl.loadSummary({urlData: {id}}), {isCORS: true});
    }
}
