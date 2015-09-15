//Initialize translations configuration.
let i18nConfig = {
    resStore: require('../i18n'),
    lng: 'en-GB'///langOpts.i18nCulture
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
i18n.init(i18nConfig, function init(content) {
    return console.log('Translation correctly initialized.');
});
