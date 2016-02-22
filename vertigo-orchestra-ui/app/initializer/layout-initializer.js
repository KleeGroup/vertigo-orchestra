let render = Focus.application.render;
let Layout = Focus.components.application.layout.component;
let MenuLeft = require('../views/menu');

render(Layout, 'body', {
    props: {MenuLeft}
});
