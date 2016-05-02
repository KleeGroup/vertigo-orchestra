//librairies
import React, {PropTypes} from 'react';

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
                {this.fieldFor('aceId')}
                {this.fieldFor('beginTime')}
                {this.fieldFor('endTime')}
            </div>
        );
    }
});
