import {CoreStore} from 'focus-core/store';

/**
* Store dealing with subjects about movies.
* @type {focus}
*/
const movieStore = new CoreStore({
    definition: {
        movieCaracteristics: 'movieCaracteristics',
        movieCasting: 'movieCasting',
        movieSynopsis: 'movieSynopsis',
        movieTrailer: 'movieTrailer'
    }
});

export default movieStore;
