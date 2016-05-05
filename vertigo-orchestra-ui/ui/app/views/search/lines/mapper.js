import DefaultLine from './line';
import ProcessLine from './process';
import PersonLine from './person';

//TODO : à revoir avec Nicolas et Pierre
export default function lineComponentMapper(groupKey, list) {
    switch (groupKey) {
        //case 'Movies': return MovieLine;
        case 'processus': return ProcessLine;
        //case 'Persons': return PersonLine;
        case 'person': return PersonLine;
        default: return DefaultLine;
    }
}
