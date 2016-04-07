import fetch from 'focus-core/network/fetch';
import definitionsUrl from '../config/server/definitions';
import omit from 'lodash/object/omit';

export default {
    loadProcessDefinition(id) {
        console.log(`[PROCESS] call loadProcessDefinition(${id}) method`);
        return fetch(definitionsUrl.load({urlData: {id}}), {isCORS: true});
    }
}
