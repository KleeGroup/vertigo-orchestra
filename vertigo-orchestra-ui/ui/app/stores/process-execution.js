import {CoreStore} from 'focus-core/store';

/**
* Store dealing with subjects about movies.
* @type {focus}
*/
const processExecutionStore = new CoreStore({
    definition: {
        processExecution: 'processExecution'
    }
});

export default processExecutionStore;
