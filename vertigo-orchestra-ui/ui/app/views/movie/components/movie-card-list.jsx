//libraries
import React, {PropTypes} from 'react';

//web components
import MovieCard from './movie-card';
import {component as Modal} from 'focus-components/application/popin';
import MoviePreview from '../../movie/preview';

export default React.createClass({
    displayName: 'MovieCardList',
    propTypes: {
        movies: PropTypes.array
    },
    getDefaultProps() {
        return {
            movies: []
        }
    },
    /** @inheritDoc */
    getInitialState() {
        return {
            movieCodePreview: null
        }
    },

    render() {
        const {movies} = this.props;
        const {movieCodePreview} = this.state;
        return (
            <div data-demo='concept-card-list'>
                {movies &&
                    movies.map(movie => {
                        const {code} = movie;
                        const key = `movie-card-${code}`;
                        return (
                            <MovieCard key={key} movie={movie} onClickPreview={movieId => this.setState({movieCodePreview: movieId})} />
                        );
                    })
                }
                {movieCodePreview &&
                    <Modal open={true} onPopinClose={() => this.setState({movieCodePreview: null})} type='from-right'>
                        <MoviePreview id={movieCodePreview}/>
                    </Modal>
                }
            </div>
        );
    }
});
