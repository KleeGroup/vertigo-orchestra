const root = '.';
let url = Focus.util.url.builder;

module.exports = {
    getScopes: url(root + '/scopes', 'GET')
};
