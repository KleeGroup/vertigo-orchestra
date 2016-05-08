//librairies
import React from 'react';
import {translate} from 'focus-core/translation';
import history from 'focus-core/history';
import {mixin as LinePreset} from 'focus-components/list/selection/line'


const SummaryLine = React.createClass({
    displayName: 'SummaryLine',
    mixins: [LinePreset],
    definitionPath: 'oExecutionSummary',


    _navigateToDetail(id, event) {
      history.navigate(`#definitions/${id}`, true);
      window.scrollTo(0, 0);
    },

    renderLineContent() {
        return (
            <div data-orchestra='summary-line-content'>
                <div data-orchestra='name' onClick={(event) => this._navigateToDetail(this.props.data.proId, event)}>
                {this.textFor('processName')}
                </div>
                <div data-orchestra='counts'>
                {this.textFor('successfulCount')}
                {this.textFor('misfiredCount')}
                {this.textFor('errorsCount')}
                </div>
                <div data-orchestra='last'>
                <span>Dernière execution le</span>
                <br/>
                {this.textFor('lastExecutionTime')}
                </div>
                <div data-orchestra='next'>
                <span>Prochaine execution le</span>
                <br/>
                {this.textFor('nextExecutionTime')}
                </div>
            </div>
        );
    }
});

export default SummaryLine;
