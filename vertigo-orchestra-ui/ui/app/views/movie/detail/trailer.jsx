//librairies
import React, {PropTypes} from 'react';

// web components
import Panel from 'focus-components/components/panel';
import {mixin as formPreset} from 'focus-components/common/form';

//stores & actions
import movieStore from '../../../stores/movie';
import {trailerActions} from '../../../action/movie';

//custom components
import Trailer from '../components/trailer';

export default React.createClass({
    displayName: 'MovieTrailer',
    propTypes: {
        id: PropTypes.number.isRequired
    },
    mixins: [formPreset],
    definitionPath: 'movie',
    stores: [{store: movieStore, properties: ['movieTrailer']}],
    action: trailerActions,

    /** @inheritDoc */
    renderContent() {
        const {trailerHRef} = this.state;
        return (
            <Panel title='view.movie.detail.trailer'>
                {this.fieldFor('trailerName')}
                {this.fieldFor('trailerHRef')}
                <br/>
                <Trailer url={trailerHRef} />
            </Panel>
        );
    }
});
