import React, {PropTypes} from 'react';


// web components
import Panel from 'focus-components/components/panel';
import ProcessExecutionsList from './processExecutions-list'
import ProcessExecutionsActivities from '../processExecutionActivities'
import ExecutionCaracteristics from './execution-caracteristics';

//stores & actions
import processExecutionListStore from '../../../stores/process-executions-list';
import activityExecutionsListStore from '../../../stores/activity-executions-list';
import {loadProcessExecutionsList} from '../../../action/process-executions';

//cartridge configuration


export default React.createClass({
    displayName: 'ProcessExecutionsList',

    propTypes: {
        id: PropTypes.number.isRequired
    },

    getInitialState () {
        return {
            preId: null
        };
    },


    _onProcessExecutionLineClick(d) {
        this.setState({preId: d.preId});

    },

    /** @inheritDoc */
    render() {
        const {id} = this.props;
        const {preId} = this.state;
        return (
          <div data-orchestra='processExecutions-list'>
            <div data-orchestra='timeline'>
              <ProcessExecutionsList
                  id = {id}
                  action={loadProcessExecutionsList(id)}
                  columns={[]}
                  store={processExecutionListStore}
                  handleLineClick={d => this.setState({preId: d.preId})}
               />
            </div>
            <div data-orchestra='item-detail'>
               {preId !== null &&
                 <div>
                  <div>
                    <ExecutionCaracteristics id={preId} />
                  </div>
                  <div>
                    <ProcessExecutionsActivities id={preId} />
                  </div>
                </div>
               }
            </div>
          </div>

        );
    }
});
