//librairies
import React, {PropTypes} from 'react';

// web components
import Panel from 'focus-components/components/panel';
import {mixin as formPreset} from 'focus-components/common/form';
import {component as Modal} from 'focus-components/application/popin';
import {component as Button} from 'focus-components/common/button/action';
import {translate} from 'focus-core/translation';

//stores & actions
import processExecutionStore from '../../../stores/process-execution';
import {summaryAction} from '../../../action/process-executions';

//views
import ProcessExecutions from '../../components/processExecutions';
import ProcessSummary from '../../components/processSummary'

export default React.createClass({
    displayName: 'ProcessCaracteristics',
    propTypes: {
        id: PropTypes.number.isRequired
    },
    mixins: [formPreset],
    definitionPath: 'oProcessUi',
    stores: [{store: processExecutionStore, properties: ['summary']}],
    action: summaryAction,

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

    _onErrorClick() {
        this.setState({
            initialStatus: 'ERROR'
        });
        this._onProcessExecutionsModalToggle();
    },

    _onSuccessClick() {
        this.setState({
            initialStatus: 'DONE'
        });
        this._onProcessExecutionsModalToggle();
    },

    /** @inheritDoc */
    renderContent() {
        const {isProcessExecutionsModalOpen, initialStatus} = this.state;
        const {errorsCount, successfulCount, misfiredCount, averageExecutionTime} = this.state;
        const {id} = this.props;
        return (
            <Panel title='view.process.detail.executions'>
                <ProcessSummary
                  errorsCount={errorsCount}
                  successfulCount={successfulCount}
                  misfiredCount={misfiredCount}
                  averageExecutionTime={averageExecutionTime}
                  handleErrorClick={this._onErrorClick}
                  handleSuccessClick={this._onSuccessClick} />
                <Button label={translate('button.viewAllExecutions')} type='button' handleOnClick={this._onProcessExecutionsModalToggle} />
                {isProcessExecutionsModalOpen &&
                    <div>
                        <Modal open={true} type='from-right' size="large" onPopinClose={this._onProcessExecutionsModalToggle}>
                          <ProcessExecutions id={id} initialStatus={initialStatus} />
                        </Modal>
                    </div>
                }
            </Panel>

        );
    }
});
