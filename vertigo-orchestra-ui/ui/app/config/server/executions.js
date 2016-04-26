import builder from 'focus-core/util/url/builder';
import {apiRoot} from './index';

const executionsRoot = `${apiRoot}executions/`;

export default {
    loadSummary: builder(executionsRoot+'summary/' + '${id}', 'GET'),
    loadSummaries: builder(executionsRoot+'summaries', 'GET'),
};
