//libraries
import React, {PropTypes} from 'react';

//web components
import PersonCard from './person-card';
import {component as Modal} from 'focus-components/application/popin';
import PersonPreview from '../../person/preview';


export default React.createClass({
    displayName: 'PersonCardList',
    propTypes: {
        persons: PropTypes.array
    },
    getDefaultProps() {
        return {
            persons: []
        }
    },
    getInitialState() {
        return {
            personCodePreview: null
        }
    },
    render() {
        const {persons} = this.props;
        const {personCodePreview} = this.state;
        return (
            <div data-demo='concept-card-list'>
                {persons &&
                    persons.map(person => {
                        const {code} = person;
                        const key = `person-card-${code}`;
                        return (
                            <PersonCard key={key} person={person} onClickPreview={personId => this.setState({personCodePreview: personId})} />
                        );
                    })
                }
                {personCodePreview &&
                    <Modal open={true} onPopinClose={() => this.setState({personCodePreview: null})} type='from-right'>
                        <PersonPreview id={personCodePreview} />
                    </Modal>
                }
            </div>
        );
    }
});
