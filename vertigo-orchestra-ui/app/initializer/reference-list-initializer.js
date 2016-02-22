//Path to the reference service.
let referenceService = require('../services/reference');
let reference = Focus.reference;

module.exports = {
    initialize() {
        reference.config.set({scopes: referenceService.getScopes});
    }
};
