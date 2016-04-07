import fetch from 'focus-core/network/fetch';
import masterdataUrl from '../config/server/masterdatas';

export default {
    loadGenders() {
        console.log(`[MASTERDATA] call loadGenders() method`);
        return fetch(masterdataUrl.loadGenders(), {isCORS: true});
    }
}
