import React from 'react';

// web components
import {cartridgeBehaviour} from 'focus-components/page/mixin';
import Rankings from './rankings';
import OrchestraTitle from '../components/orchestra-title';

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
            cartridge: {
                component: OrchestraTitle
            },
            actions: {
                primary: [],
                secondary: []
            }
        };
    },

    /** @inheritDoc */
    render() {
        return (
            <div data-demo='homepage'>
                <Rankings/>
            </div>
        );
    }
});
