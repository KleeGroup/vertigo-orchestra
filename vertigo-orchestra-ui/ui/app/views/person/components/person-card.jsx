//libraries
import React, {PropTypes} from 'react';
import history from 'focus-core/history';

//web components
import {component as Button} from 'focus-components/common/button/action';

function PersonCard({onClickPreview, person}) {
    const {code, leadActor, linked, name, photoURL, role} = person;
    return (
        <div className='mdl-card mdl-shadow--4dp' data-demo='material-card'>
            <div className='visuel'>
                <div>
                    {photoURL && <img src={photoURL} title='Picture' alt='Picture' />}
                </div>
                {!photoURL &&
                    <span className="mdl-card__menu">
                        <Button shape='icon' label='view.person.action.add-photo' icon='add_a_photo' handleOnClick={() => console.log('click on person card add photo')} />
                    </span>
                }
            </div>
            <div className='mdl-card__supporting-text'>
                <div className='card-info' data-demo="person-identity">
                    <div className='card-info--level1'>{name}</div>
                    <div className='card-info--level2'>{role}</div>
                </div>
            </div>
            <div className='mdl-card__actions mdl-card--border'>
                {onClickPreview && <Button shape={null} label='view.person.action.preview' handleOnClick={() => onClickPreview(+code)} />}
                <Button shape={null} label='view.person.action.consult.sheet' handleOnClick={() => history.navigate(`persons/${code}`, true)} />
            </div>
        </div>
    );
};

PersonCard.displayName = 'PersonCard';
PersonCard.propTypes = {
    onClickPreview: PropTypes.func,
    person: PropTypes.object.isRequired
};
export default PersonCard;
