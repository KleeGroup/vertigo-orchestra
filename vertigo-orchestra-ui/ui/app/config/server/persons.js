import builder from 'focus-core/util/url/builder';
import {apiRoot} from './index';

const personRoot = `${apiRoot}persons/`;

export default {
    create: builder(personRoot, 'POST'),
    load: builder(personRoot + '${id}', 'GET'),
    search: builder(personRoot + 'search?listState.skip=${skip}&listState.sortDesc=${sortDesc}&listState.top=${top}', 'POST'),
    update: builder(personRoot + '${id}', 'PUT')
};
