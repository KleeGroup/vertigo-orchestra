/*global.$ = require('jquery');//(window);
global.Backbone = require('backbone');
Backbone.$ = $;*/
//React tap event initializer.
//let injectTapEventPlugin = require('react-tap-event-plugin');

//Needed for onTouchTap
//Can go away when react 1.0 release
//Check this repo:
//https://github.com/zilverline/react-tap-event-plugin
//injectTapEventPlugin();
$(document).on('click', 'a:not([data-bypass])', function touchHandler(evt) {
    let href = { prop: $(this).prop('href'), attr: $(this).attr('href') };
    let root = location.protocol + '//' + location.host + '/';

    if (href.prop && href.prop.slice(0, root.length) === root) {
        evt.preventDefault();
        Backbone.history.navigate(href.attr, true);
    }
});


//Initialisation des configurations
require('./domain-initializer');
require('./definition-initializer');
require('./reference-list-initializer').initialize();
require('./query-store-initializer').initialize();
require('./i18n-initializer');
require('./user-initializer');

//Initialisation du layout
//
require('./layout-initializer');

$(function initMaterial() {
    $.material.init();
});
