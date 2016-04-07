//libraries
import React, {PropTypes} from 'react';

function Picture({title, url}) {
    return (
        <div data-demo='picture'>
            <div>
                <i className="material-icons">person</i>
                {url &&
                    <img alt={title} src={url} title={title} />
                }
            </div>
        </div>
    );
}

Picture.displayName = 'Picture';
Picture.propTypes = {
    url: PropTypes.string,
    title: PropTypes.string
};
export default Picture;
