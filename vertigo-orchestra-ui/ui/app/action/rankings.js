import rankingsService from '../services/rankings';
import actionBuilder from 'focus-core/application/action-builder';

export const loadDateRanking = actionBuilder({
    node: 'dateRanking',
    service: rankingsService.loadDateRanking,
    shouldDumpStoreOnActionCall: true,
    status: 'loaded'
});

export const loadMarkRanking = actionBuilder({
    node: 'markRanking',
    service: rankingsService.loadMarkRanking,
    shouldDumpStoreOnActionCall: true,
    status: 'loaded'
});
