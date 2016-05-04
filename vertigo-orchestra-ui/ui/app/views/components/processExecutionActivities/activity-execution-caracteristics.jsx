//librairies
import React, {PropTypes} from 'react';
import {translate} from 'focus-core/translation';

// web components
import builtInComponent from 'focus-components/common/mixin/built-in-components';
import storeBehaviour  from 'focus-components/common/mixin/store-behaviour';
import definitionMixin  from 'focus-components/common/mixin/definition';

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
                <h4>{this.textFor('label')}</h4>
                {this.fieldFor('beginTime')}
                {this.fieldFor('endTime')}
                {this.fieldFor('executionTime')}
                <h4>{translate('view.executions.detail.activities.workspaceIn')}</h4>
                {this.fieldFor('workspaceIn', {hasLabel:false, contentSize:12})}
                <h4>{translate('view.executions.detail.activities.workspaceOut')}</h4>
                {this.fieldFor('workspaceOut', {hasLabel:false, contentSize:12})}
            </div>
        );
    }
});
