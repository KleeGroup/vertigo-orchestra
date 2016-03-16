import FocusCore from 'focus-core';

export default {
    initialize() {
        FocusCore.dispatcher.handleServerAction({
            data: {
                scope: 'ALL'
            },
            type: 'update'
        });
    }
};
