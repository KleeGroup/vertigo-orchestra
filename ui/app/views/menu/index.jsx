// Mixins
let menuMixin = Focus.components.application.menu.mixin;

// Components

let Popin = Focus.components.application.popin.component;

let Menu = React.createClass({
    displayName: 'menu',
    mixins: [menuMixin],
    renderContent() {
        if ('menuLeft' === this.props.type) {
            return this.props.links.map(function (link) {
                if (!link.img) {
                    return <a href={link.url}>link.title</a>;
                } else {
                    return <a href={link.url}><img src={link.img}/></a>;
                }
            });
        }
        return this.renderLinks();
    }
});


let Wrapper = React.createClass({
    displayName: 'menu-wrapper',
    _getItems() {
        return ([
            {
                icon: 'home',
                route: 'home',
                name: 'Home',
                onClick: this._closeQuickSearchPopin
            },
            {
                icon: 'search',
                name: 'Search',
                onClick: this._toggleQuickSearchPopin
            }
        ]);
    },
    _toggleQuickSearchPopin() {

    },
    _closeQuickSearchPopin() {

    },
    render() {
        return (
            <div>
                <Menu
                    direction='vertical'
                    items={this._getItems()}
                    open={true}
                    position='left'
                    ref='menu'
                    title=''
                />
            </div>
        );
    }
});

module.exports = Wrapper;
