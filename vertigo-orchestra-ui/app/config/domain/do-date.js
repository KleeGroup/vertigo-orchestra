/* global moment, Focus */
module.exports = {
    type: 'text',
    style: 'date right',
    InputComponent: Focus.components.common.input.date.component,
    formatter: function dateFormatter(date){
        return moment(date).format('L');
    },
    unformatter: function dateUnformatter(data){
        return moment(data).toDate();
    }
};
