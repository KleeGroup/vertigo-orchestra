//librairies
import React, {PropTypes} from 'react';

// web components
import Panel from 'focus-components/components/panel';
import {mixin as formPreset} from 'focus-components/common/form';

//stores & actions
import personStore from '../../../stores/person';
import {biographyActions} from '../../../action/person';

export default React.createClass({
    displayName: 'PersonBiography',
    propTypes: {
        id: PropTypes.number.isRequired
    },
    mixins: [formPreset],
    definitionPath: 'person',
    stores: [{store: personStore, properties: ['personBiography']}],
    action: biographyActions,

    /** @inheritDoc */
    renderContent() {
        return (
            <Panel actions={this._renderActions} title='view.person.detail.biography'>
                {this.fieldFor('biography')}
                {this.fieldFor('shortBiography')}
            </Panel>
        );
    }
});
