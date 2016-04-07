import fetch from 'focus-core/network/fetch';
import movieUrl from '../config/server/movies';
import omit from 'lodash/object/omit';

export default {
    loadMovie(id) {
        console.log(`[MOVIE] call loadMovie(${id}) method`);
        return fetch(movieUrl.load({urlData: {id}}), {isCORS: true});
    },
    loadMovieCasting(id) {
        console.log(`[MOVIE] call loadMovieCasting(${id}) method`);
        return fetch(movieUrl.load({urlData: {id}}), {isCORS: true}).then(({actors, camera, directors, producers, writers}) => {
            return {actors, camera, directors, producers, writers};
        });
    },
    updateMovieCaracteristics(data) {
        const movieId = data.id;
        omit(data, ['id', 'actors', 'camera', 'directors', 'producers', 'writers']);
        console.log(`[MOVIE] call updateMovieCaracteristics ${movieId} method. data=${JSON.stringify(data)}`);
        return fetch(movieUrl.update({urlData: {id: movieId}, data}), {isCORS: true});
    },
    updateMovieSynopsis(data) {
        const movieId = data.id;
        omit(data, ['id', 'actors', 'camera', 'directors', 'producers', 'writers']);
        console.log(`[MOVIE] call updateMovieSynopsis method. data=${JSON.stringify(data)}`);
        return fetch(movieUrl.update({urlData: {id: movieId}, data}), {isCORS: true});
    }
}
