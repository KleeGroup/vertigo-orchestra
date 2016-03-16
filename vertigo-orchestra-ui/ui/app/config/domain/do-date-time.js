import moment from 'moment';
import FocusComponents from 'focus-components';

export default {
    InputComponent: FocusComponents.components.input.Date,
    formatter: date => date ? moment(date, moment.ISO_8601).format('DD/MM/YYYY hh:mm a') : '',
    format: ['DD/MM/YYYY', 'DD-MM-YYYY', 'D MMM YYYY']
};
