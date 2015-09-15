/*global Focus */
//Dependencies.
let Router = Focus.router;

let SearchRouter = Router.extend({
    log: true,
    beforeRoute(){
        Focus.application.changeRoute('search');
    },
    routes: {
    }
});

module.exports = new SearchRouter();
