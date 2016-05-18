//librairies
import React, {PropTypes} from 'react';
import {translate} from 'focus-core/translation';
// web components
import builtInComponent from 'focus-components/common/mixin/built-in-components';
import storeBehaviour  from 'focus-components/common/mixin/store-behaviour';
import definitionMixin  from 'focus-components/common/mixin/definition';
import Panel from 'focus-components/components/panel';

//stores & actions
import processExecutionStore from '../../../stores/process-execution';
import {caracteristicsActions} from '../../../action/process-executions';

export default React.createClass({
    displayName: 'ProcessExecutionCaracteristics',
    propTypes: {
        id: PropTypes.number.isRequired
    },
    mixins: [builtInComponent, storeBehaviour, definitionMixin ],
    definitionPath: 'oProcessExecutionUi',
    stores: [{store: processExecutionStore, properties: ['processExecution']}],
    action: caracteristicsActions,

    getInitialState(){
      return ({isEdit:false});
    },

    componentWillMount(){
      this.action.load(this.props.id);
    },

    componentWillReceiveProps(newProps) {
      this.action.load(newProps.id)
    },

    dlLogFile() {
        window.location.href = './executions/processExecution/'+this.props.id+'/logFile';
    },
    /** @inheritDoc */
    render() {
        const {hasLogFile} = this.state;
        const preId = this.props.id;
        return (
            <Panel title='view.executions.detail.title' data-orchestra='item-detail'>
                {this.fieldFor('beginTime')}
                {this.fieldFor('endTime')}
                {this.fieldFor('executionTime')}
                { hasLogFile &&
                  <div className="mdl-grid" data-focus="field" >
                    <div className="mdl-cell mdl-cell--4-col" data-focus="field-label-container">
                        <label data-focus="label">{translate('view.executions.reportFile.label')}</label>
                    </div>
                    <div className="mdl-cell mdl-cell--8-col" data-focus="field-value-container">
                      <a data-orchestra="download-link" onClick={this.dlLogFile}>{translate('view.executions.reportFile.linkTitle')}</a>
                    </div>
                  </div>
                }
            </Panel>
        );
    }
});
