console.info('############# Application starting ############');
//Start the application.
console.info('Load all the routes.');
require('./router');
//Start the router.
Backbone.history.start();
