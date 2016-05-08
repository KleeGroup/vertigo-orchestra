import React from 'react';


// web components
import {cartridgeBehaviour} from 'focus-components/page/mixin';
import {storeBehaviour} from 'focus-components/page/mixin';
import OrchestraTitle from '../components/orchestra-title';
import SummaryCardList from './components/summary-card-list'

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



    /** @inheritDoc */
    render() {
        return (
            <div data-demo='homepage'>
              <SummaryCardList
                  action={loadSummaryList}
                  columns={[]}
                  store={summaryListStore}
               />
            </div>
        );
    }
});
