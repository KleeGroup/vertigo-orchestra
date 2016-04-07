//librairies
import React, {PropTypes} from 'react';

// web components
import Panel from 'focus-components/components/panel';
import {storeBehaviour} from 'focus-components/common/mixin';
import MovieCardList from '../../movie/components/movie-card-list';

//stores & actions
import personStore from '../../../stores/person';
import {moviesLinksActions} from '../../../action/person';

export default React.createClass({
    displayName: 'PersonMovie',
    propTypes: {
        id: PropTypes.number.isRequired
    },
    mixins: [storeBehaviour],

    /** @inheritDoc */
    getInitialState() {
        return {
            personMovieLinks: personStore.getPersonMovieLinks() || []
        }
    },

    /** @inheritDoc */
    componentWillMount() {
        const {id} = this.props;
        moviesLinksActions.load(id);
    },
    stores: [{store: personStore, properties: ['personMovieLinks']}],

    /** @inheritDoc */
    render() {
        const {personMovieLinks} = this.state;
        return (
            <Panel title='view.person.detail.movies'>
                <MovieCardList movies={personMovieLinks} />
            </Panel>
        );
    }
});
