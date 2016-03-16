import FocusCore from 'focus-core';

FocusCore.definition.entity.container.setEntityConfiguration(require('../config/entity-definition'));

//Display domaines utilisés
const entityDef = require('../config/entity-definition');
const arr = [];
for (const node in entityDef) {
    for (const sub in entityDef[node]) {
        arr.push(entityDef[node][sub].domain);
    }
}
const appDomains = _.uniq(arr);
const domains = Object.keys(require('../config/domain'));

console.info('########################## DOMAINS ##############################');
console.info('Entity definitions domains: ', appDomains);
console.info('Domains with a definition', domains);
console.warn('Missing domain\'s definition', _.difference(appDomains, _.intersection(appDomains, domains)));
console.warn('Useless domain\'s definition', _.difference(domains, _.intersection(appDomains, domains)));
console.info('####################################################################');
