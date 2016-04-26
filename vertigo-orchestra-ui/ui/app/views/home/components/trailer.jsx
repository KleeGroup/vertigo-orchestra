//libraries
import React, {PropTypes} from 'react';

function Trailer({url}) {
    return (
        <div data-demo='trailer'>
        {url &&
            <div>
                <object type="application/x-shockwave-flash" width="800" height="600" data={url}>
                    <param name="movie" value={url} />
                    <param name="allowFullScreen" value="true"></param>
                    <param name="allowScriptAccess" value="always"></param>
                    <embed src={url} type="application/x-shockwave-flash" width="800" height="600" allowFullScreen="true" allowScriptAccess="always"></embed>
                </object>
            </div>
        }
        </div>
    );
}

Trailer.displayName = 'Trailer';
Trailer.propTypes = {
    url: PropTypes.string
};
export default Trailer;
