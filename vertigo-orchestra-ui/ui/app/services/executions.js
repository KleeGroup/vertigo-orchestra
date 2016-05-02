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
    },
    loadProcessExecutions({urlData,data}) {
        console.log(`[PROCESS] call loadProcessExecutions() method with parameters :`);
        return fetch(executionsUrl.loadProcessExecutions({urlData: {id:data.criteria.id}}), {isCORS: true}).then((filteredData) => (
        {dataList: filteredData, totalCount: filteredData.length}
        ));
    },
    loadActivityExecutions({urlData,data}) {
        console.log(`[PROCESS] call loadActivityExecutions() method with parameters :`);
        return fetch(executionsUrl.loadActivityExecutions({urlData: {id:data.criteria.id}}), {isCORS: true}).then((filteredData) => (
        {dataList: filteredData, totalCount: filteredData.length}
        ));
    },
    loadProcessExecution(id) {
        console.log(`[PROCESS] call loadProcessExecution(${id}) method`);
        return fetch(executionsUrl.loadProcessExecution({urlData: {id}}), {isCORS: true});
    },
    loadActivityExecution(id) {
        console.log(`[PROCESS] call loadActivityExecution(${id}) method`);
        return fetch(executionsUrl.loadActivityExecution({urlData: {id}}), {isCORS: true});
    }
}
