import {CoreStore} from 'focus-core/store';

const rankingsStore = new CoreStore({
    definition: {
        dateRanking: 'dateRanking',
        markRanking: 'markRanking'
    }
});

export default rankingsStore;
