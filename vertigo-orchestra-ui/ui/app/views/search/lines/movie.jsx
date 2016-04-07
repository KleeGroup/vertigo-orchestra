//libraries
import React, {PropTypes} from 'react';

//web components
import {mixin as lineMixin} from 'focus-components/list/selection/line'

export default React.createClass({
    displayName: 'MovieLine',
    mixins: [lineMixin],
    definitionPath: 'movie',
    propTypes: {
        data: PropTypes.object.isRequired
    },
    renderLineContent() {
        const {data} = this.props;
        const {code} = data;
        return (
            <div key={code} data-demo='movie-line'>
                <div className='level1'>{this.textFor('title')}</div>
                <div className='level2'>{this.textFor('movieType')}</div>
                <div className='level3'>{this.textFor('productionYear')}</div>
            </div>
        );
    }
});
