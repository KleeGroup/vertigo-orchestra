//librairies
import React, {PropTypes} from 'react';

// web components
import Panel from 'focus-components/components/panel';
import {mixin as formPreset} from 'focus-components/common/form';

//stores & actions
import movieStore from '../../../stores/movie';
import {caracteristicsActions} from '../../../action/movie';

export default React.createClass({
    displayName: 'MovieCaracteristics',
    propTypes: {
        id: PropTypes.number.isRequired
    },
    mixins: [formPreset],
    definitionPath: 'movie',
    stores: [{store: movieStore, properties: ['movieCaracteristics']}],
    action: caracteristicsActions,

    /** @inheritDoc */
    renderContent() {
        return (
            <Panel actions={this._renderActions} title='view.movie.detail.caracteristics'>
                {this.fieldFor('title')}
                {this.fieldFor('originalTitle')}
                {this.fieldFor('keywords')}
                {this.fieldFor('runtime')}
                {this.fieldFor('movieType')}
                {this.fieldFor('productionYear')}
            </Panel>
        );
    }
});
