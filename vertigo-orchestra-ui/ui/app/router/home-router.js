import {application, router} from 'focus-core';
import HomeView from '../views/home';

const HomeRouter = router.extend({
    log: true,
    beforeRoute() {
        application.changeRoute('home');
    },
    routes: {
        '': 'home',
        home: 'home'
    },
    home() {
        console.log('ROUTE: HOME');
        this._pageContent(HomeView);
    }
});

new HomeRouter();
