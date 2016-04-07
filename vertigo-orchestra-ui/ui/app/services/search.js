import fetch from 'focus-core/network/fetch';

import commonUrl from '../config/server/common';
import moviesUrl from '../config/server/movies';
import personsUrl from '../config/server/persons';

import searchParser from './helpers/old-search-parser';

const ENABLE_NEW_SEARCH_API = false;

export default {
    _legacyfyServerResult(serverData) {
        if (ENABLE_NEW_SEARCH_API) {
            if (serverData.groups) serverData.groups = serverData.groups.map(group => ({[group.code]: group.list}));
            serverData.facets = serverData.facets.map(facet => ({[facet.code]: facet.entries.map(entry => ({[entry.code]: entry.value}))}));
        }
        return serverData;
    },

    /**
     * Target search service call.
     * (This should the target : search service should be written this way.)
     *
     * @param  {object} config search call configuration.
     * @param  {string} scope  scope search
     * @return {object}        search response
     */
    _search(config, scope) {
        switch (scope) {
            case 'movie':
                console.log(`[SEARCH MOVIE] config: ${JSON.stringify(config)}`);
                return fetch(moviesUrl.search(config))
                .then(this._legacyfyServerResult);
            case 'person':
                console.log(`[SEARCH PERSON] config: ${JSON.stringify(config)}`);
                return fetch(personsUrl.search(config))
                .then(this._legacyfyServerResult);
            default:
                console.log(`[SEARCH ALL] config: ${JSON.stringify(config)}`);
                return fetch(commonUrl.search(config))
                .then(this._legacyfyServerResult);
        }
    },

    /**
    * Search with scope.
    * @param  {Object} AdvancedSearch config that launches the call of this service
    * @return {Promise}
    */
    scoped(config) {
        const {criteria} = config.data;
        const {scope} = criteria;
        const serverConfig = searchParser.transformConfig(config);
        return this._search(serverConfig, scope);
    },
    /**
    * Search without scope.
    * @param  {Object} AdvancedSearch config that launches the call of this service
    * @return {Promise}
    */
    unscoped(config) {
        const serverConfig = searchParser.transformConfig(config, false);
        return this._search(serverConfig);
    }
};
