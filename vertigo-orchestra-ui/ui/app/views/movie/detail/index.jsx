// libraries
import React, {PropTypes} from 'react';

// web components
import ScrollspyContainer from 'focus-components/components/scrollspy-container';
import {component as BackButton} from 'focus-components/common/button/back';
import {cartridgeBehaviour} from 'focus-components/page/mixin';

//views
import Caracteristics from './caracteristics';
import Casting from './casting';
import HeaderExpanded from './header-content-expanded';
import HeaderSummary from './header-content-summary';
import Overview from './overview';
import Posters from './posters';
import Synopsis from './synospis';
import Trailer from './trailer';

export default React.createClass({
    displayName: 'MovieDetailView',
    propTypes: {
        id: PropTypes.number.isRequired
    },
    mixins: [cartridgeBehaviour],
    /**
    * Related to the CartridgeBehaviour.
    * Define the cartridge configuration.
    * @return {[type]} [description]
    */
    cartridgeConfiguration() {
        const props = { hasLoad: false, hasForm: false }; //{id: this.props.id};
        return {
            barLeft: { component: BackButton },
            cartridge: { component: HeaderExpanded, props },
            summary: { component: HeaderSummary, props },
            actions: {
                primary: this._getGlobalPrimaryActions() || [],
                secondary: []
            }
        };
    },

    _getGlobalPrimaryActions() {
        const actions = [];
        actions.push({label: 'Imprimer', icon: 'print', action: () => { alert('todo print')}});
        return actions;
    },

    /** @inheritDoc */
    render() {
        const {id} = this.props;
        return (
            <ScrollspyContainer gridContentSize={10} gridMenuSize={2}>
                <Overview hasLoad={false} hasForm={false} />
                <Caracteristics id={id} />
                <Synopsis id={id} />
                <Trailer id={id} />
                <Posters id={id} />
                <Casting id={id} />
            </ScrollspyContainer>
        );
    }
});
