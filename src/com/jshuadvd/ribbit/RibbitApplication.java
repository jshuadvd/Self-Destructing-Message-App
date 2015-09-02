package com.jshuadvd.ribbit;

import android.app.Application;

import com.jshuadvd.ribbit.utilities.ParseConstants;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseUser;

public class RibbitApplication extends Application {
	
	@Override
	public void onCreate() { 
		super.onCreate();
	    
		Parse.initialize(this, "5mxmuCNaVB2ua1mrQRVmsNIDUg8KfcbYrlzzrulh", 
	    						"tEpFDQp3SneukuBDXXem6GID6Tx56fJe83ZZjEMt");   
	    
	}
	
	public static void updateParseInstallation(ParseUser user) {
		ParseInstallation installation = ParseInstallation.getCurrentInstallation();
		installation.put(ParseConstants.KEY_USER_ID, user.getObjectId());
	}
}
