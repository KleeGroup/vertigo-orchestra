// Components
let QuickSearch = Focus.components.search.searchBar.component;

// Mixins
let i18nMixin = Focus.components.common.i18n.mixin;

module.exports = React.createClass({
    displayName: 'cartridge-search',
    mixins: [i18nMixin],
    render() {
        return (
            <div className='cartridge-search'>
                <h1>{this.i18n('search.cartridge.title')}</h1>
                <QuickSearch />
            </div>
        );
    }
});
