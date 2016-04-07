//libraries
import React, {PropTypes} from 'react';

//web components
import {component as Button} from 'focus-components/common/button/action';
import {component as Modal} from 'focus-components/application/popin';


export default React.createClass({
    displayName: 'Poster',
    propTypes: {
        poster: PropTypes.string,
        title: PropTypes.string,
        hasZoom: PropTypes.bool
    },
    openPosterPopin() {
        this.refs['modal-poster'].toggleOpen();
    },
    render(){
        const {hasZoom, poster, title} = this.props;
        return (
            <div data-demo='poster'>
                {poster &&
                    <img alt={title} src={poster} title={title} />
                }
                {poster && hasZoom &&
                    <div>
                        <div className='zoom'>
                            <Button icon='zoom_in' shape='fab' label='view.person.card.consult.info' handleOnClick={this.openPosterPopin} />
                        </div>
                        <Modal className='popin-poster' ref='modal-poster'>
                            <img alt={title} src={poster} title={title} />
                        </Modal>
                    </div>
                }
            </div>
        );
    }
})
