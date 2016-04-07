//libraries
import React, {PropTypes} from 'react';

// web components
import Panel from 'focus-components/components/panel';
import {mixin as formPreset} from 'focus-components/common/form';

//stores & actions
import movieStore from '../../../stores/movie';
import {synopsisActions} from '../../../action/movie';

export default React.createClass({
    displayName: 'MovieSynopsis',
    propTypes: {
        id: PropTypes.number.isRequired
    },
    mixins: [formPreset],
    definitionPath: 'movie',
    stores: [{store: movieStore, properties: ['movieSynopsis']}],
    action: synopsisActions,

    /** @inheritDoc */
    renderContent() {
        return (
            <Panel actions={this._renderActions} title='view.movie.detail.synopsis'>
                {this.fieldFor('synopsis')}
                {this.fieldFor('shortSynopsis')}
            </Panel>
        );
    }
});
