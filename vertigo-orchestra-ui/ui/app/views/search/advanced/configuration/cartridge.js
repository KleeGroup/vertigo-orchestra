import CartridgePageSearch from 'focus-components/page/search/search-header/cartridge';
import SummaryPageSearch from 'focus-components/page/search/search-header/summary';
import service from '../../../../services/search';

export default function cartridgeConfiguration() {
    return {
        summary: {
            component: SummaryPageSearch,
            props: { service }
        },
        cartridge: {
            component: CartridgePageSearch,
            props: { service }
        },
        actions: {
            primary: [],
            secondary: []
        }
    };
}
