//librairies
import React, {PropTypes} from 'react';

// web components
import Panel from 'focus-components/components/panel';
import {mixin as formPreset} from 'focus-components/common/form';
import {component as Button} from 'focus-components/common/button/action';
import {translate} from 'focus-core/translation';

//stores & actions
import personStore from '../../../stores/person';

//custom components
import Trailer from '../../movie/components/trailer';

export default React.createClass({
    displayName: 'Overview',
    mixins: [formPreset],
    definitionPath: 'person',
    stores: [{store: personStore, properties: ['personIdentity']}],

    //pourcentage de completude<br/>
    // nombre de films<br/>
    // nombre de réalisations<br/>
    /** @inheritDoc */
    renderContent() {
        const {code} = this.state;
        const url = `http://www.allocine.fr/personne/fichepersonne_gen_cpersonne=${code}.html`;
        return (
            <Panel title='view.person.detail.overview' data-demo='overview'>
                <Button label={translate('view.person.action.consult.allocine')} type='button' handleOnClick={() => window.open(url,'_blank')} />
            </Panel>
        );
    }
});
