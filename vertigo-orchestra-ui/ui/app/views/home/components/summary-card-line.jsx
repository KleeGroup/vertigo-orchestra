//librairies
import React from 'react';
import {translate} from 'focus-core/translation';
import history from 'focus-core/history';
import {mixin as LinePreset} from 'focus-components/list/selection/line'


const SummaryLine = React.createClass({
    displayName: 'SummaryLine',
    mixins: [LinePreset],
    definitionPath: 'oExecutionSummary',
    renderLineContent() {
        return (
            <div data-orchestra='summary-line-content'>
                <div onClick={() => {history.navigate(`definitions/${this.props.data.proId}`, true); window.scrollTo(0, 0);}}>
                {this.textFor('processName')}
                </div>
                <div data-orchestra='summary-line-counts'>
                {this.textFor('successfulCount')}
                {this.textFor('misfiredCount')}
                {this.textFor('errorsCount')}
                </div>
                {this.textFor('lastExecutionTime')}
                {this.textFor('nextExecutionTime')}
            </div>
        );
    }
});

export default SummaryLine;
