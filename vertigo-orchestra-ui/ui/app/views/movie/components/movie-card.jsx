//libraries
import React, {PropTypes} from 'react';
import {translate} from 'focus-core/translation';
import history from 'focus-core/history';
import {component as Icon} from 'focus-components/common/icon';
import moment from 'moment';

//web components
import {component as Button} from 'focus-components/common/button/action';

export default React.createClass({
    displayName: 'MovieCard',
    propTypes: {
        onClickPreview: PropTypes.func,
        movie: PropTypes.object.isRequired
    },

    render() {
        const {movie, onClickPreview} = this.props;
        const {code, linked, movieType, poster, productionYear, runtime, title, userRating} = movie;
        const duration = moment.utc(moment.duration(runtime, 'seconds').asMilliseconds()).format('h:mm');
        const hasUserRating = userRating > -1;
        const hasDuration = runtime !== undefined;
        const hasSecondaryInfos = hasDuration && hasUserRating;
        return (
            <div className='mdl-card mdl-shadow--4dp' data-demo='material-card'>
                <div className='visuel'>
                    <div>
                        {poster && <img src={poster} title='Picture' alt='Picture' />}
                    </div>
                    {!poster &&
                        <span className="mdl-card__menu">
                            <Button shape='icon' label='view.movie.action.add-photo' icon='add_a_photo' />
                        </span>
                    }
                    {hasSecondaryInfos &&
                        <div className='visuel-infos'>
                            {!hasUserRating &&
                                <div></div>
                            }
                            {hasUserRating &&
                                <div>
                                    <Icon name='grade' />
                                    <span>{`${userRating}%`}</span>
                                </div>
                            }
                            {!hasDuration &&
                                <div></div>
                            }
                            {hasDuration &&
                                <div>
                                    <Icon name='alarm' />
                                    <span>{duration}</span>
                                </div>
                            }
                        </div>
                    }
                </div>
                <div className='mdl-card__supporting-text'>
                    <div className='card-info'>
                        <div className='card-info--level1'>{title}</div>
                        <div className='card-info--level2'>{movieType}</div>
                        <div className='card-info--level3'>{productionYear}</div>
                    </div>
                </div>
                <div className='mdl-card__actions mdl-card--border'>
                    {onClickPreview && <Button shape={null} label='view.movie.action.preview' handleOnClick={() => onClickPreview(+code)} />}
                    <Button shape={null} label='view.movie.action.consult.sheet' handleOnClick={() => history.navigate(`movies/${code}`, true)} />
                </div>
            </div>
        );
    }
});
