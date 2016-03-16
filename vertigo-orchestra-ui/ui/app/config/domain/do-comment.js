import FocusComponents from 'focus-components';

export default {
    type: 'text',
    InputComponent: FocusComponents.components.input.Textarea,
    validator: [{
        type: 'string',
        options: {
            maxLength: 5000
        }
    }]
};
