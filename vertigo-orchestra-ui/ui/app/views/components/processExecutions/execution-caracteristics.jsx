//librairies
import React, {PropTypes} from 'react';

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
    /** @inheritDoc */
    render() {
        return (
            <Panel title='view.executions.detail.title' data-orchestra='item-detail'>
                {this.fieldFor('beginTime')}
                {this.fieldFor('endTime')}
                {this.fieldFor('executionTime')}
            </Panel>
        );
    }
});
