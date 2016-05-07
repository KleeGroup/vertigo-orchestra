//librairies
import React, {PropTypes} from 'react';

// web components
import Panel from 'focus-components/components/panel';
import {mixin as formPreset} from 'focus-components/common/form';
import {component as Modal} from 'focus-components/application/popin';
import {component as Button} from 'focus-components/common/button/action';
import {translate} from 'focus-core/translation';

//stores & actions
import processDefinitionStore from '../../../stores/process-definition';
import {caracteristicsActions} from '../../../action/process-definition';

//views
import ProcessExecutions from '../../components/processExecutions';

export default React.createClass({
    displayName: 'ProcessCaracteristics',
    propTypes: {
        id: PropTypes.number.isRequired
    },
    mixins: [formPreset],
    definitionPath: 'oProcessUi',
    stores: [{store: processDefinitionStore, properties: ['processCaracteristics']}],
    action: caracteristicsActions,

    getInitialState () {
        return {
            isProcessExecutionsModalOpen: false
        };
    },

    _onProcessExecutionsModalToggle() {
        const {isProcessExecutionsModalOpen} = this.state;
        this.setState({
            isProcessExecutionsModalOpen: !isProcessExecutionsModalOpen
        });
    },

    /** @inheritDoc */
    renderContent() {
        const {isProcessExecutionsModalOpen} = this.state;
        const {id} = this.props;
        return (
            <Panel title='view.process.detail.executions'>
                <Button label={translate('button.viewAllExecutions')} type='button' handleOnClick={this._onProcessExecutionsModalToggle} />
                {isProcessExecutionsModalOpen &&
                    <div>
                        <Modal open={true} type='from-right' size="large" onPopinClose={this._onProcessExecutionsModalToggle}>
                          <ProcessExecutions id={id} />
                        </Modal>
                    </div>
                }
            </Panel>

        );
    }
});
