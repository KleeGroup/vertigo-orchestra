const opener = require('opener');
const webpackConfig = require('./webpack.config');
const serverLauncher = require('webpack-focus').serverLauncher;
serverLauncher(webpackConfig);
const host = process.env.DEV_SERVER_HOST || 'localhost';
const port = process.env.DEV_SERVER_PORT || 3000;
opener(`http://${host}:${port}`);
