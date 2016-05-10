import React from 'react';


// web components
import {cartridgeBehaviour} from 'focus-components/page/mixin';
import {storeBehaviour} from 'focus-components/page/mixin';
import OrchestraTitle from '../components/orchestra-title';
import SummaryCardList from './components/summary-card-list'
import {component as Button} from 'focus-components/common/button/action';

//stores & actions
import summaryListStore from '../../stores/summary-list';
import {loadSummaryList} from '../../action/process-executions';

//cartridge configuration


export default React.createClass({
    displayName: 'HomeView',
    mixins: [cartridgeBehaviour],

    /**
    * Related to the CartridgeBehaviour.
    * Define the cartridge configuration.
    * @return {[type]} [description]
    */
    cartridgeConfiguration() {
        return {
            summary: {
                component: OrchestraTitle
            },
            canDeploy: false
        };
    },

    getInitialState () {
        return {
            status: null,
            offset: 0
        };
    },

    _onErrorClick(d) {
        this.setState({status:'ERROR'});
    },
    _onAllClick(d) {
        this.setState({status:null});
    },

    _onSuccessClick(d) {
        this.setState({status:'DONE'});
    },

    _onPreviousClick(d) {
        const {offset} = this.state;
        this.setState({offset:offset-1});
    },
    _onNextClick(d) {
        const {offset} = this.state;
        this.setState({offset:offset+1});
    },
    _onCurrentClick(d) {
        this.setState({offset:0});
    },

    componentWillUpdate(nextProps,nextState){
      const {status, offset} = nextState;
      loadSummaryList.updateProperties({criteria : {status:status, offset:offset}});
    },

    /** @inheritDoc */
    render() {
      let {offset} =  this.state
      let curr = new Date; // get current date
      let first = curr.getDate() - curr.getDay() +1 +7*offset; // First day is the day of the month - the day of the week
      let last = first + 6; // last day is the first day + 6

      let firstday = new Date(curr.setDate(first)).toUTCString();
      let lastday = new Date(curr.setDate(last)).toUTCString();

        return (
            <div data-demo='homepage'>
            <div>
                fisrt : {firstday}
                last : {lastday}
            </div>
            <div data-orchestra='filter'>
              <Button label='Tout' type='button' handleOnClick={this._onAllClick} />
              <Button label='Err' type='button' handleOnClick={this._onErrorClick} />
              <Button label='Succ' type='button' handleOnClick={this._onSuccessClick} />
            </div>
            <div data-orchestra='time-filter'>
              <Button label='Prev' type='button' handleOnClick={this._onPreviousClick} />
              <Button label='Curr' type='button' handleOnClick={this._onCurrentClick} />
              <Button label='Suiv' type='button' handleOnClick={this._onNextClick} />
            </div>
              <SummaryCardList
                  action={loadSummaryList.load}
                  columns={[]}
                  store={summaryListStore}
               />
            </div>
        );
    }
});
