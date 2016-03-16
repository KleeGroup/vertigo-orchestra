import FocusComponents from 'focus-components';
import {translate} from 'focus-core/translation';

export default {
    SelectComponent: FocusComponents.common.select.radio.component,
    refContainer: {yesNoList: [{code: true, label: 'select.oui'}, {code: false, label: 'select.non'}]},
    listName: 'yesNoList',
    formatter: translate
};
