import React from 'react';
import {translate} from 'focus-core/translation';
import {component as Icon} from 'focus-components/common/icon';

function masterdataHome() {
    return (
        <div data-demo='masterdata-home'>
            <div>
                <Icon name='arrow_back' />
                <span>{translate('view.admin.masterdata.home')}</span>
            </div>
        </div>
    );
}

masterdataHome.displayName = 'MasterdataHome';
export default masterdataHome;
