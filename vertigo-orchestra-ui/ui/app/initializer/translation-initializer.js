import {translate, init} from 'focus-core/translation';
import traductionFiles from '../i18n';

// Initialize translations configuration.
const i18nConfig = {
    resStore: traductionFiles,
    lng: 'fr-FR'///langOpts.i18nCulture
};

//In production, fallback is english.
/*if (config.env === "production") {
_.extend(i18nConfig, {
fallbackLng: 'en-GB'
});
}*/

//Ajax culture into headers.
/*$.ajaxSetup({
headers: {
'CultureCode': culture
}
});*/

// Plugin initialization.
init(i18nConfig, () => {
    return console.log('Translation correctly initialized.');
});
