import actionBuilder from 'focus-core/application/action-builder';
import listActionBuilder from 'focus-core/list/action-builder'
import {loadCountryListByCriteria, loadCountryById, saveCountry} from '../services/country';
import countryListStore from '../stores/country-list';
const listActions = listActionBuilder({
    service: loadCountryListByCriteria,
    identifier: 'countryList',
    getListOptions: () => countryListStore.getValue() } // Binding the store in the function call
);

export const loadCountryList = listActions.load;
export const updateCountyListProperties = listActions.updateProperties;

export const countryActions = {
    load: actionBuilder({
        node: 'informations',
        service: loadCountryById,
        shouldDumpStoreOnActionCall: true,
        status: 'loaded'
    }),
    save: actionBuilder({
        node: 'informations',
        service: saveCountry,
        status: 'saved'
    })

}
