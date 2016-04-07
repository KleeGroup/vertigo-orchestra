import movieServices from '../services/movie';
import actionBuilder from 'focus-core/application/action-builder';

export const castingActions = {
    loadPeople: actionBuilder({
        node: 'movieCasting',
        service: movieServices.loadMovieCasting,
        shouldDumpStoreOnActionCall: true,
        status: 'loaded'
    })
}

export const caracteristicsActions = {
    load: actionBuilder({
        node: 'movieCaracteristics',
        service: movieServices.loadMovie,
        shouldDumpStoreOnActionCall: true,
        status: 'loaded'
    }),
    save: actionBuilder({
        node: 'movieCaracteristics',
        service: movieServices.updateMovieCaracteristics,
        shouldDumpStoreOnActionCall: false,
        status: 'saved'
    })
}

export const synopsisActions = {
    load: actionBuilder({
        node: 'movieSynopsis',
        service: movieServices.loadMovie,
        shouldDumpStoreOnActionCall: true,
        status: 'loaded'
    }),
    save: actionBuilder({
        node: 'movieSynopsis',
        service: movieServices.updateMovieSynopsis,
        shouldDumpStoreOnActionCall: false,
        status: 'saved'
    })
}

export const trailerActions = {
    load: actionBuilder({
        node: 'movieTrailer',
        service: movieServices.loadMovie,
        shouldDumpStoreOnActionCall: true,
        status: 'loaded'
    })
}
