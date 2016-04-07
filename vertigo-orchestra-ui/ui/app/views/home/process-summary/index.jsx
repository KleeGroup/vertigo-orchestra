import React, {Component} from 'react';
import {component as Modal} from 'focus-components/application/popin';
// import {setHeader} from 'focus-core/application'
import {translate} from 'focus-core/translation';

//import CountryActionBar from './country-action-bar';
import {loadProcessSummary} from '../../../action/process-executions';
import processExecutionStore from '../../../stores/process-executions';


export default React.createClass({
    displayName: 'ProcessSummary',
    propTypes: {
        id: PropTypes.number.isRequired
      },
    definitionPath: 'oProcessSummary',
    stores: [{store: processExecutionStore, properties: ['processSummary']}],

    render: function() {
        const {detailId} = this.state;
        return (
            <div data-demo='masterdata-countries'>

            </div>


        );
    }
});


// Page which stands for the administration
class MasterdataCountry extends Component {
    constructor(props){
        super(props);

        // Initial state
        this.state = {
            detailId: null
        };
    }
    _onDetailPopinClose = () => {
        //Remove the detailId and call the list load action.
        this.setState({detailId: null}, () => loadCountryList());
    };

    render() {
        const {detailId} = this.state;
        return (
            <div data-demo='masterdata-countries'>
                <CountryCriteria onSearch={_dispatchSearchCriteria} />
                {/*LIST : This is the list which trigger the search and is connected to the list store */}
                <CountryList
                    action={loadCountryList}
                    handleLineClick={d => this.setState({detailId: d.id})}
                    store={countryListStore} />

                {
                    /*
                    When it is in the state, the popin is automatically displayed
                    The detail popin is handled by this id
                    */
                    detailId !== null &&
                    <Modal
                        onPopinClose={this._onDetailPopinClose}
                        open={true}
                        type='from-right'>
                        <CountryDetail id={detailId} isEdit={true}/>
                    </Modal>
                }
            </div>
        );
    }
}

MasterdataCountry.displayName = 'MasterdataCountry';
export default MasterdataCountry;
