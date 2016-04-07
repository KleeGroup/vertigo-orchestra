//libraries
import React, {PropTypes} from 'react';
import {translate} from 'focus-core/translation';
import history from 'focus-core/history';

// web components
import {component as Button} from 'focus-components/common/button/action';
import builtInComponents from 'focus-components/common/mixin/built-in-components';
import definitionMixin from 'focus-components/common/mixin/definition';
import storeBehaviour from 'focus-components/common/mixin/store-behaviour';
import Panel from 'focus-components/components/panel';

// actions & stores
import {identityActions} from '../../../action/person';
import personStore from '../../../stores/person';

//custom components
import Picture from '../components/picture';

export default React.createClass({
    displayName: 'PersonPreview',
    propTypes: {
        id: PropTypes.number.isRequired
    },
    mixins: [builtInComponents, definitionMixin, storeBehaviour],
    definitionPath: 'person',
    stores: [{store: personStore, properties: ['personIdentity', 'personBiography']}],

    /** @inheritDoc */
    getInitialState(){
        return {};
    },

    /** @inheritDoc */
    componentDidMount() {
        const {id} = this.props;
        identityActions.load(id);
    },

    /** @inheritDoc */
    render() {
        const {id} = this.props;
        const {code,fullName, photoURL} = this.state;
        return (
            <div data-demo='preview'>
                <div data-demo='preview-header'>
                    <Picture url={photoURL} title={fullName} />
                    <div>
                        <h3>{this.textFor('fullName')}</h3>
                        <h5>{this.textFor('activity')}</h5>
                        <div>{this.textFor('shortBiography')}</div>
                        <br/>
                        <Button label='view.person.action.consult.sheet' handleOnClick={() => {history.navigate(`persons/${code}`, true); window.scrollTo(0, 0);}} />
                    </div>
                </div>
                <div data-demo='preview-content'>
                    <Panel title='view.person.detail.identity'>
                        {this.displayFor('fullName')}
                        {this.displayFor('firstName')}
                        {this.displayFor('birthDate')}
                    </Panel>
                    <Panel title='view.person.detail.lastmovies'>

                    </Panel>
                </div>
            </div>
        );
    }
});
