import builder from 'focus-core/util/url/builder';
import {apiRoot} from './index';

const movieRoot = `${apiRoot}masterdata/`;

export default {
    loadGenders: builder(movieRoot + 'genders', 'GET'),
};
