import builder from 'focus-core/util/url/builder';
import {apiRoot} from './index';

const executionsRoot = `${apiRoot}executions/`;

export default {
    loadSummary: builder(executionsRoot+'summary/' + '${id}', 'GET'),
    loadSummaries: builder(executionsRoot+'summaries', 'GET'),
    loadProcessExecutions: builder(executionsRoot + '${id}?status=${status}&limit=${top}&offset=${skip}', 'GET'),
    loadActivityExecutions: builder(executionsRoot+'processExecution/'+'${id}'+'/activities', 'GET'),
    loadProcessExecution: builder(executionsRoot+'processExecution/'+'${id}', 'GET'),
    loadActivityExecution: builder(executionsRoot+'activityExecution/'+'${id}', 'GET'),
};
