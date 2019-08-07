var exec = require('cordova/exec'),
	PLUGIN_NAME = 'GecWalletPlugin',  
	pluginNativeMethod = {
		GENERATE: 'generate',
		GENERATE_BY_PK: 'generateByPk',
		LOAD: 'load',
	};

function GecWalletPlugin() {
	
}

GecWalletPlugin.prototype.generate = function (mnemonic, password, success, error) {
    exec(success, error, PLUGIN_NAME, pluginNativeMethod.GENERATE, [mnemonic, password]);
};

GecWalletPlugin.prototype.generateByPk = function (pk, password, success, error) {
    exec(success, error, PLUGIN_NAME, pluginNativeMethod.GENERATE_BY_PK, [pk, password]);
};

GecWalletPlugin.prototype.load = function (keystore, password, success, error) {
	exec(success, error, PLUGIN_NAME, pluginNativeMethod.LOAD, [keystore, password]);
};

var gecWalletPlugin = new GecWalletPlugin();
module.exports = gecWalletPlugin;