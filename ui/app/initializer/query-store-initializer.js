module.exports = {
    initialize() {
        Focus.dispatcher.handleServerAction({
            data: {
                scope: 'ALL'
            },
            type: 'update'
        });
    }
};
