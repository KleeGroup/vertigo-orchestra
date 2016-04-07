// libraries
import React from 'react';
import {translate} from 'focus-core/translation';

//web components
import {mixin as formPreset} from 'focus-components/common/form';

//stores
import movieStore from '../../../stores/movie';

//custom components
import Poster from '../components/poster';

export default React.createClass({
    displayName: 'MovieDetailHeaderExpanded',
    mixins: [formPreset],
    definitionPath: 'movie',
    stores: [{store: movieStore, properties: ['movieCaracteristics', 'movieSynopsis']}],

    /** @inheritDoc */
    renderContent() {
        const {title, poster, trailerHRef} = this.state;
        return (
            <div data-demo='header-content-expanded'>
                <Poster poster={poster} title={title} hasZoom={true} />
                <div data-demo='header-content-expanded__infos'>
                    <div className="key-concept">{translate('view.movie.keyConcept.name')}</div>
                    <h3>{this.textFor('title')}</h3>
                    <h5>{this.textFor('movieType')}</h5>
                    <h6>{this.textFor('productionYear')}</h6>
                    <div>{this.textFor('shortSynopsis')}</div>
                </div>
            </div>
        );
    }
})
