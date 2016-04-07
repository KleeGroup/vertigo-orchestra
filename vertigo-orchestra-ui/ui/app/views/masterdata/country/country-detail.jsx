//librairies
import React from 'react';
import {translate} from 'focus-core/translation';
import Panel from 'focus-components/components/panel';
import {mixin as formPreset} from 'focus-components/common/form';

//stores & actions
import countryStore from '../../../stores/country';
import {countryActions} from '../../../action/country'

export default React.createClass({
    displayName: 'Country',
    mixins: [formPreset],
    definitionPath: 'country',
    stores: [{store: countryStore, properties: ['informations']}],
    action: countryActions,
    /** @inheritDoc */
    renderContent() {
        return (
            <div>
                <h4>{translate('country.detail.title')}</h4>
                <Panel actions={this._renderActions} title='view.masterdata.country.caracteristics'>
                    {this.displayFor('id')}
                    {this.fieldFor('code')}
                    {this.fieldFor('name')}
                    {this.fieldFor('maps')}
                </Panel>
            </div>
        );
    }
});
