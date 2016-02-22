module.exports = {
    type: 'text',
    decorator: 'datePicker',
    style: 'date right',
    formatter: {
        value: function getValue(data) {
            return data;
        }
    }
};
