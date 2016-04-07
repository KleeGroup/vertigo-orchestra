//libraries
import React, {PropTypes} from 'react';

//web components
import {mixin as lineMixin} from 'focus-components/list/selection/line'

export default React.createClass({
    displayName: 'Line',
    mixins: [lineMixin],
    definitionPath: 'movie',
    renderLineContent() {
        return (
            <div data-demo='line'>
                Unknown type of concept.
            </div>
        );
    }
});
