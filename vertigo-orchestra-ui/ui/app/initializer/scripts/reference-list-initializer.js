import {config} from 'focus-core/reference';
import masterdataServices from '../../services/masterdata';

// load here all your reference lists
export default () => {
    console.info('|--- REFERENCES');
    config.set({genders: masterdataServices.loadGenders});
    config.set({
        scopes: () => {
            return Promise.resolve(
                //here call your webservice to get scope references
                [
                    {code: 'process', label: 'search.scope.process'}
                ]
            ).then(scopes => {
                //here define application icons
                scopes.map(_applyAdditionalScopeProperties);
                return scopes  ;
            });
        }
    });
}

function _applyAdditionalScopeProperties(scope) {
    switch (scope.code) {
        case 'process':
            scope.icon = 'all_inclusive';
            break;
        default:
            scope.icon = 'mood_bad'
            break;
    }
}
