//librairies
import React, {PropTypes} from 'react';

// web components
import Panel from 'focus-components/components/panel';
import {mixin as formPreset} from 'focus-components/common/form';

//stores & actions
import processDefinitionStore from '../../../stores/process-definition';
import {caracteristicsActions} from '../../../action/process-definition';

export default React.createClass({
    displayName: 'ProcessTehnicalCaracteristics',
    propTypes: {
        id: PropTypes.number.isRequired
    },
    mixins: [formPreset],
    definitionPath: 'oProcess',
    stores: [{store: processDefinitionStore, properties: ['processCaracteristics']}],
    action: caracteristicsActions,

    /** @inheritDoc */
    renderContent() {
        return (
            <Panel actions={this._renderActions} title='view.process.detail.technicalCaracteristics'>
                {this.fieldFor('proId')}
                {this.fieldFor('name')}
            </Panel>
        );
    }
});
