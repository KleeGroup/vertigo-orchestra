import React, {PropTypes} from 'react';
import {translate} from 'focus-core/translation';

// web components
import Panel from 'focus-components/components/panel';
import {component as Button} from 'focus-components/common/button/action';
import ProcessExecutionsList from './processExecutions-list'
import ProcessExecutionsActivities from '../processExecutionActivities'
import ExecutionCaracteristics from './execution-caracteristics';

//stores & actions
import processExecutionListStore from '../../../stores/process-executions-list';
import activityExecutionsListStore from '../../../stores/activity-executions-list';


//cartridge configuration


export default React.createClass({
    displayName: 'ProcessExecutionsList',

    propTypes: {
        id: PropTypes.number.isRequired,
        initialStatus: PropTypes.string
    },

    componentWillReceiveProps(nextProps){
      this.setState({
                    preId: nextProps.id,
                    status: nextProps.initialStatus
                  }
      );
    },


    getInitialState () {
        return {
            preId: null,
            status: null
        };
    },

    componentWillMount(){
        this.setState({status: this.props.initialStatus});
    },


    _onProcessExecutionLineClick(d) {
        this.setState({preId: d.preId});

    },

    _onErrorClick(d) {
        this.setState({status: 'ERROR', preId:null});

    },

    _onAllClick(d) {
        this.setState({status:null, preId:null});

    },
    _onSuccessClick(d) {
        this.setState({status:'DONE', preId:null});

    },

    /** @inheritDoc */
    render() {
        const {id} = this.props;
        const {status, preId} = this.state;
        return (
          <div data-orchestra='processExecutions-list'>
            <div data-orchestra='panel-left'>
              <div data-orchestra='header'>
                <h3>{translate('view.executions.title')}</h3>
                <div data-orchestra='filter'>
                  <Button label='Tout' type='button' handleOnClick={this._onAllClick} />
                  <Button label='Err' type='button' handleOnClick={this._onErrorClick} />
                  <Button label='Succ' type='button' handleOnClick={this._onSuccessClick} />
                </div>
              </div>
              <div data-orchestra='timeline'>
                <ProcessExecutionsList
                    id = {id}
                    status = {status}
                    columns={[]}
                    store={processExecutionListStore}
                    handleLineClick={d => this.setState({preId: d.preId})}
                 />
              </div>
            </div>
            <div data-orchestra='item-detail'>
               {preId !== null &&
                 <div>
                  <div>
                    <h3>{translate('view.executions.detail.title')}</h3>
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
