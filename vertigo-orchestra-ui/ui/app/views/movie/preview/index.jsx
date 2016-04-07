//libraries
import React, {PropTypes} from 'react';
import {translate} from 'focus-core/translation';
import history from 'focus-core/history';

//web components
import {component as Button} from 'focus-components/common/button/action';
import {storeBehaviour} from 'focus-components/common/mixin';
import builtInComponents from 'focus-components/common/mixin/built-in-components';

// actions & stores
import movieStore from '../../../stores/movie'
import {caracteristicsActions} from '../../../action/movie'

//custom components
import Poster from '../components/poster';
import MovieCaracteristics from'../detail/caracteristics'
import MovieSynopsis from'../detail/synospis'

export default React.createClass({
    displayName: 'MoviePreview',
    propTypes: {
        id: PropTypes.number.isRequired
    },
    mixins: [builtInComponents, storeBehaviour],
    definitionPath: 'movie',
    stores: [{store: movieStore, properties: ['movieCaracteristics', 'movieSynopsis']}],

    /** @inheritDoc */
    getInitialState(){
        return {};
    },

    /** @inheritDoc */
    componentDidMount() {
        const {id} = this.props;
        caracteristicsActions.load(id);
    },

    /** @inheritDoc */
    render() {
        const {poster,title} = this.state;
        const {id} = this.props;
        return (
            <div data-demo='preview'>
                <div data-demo='preview-header'>
                    <Poster poster={poster} title={title} hasZoom={true}/>
                    <div>
                        <h3>{this.textFor('title')}</h3>
                        <h5>{this.textFor('movieType')}</h5>
                        <h5>{this.textFor('productionYear')}</h5>
                        <div>{this.textFor('synopsis')}</div>
                        <br/>
                        <Button label='view.movie.action.consult.sheet' handleOnClick={() => {history.navigate(`movies/${id}`, true); window.scrollTo(0, 0);}} />
                    </div>
                </div>
                <div data-demo='preview-content'>
                    <MovieCaracteristics id={id} hasEdit={false}/>
                </div>
            </div>
        ) ;
    }

});
