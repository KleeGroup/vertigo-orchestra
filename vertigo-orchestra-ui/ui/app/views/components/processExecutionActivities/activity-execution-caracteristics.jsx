//librairies
import React, {PropTypes} from 'react';
import {translate} from 'focus-core/translation';

// web components
import builtInComponent from 'focus-components/common/mixin/built-in-components';
import storeBehaviour  from 'focus-components/common/mixin/store-behaviour';
import definitionMixin  from 'focus-components/common/mixin/definition';
import Panel from 'focus-components/components/panel';

//stores & actions
import activityExecutionStore from '../../../stores/activity-execution';
import {activityCaracteristicsActions} from '../../../action/process-executions';

export default React.createClass({
    displayName: 'ActivityExecutionCaracteristics',
    propTypes: {
        id: PropTypes.number.isRequired
    },
    mixins: [builtInComponent, storeBehaviour, definitionMixin ],
    definitionPath: 'oActivityExecutionUi',
    stores: [{store: activityExecutionStore, properties: ['activityExecution']}],
    action: activityCaracteristicsActions,

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
            <div>
                <Panel title={this.state.label}>
                  {this.fieldFor('beginTime')}
                  {this.fieldFor('endTime')}
                  {this.fieldFor('executionTime')}
                  <h6>{translate('view.executions.detail.activities.workspaceIn')}</h6>
                  {this.fieldFor('workspaceIn', {hasLabel:false, contentSize:12})}
                  <h6>{translate('view.executions.detail.activities.workspaceOut')}</h6>
                  {this.fieldFor('workspaceOut', {hasLabel:false, contentSize:12})}
                </Panel>
            </div>
        );
    }
});
