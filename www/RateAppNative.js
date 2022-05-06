var exec = require('cordova/exec');

exports.rate = function (success, error) {
    exec(success, error, 'RateAppNative', 'rate', []);
};
