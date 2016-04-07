import React, {PropTypes} from 'react';
import {translate} from 'focus-core/translation';

// web components
import {cartridgeBehaviour} from 'focus-components/page/mixin';
import MasterdataMenu from './menu';
import MasterdataHome from './home';

function TitleCartridge({reference}) {
    const title = `view.admin.masterdata.${reference ? reference : 'title'}`;
    return (
        <div data-demo='masterdata-title'>
            {reference && <h6>{translate('view.admin.masterdata.title')}</h6>}
            <h3>{translate(title)}</h3>
        </div>
    );
}

function TitleSummary({reference}) {
    const title = `view.admin.masterdata.${reference ? reference : 'title'}`;
    return (
        <div data-demo='masterdata-title'>
            <h4>{translate(title)}</h4>
        </div>
    );
}


export default React.createClass({
    displayName: 'MasterDataHome',
    mixins: [cartridgeBehaviour],
    propTypes: {
        reference: PropTypes.string,
        ReferenceComponent: PropTypes.func
    },
    getDefaultProps() {
        return {
            reference: null,
            ReferenceComponent: MasterdataHome
        };
    },


    /**
    * Related to the CartridgeBehaviour.
    * Define the cartridge configuration.
    * @return {[type]} [description]
    */
    cartridgeConfiguration() {
        const {reference} = this.props;
        return {
            summary: {
                component: TitleSummary,
                props: { reference }
            },
            cartridge: {
                component: TitleCartridge,
                props: { reference }
            },
            actions: {
                primary: [],
                secondary: []
            }
        };
    },

    /** @inheritDoc */
    render() {
        const {reference, ReferenceComponent} = this.props;
        return (
            <div data-demo='masterdata'>
                <div data-demo='masterdata--nav'>
                    <MasterdataMenu reference={reference} />
                </div>
                <div data-demo='masterdata--component'>
                    <ReferenceComponent />
                </div>
            </div>
        );
    }
});
