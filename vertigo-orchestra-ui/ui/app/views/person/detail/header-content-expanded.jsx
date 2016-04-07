// libraries
import React from 'react';
import {translate} from 'focus-core/translation';

//web components
import {mixin as formPreset} from 'focus-components/common/form';

//stores
import personStore from '../../../stores/person';

//custom components
import Picture from '../components/picture';

export default React.createClass({
    displayName: 'PersonDetailHeaderExpanded',
    mixins: [formPreset],
    definitionPath: 'person',
    stores: [{store: personStore, properties: ['personIdentity']}],

    /** @inheritDoc */
    renderContent() {
        const {fullName, photoURL} = this.state;
        return (
            <div data-demo='header-content-expanded'>
                <Picture url={photoURL} title={fullName} />
                <div data-demo='header-content-expanded__infos'>
                    <div className="key-concept">{translate('view.person.keyConcept.name')}</div>
                    <h3>{this.textFor('fullName')}</h3>
                    <h5>{this.textFor('activity')}</h5>
                    <div>{this.textFor('shortBiography')}</div>
                </div>
            </div>
        );
    }
})
