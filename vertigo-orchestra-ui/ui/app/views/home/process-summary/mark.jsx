import React, {Component} from 'react';
import MovieCardList from '../../movie/components/movie-card-list';
import {translate} from 'focus-core/translation';

const MarkRanking = ({markRanking}) => (
    <div>
        <h1>{translate('view.movie.rankings.best.title')}</h1>
        <MovieCardList movies={markRanking} />
    </div>
);

export default MarkRanking;
