package com.gec.wallet;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * This class echoes a string called from JavaScript.
 */
public class GecWalletPlugin extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("generate")) {
        	String mnemonic = args.getString(0);
        	String password = args.getString(1);
        	this.generate(mnemonic, password, callbackContext);
        	return true;
        }
        if (action.equals("generateByPk")) {
        	String pk = args.getString(0);
        	String password = args.getString(1);
        	this.generateByPk(pk, password, callbackContext);
        	return true;
        }
        if (action.equals("load")) {
        	String keystore = args.getString(0);
        	String password = args.getString(1);
        	this.load(keystore, password, callbackContext);
        	return true;
        }
        return false;
    }

    private void generate(String mnemonic, String password, CallbackContext callbackContext) {
    	if (GecWalletPlugin.isNotEmpty(password) && GecWalletPlugin.isNotEmpty(mnemonic)) {
    		String wallet = GecWalletUtils.generate(mnemonic, password);
    		if (GecWalletPlugin.isNotEmpty(wallet)) {
    			callbackContext.success(wallet);
    		} else {
    			callbackContext.error("generate failed");
    		}
    	} else {
    		callbackContext.error("Expected two non-empty string argument");
    	}
    }
    
	private void generateByPk(String pk, String password, CallbackContext callbackContext) {
		if (GecWalletPlugin.isNotEmpty(password) && GecWalletPlugin.isNotEmpty(pk)) {
			String wallet = GecWalletUtils.generateByPk(pk, password);
			if (GecWalletPlugin.isNotEmpty(wallet)) {
				callbackContext.success(wallet);
			} else {
				callbackContext.error("generate failed");
			}
		} else {
			callbackContext.error("Expected two non-empty string argument");
		}
	}


	private void load(String keystore, String password, CallbackContext callbackContext) {
		if (GecWalletPlugin.isNotEmpty(keystore) && GecWalletPlugin.isNotEmpty(password)) {

			String pk = GecWalletUtils.load(keystore, password);
			if(GecWalletPlugin.isNotEmpty(pk)) {
				callbackContext.success(pk);
			} else {
				callbackContext.error("load failed");
			}
		} else {
			callbackContext.error("Expected two non-empty string argument");
		}
	}

	private static boolean isNotEmpty(String str) {
		return str != null && str.trim().length() > 0;
	}
}
