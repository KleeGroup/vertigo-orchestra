//librairies
import React, {PropTypes} from 'react';
import {translate} from 'focus-core/translation';
import reduce from 'lodash/collection/reduce';

// web components
import Panel from 'focus-components/components/panel';
import {storeBehaviour} from 'focus-components/common/mixin';
import PersonCardList from '../../person/components/person-card-list';
import {component as Button} from 'focus-components/common/button/action';

//stores & actions
import movieStore from '../../../stores/movie';
import {castingActions} from '../../../action/movie';


export default React.createClass({
    displayName: 'MovieCasting',
    propTypes: {
        id: PropTypes.number.isRequired
    },
    mixins: [storeBehaviour],
    stores: [{store: movieStore, properties: ['movieCasting']}],

    getInitialState() {
        return {
            filter: null
        }
    },

    /** @inheritDoc */
    componentWillMount() {
        const {id} = this.props;
        castingActions.loadPeople(id);
    },

    _getCurrentFilter() {
        const {filter} = this.state;
        if(filter === null) {
            const tabs = this._getTabs();
            return tabs.length > 0 ? tabs[0] : 'actors';
        }
        return filter;
    },

    _getPeople() {
        const filter = this._getCurrentFilter();
        const people = this._getPeopleByName(filter);
        return people ? people : [];
    },

    _getPeopleByName(filter) {
        const {actors, camera, directors, producers, writers} = this.state;
        switch (filter) {
            case 'actors':
                return actors;
            case 'camera':
                return camera;
            case 'directors':
                return directors;
            case 'producers':
                return producers;
            case 'writers':
                return writers;
            default:
                return [];
        }
    },

    _setFilter(value) {
        this.setState({
            filter: value
        });
    },

    _getActionLabel(filter) {
        const people = this._getPeopleByName(filter);
        const size = people ? people.length : 0;
        return`${translate(`view.movie.action.filter.${filter}`)} (${size})`;
    },

    _isActive(value) {
        const filter = this._getCurrentFilter();
        return filter === value;
    },

    _getTabs() {
        const tabs = reduce(['actors','camera','directors','producers','writers'], (tabs, peopleType) => {
            const people = this._getPeopleByName(peopleType);
            if(people) tabs.push(peopleType);
            return tabs;
        }, []);
        return tabs;
    },

    /** @inheritDoc */
    render() {
        const {filter} = this.state;
        const list = this._getPeople();
        const tabs = this._getTabs();
        return (
            <Panel title='view.movie.detail.casting'>
                <div className='filters-bar'>
                    {tabs.map(peopleType =>
                        <Button key={`btn-filter-${peopleType}`} shape={null} label={this._getActionLabel(peopleType)} handleOnClick={() => this._setFilter(peopleType)} data-active={this._isActive(peopleType)} />
                    )}
                </div>
                <PersonCardList persons={list} />
            </Panel>
        );
    }
});
