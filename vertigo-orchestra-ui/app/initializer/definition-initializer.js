/*global Focus, _*/
Focus.definition.entity.container.setEntityConfiguration(require('../config/entity-definition'));

//Display domaines utilisés
let entityDef = require('../config/entity-definition');
let arr = [];
for (let node in entityDef) {
    for (let sub in entityDef[node]) {
        arr.push(entityDef[node][sub].domain);
    }
}
let appDomains = _.uniq(arr);
let domains = Object.keys(require('../config/domain'));

console.info('########################## DOMAINS ##############################');
console.info('Entity definitions domains: ', appDomains);
console.info('Domains with a definition',domains);
console.warn('Missing domain\'s definition', _.difference(appDomains, _.intersection(appDomains,domains)));
console.warn('Useless domain\'s definition', _.difference(domains, _.intersection(appDomains,domains)));
console.info('####################################################################');
