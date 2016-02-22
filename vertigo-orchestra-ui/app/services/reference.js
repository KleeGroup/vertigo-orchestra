let URL = require('../../config/server');
let fetch = Focus.network.fetch;

module.exports = {
    getScopes(id) {
        return fetch(URL.reference.getScopes({}));
    }
};
