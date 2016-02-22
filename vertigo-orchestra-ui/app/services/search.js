let URL = require('../../config/server');
let fetch = Focus.network.fetch;

module.exports = {
    unscoped(ajaxConfig) {
        return fetch(URL.search.searchByScope(ajaxConfig));
    },
    scoped(ajaxConfig) {
        return fetch(URL.search.searchByScope(ajaxConfig));
    }
};
