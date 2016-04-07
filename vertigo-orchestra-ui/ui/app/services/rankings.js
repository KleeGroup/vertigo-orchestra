import fetch from 'focus-core/network/fetch';
import rankingUrls from '../config/server/rankings';

export default {
    loadDateRanking() {
        return fetch(rankingUrls.dateRanking({}), {isCORS: true});
    },
    loadMarkRanking() {
        return fetch(rankingUrls.markRanking({}), {isCORS: true});
    }
}
