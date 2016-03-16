import React from 'react';
import {translate} from 'focus-core/translation';

export default React.createClass({
    displayName: 'HomeView',
    render() {
        return (
            <div>
                <h1>{translate('test.welcome')}</h1>
            </div>
        );
    }
});
