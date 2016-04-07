import {CoreStore} from 'focus-core/store';

/**
* Store dealing with subjects about movies.
* @type {focus}
*/
const countryStore = new CoreStore({
    definition: {
        informations: 'informations'
    }
});

export default countryStore;
