import history from 'focus-core/history';

//Start the application.
console.info('############# Application starting ############');
console.info('Loading routes...');
require('./router');
console.info('All the routes are loaded...');

//Start the router.
history.start();
