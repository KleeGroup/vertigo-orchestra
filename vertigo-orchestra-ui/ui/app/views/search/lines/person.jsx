//libraries
import React, {PropTypes} from 'react';

//web components
import {mixin as lineMixin} from 'focus-components/list/selection/line'

export default React.createClass({
    displayName: 'PersonLine',
    mixins: [lineMixin],
    definitionPath: 'person',
    propTypes: {
        data: PropTypes.object.isRequired
    },
    renderLineContent() {
        const {data} = this.props;
        const {code} = data;
        return (
            <div key={code} data-demo='person-line'>
                <div className='level1'>{this.textFor('fullname')}</div>
                <div className='level2'>{this.textFor('activity')}</div>
            </div>
        );
    }
});
