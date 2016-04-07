import React, {Component} from 'react';
import MovieCardList from '../../movie/components/movie-card-list';
import {translate} from 'focus-core/translation';

const DateRanking = ({dateRanking}) => (
    <div>
        <h1>{translate('view.movie.rankings.latest.title')}</h1>
        <MovieCardList movies={dateRanking} />
    </div>
);

export default DateRanking;
