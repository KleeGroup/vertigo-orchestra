import builder from 'focus-core/util/url/builder';
import {apiRoot} from './index';

const definitionsRoot = `${apiRoot}definitions/`;

export default {
    load: builder(definitionsRoot + '${id}', 'GET')
};
