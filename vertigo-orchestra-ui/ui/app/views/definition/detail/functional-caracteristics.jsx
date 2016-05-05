//librairies
import React, {PropTypes} from 'react';

// web components
import Panel from 'focus-components/components/panel';
import {mixin as formPreset} from 'focus-components/common/form';

//stores & actions
import processDefinitionStore from '../../../stores/process-definition';
import {caracteristicsActions} from '../../../action/process-definition';

export default React.createClass({
    displayName: 'ProcessFunctionalCaracteristics',
    propTypes: {
        id: PropTypes.number.isRequired
    },
    mixins: [formPreset],
    definitionPath: 'oProcessUi',
    stores: [{store: processDefinitionStore, properties: ['processCaracteristics']}],
    action: caracteristicsActions,

    /** @inheritDoc */
    renderContent() {
        return (
            <Panel actions={this._renderActions} title='view.process.detail.functionalCaracteristics'>
                {this.fieldFor('trtCd')}
                {this.fieldFor('cronExpression')}
                {this.fieldFor('active')}
                {this.fieldFor('multiexecution')}
            </Panel>
        );
    }
});
