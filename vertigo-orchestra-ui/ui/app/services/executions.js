import fetch from 'focus-core/network/fetch';
import executionsUrl from '../config/server/executions';
import omit from 'lodash/object/omit';

export default {
    loadProcessSummary(id) {
        console.log(`[PROCESS] call loadProcessSummary(${id}) method`);
        return fetch(executionsUrl.loadSummary({urlData: {id}}), {isCORS: true});
    },
    loadSummaries() {
        console.log(`[PROCESS] call loadSummaries() method`);
        return fetch(executionsUrl.loadSummaries(), {isCORS: true}).then((data) => (
        {dataList: data, totalCount: data.length}
        ));
    }
}
