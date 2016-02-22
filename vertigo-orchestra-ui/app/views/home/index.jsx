// Mixins

let cartridgeBehaviour = Focus.components.page.mixin.cartridgeBehaviour;

// Service

let service = require('../../services/search');

// Composants du cartouche

let ApplicationTitle = React.createClass({
    render() {
        return (
            <span className="page-title">FOCUS</span>
        );
    }
});

let initializationCallsCount = 4;

let navigateAdvancedSearch = function () {
    if (initializationCallsCount === 0) {
        let route = '#search/advanced';
        Backbone.history.navigate(route, true);
    } else {
        initializationCallsCount--;
    }
};

//Creates a View for hehe home page which is
let HomeView = React.createClass({
    mixins: [cartridgeBehaviour],
    cartridgeConfiguration() {
        let buildProps = {
            service,
            onSearchCriteriaChange: navigateAdvancedSearch
        };
        return {
            barLeft: {component: ApplicationTitle},
            summary: {
                component: Focus.components.page.search.searchHeader.summary,
                props: buildProps
            },
            cartridge: {
                component: Focus.components.page.search.searchHeader.cartridge,
                props: buildProps
            },
            actions: {
                primary: [],
                secondary: []
            }
        };
    },
    render() {
        return (
            <div className="homepage"></div>
        );
    }
});
module.exports = HomeView;
