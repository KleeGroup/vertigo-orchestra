//librairies
import React, {PropTypes} from 'react';

// web components
import Panel from 'focus-components/components/panel';
import {mixin as formPreset} from 'focus-components/common/form';

//stores & actions
import processDefinitionStore from '../../../stores/process-definition';
import {propertiesActions} from '../../../action/process-definition';

export default React.createClass({
    displayName: 'ProcessTechnicalCaracteristics',
    propTypes: {
        id: PropTypes.number.isRequired
    },
    mixins: [formPreset],
    definitionPath: 'oProcessUi',
    stores: [{store: processDefinitionStore, properties: ['processCaracteristics']}],
    action: propertiesActions,

    /** @inheritDoc */
    renderContent() {
        return (
            <Panel actions={this._renderActions} title='view.process.detail.technicalCaracteristics'>
              {this.fieldFor('cronExpression')}
              {this.fieldFor('active', {options: {disabled: !this.state.isEdit, label: ''}})}
              {this.fieldFor('multiexecution', {options: {disabled: !this.state.isEdit, label: ''}})}
              {this.fieldFor('rescuePeriod')}
            </Panel>
        );
    }
});
