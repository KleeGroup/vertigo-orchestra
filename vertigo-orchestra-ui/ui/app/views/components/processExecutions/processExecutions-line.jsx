//librairies
import React from 'react';
import {translate} from 'focus-core/translation';
import history from 'focus-core/history';
import {mixin as lineMix} from 'focus-components/list/timeline/line';


const ProcessExecutionsLine = React.createClass({
    displayName: 'ProcessExecutionsLine',
    mixins: [lineMix],
    definitionPath: 'oProcessExecution',

    renderLineContent() {
        return (
            <div >
                {this.textFor('beginTime')}
            </div>
        );
    }
});

export default ProcessExecutionsLine;
