import DefaultLine from './line';
import MovieLine from './movie';
import PersonLine from './person';

//TODO : à revoir avec Nicolas et Pierre
export default function lineComponentMapper(groupKey, list) {
    switch (groupKey) {
        //case 'Movies': return MovieLine;
        case 'movie': return MovieLine;
        //case 'Persons': return PersonLine;
        case 'person': return PersonLine;
        default: return DefaultLine;
    }
}
