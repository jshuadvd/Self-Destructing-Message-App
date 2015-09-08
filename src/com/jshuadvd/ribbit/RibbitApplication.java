//package com.jshuadvd.ribbit;
//
//import android.app.Application;
//
//import com.jshuadvd.ribbit.ui.MainActivity;
//import com.jshuadvd.ribbit.utils.ParseConstants;
//import com.parse.Parse;
//import com.parse.ParseInstallation;
//import com.parse.ParseUser;
//import com.parse.PushService;
//
//public class RibbitApplication extends Application {
//	
//	@Override
//	public void onCreate() { 
//		super.onCreate();
//	    
//		Parse.initialize(this, "5mxmuCNaVB2ua1mrQRVmsNIDUg8KfcbYrlzzrulh", 
//	    						"tEpFDQp3SneukuBDXXem6GID6Tx56fJe83ZZjEMt");   
//	    
//		//PushService.setDefaultPushCallback(this, MainActivity.class);
//	    PushService.setDefaultPushCallback(this, MainActivity.class, 
//	    		R.drawable.ic_stat_ic_launcher);
//	    ParseInstallation.getCurrentInstallation().saveInBackground();
//	}
//	
//	public static void updateParseInstallation(ParseUser user) {
//		ParseInstallation installation = ParseInstallation.getCurrentInstallation();
//		installation.put(ParseConstants.KEY_USER_ID, user.getObjectId());
//		installation.saveInBackground();
//	}
//}


package com.jshuadvd.ribbit;

import android.app.Application;

import com.jshuadvd.ribbit.ui.MainActivity;
import com.jshuadvd.ribbit.utils.ParseConstants;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.PushService;

public class RibbitApplication extends Application {
	
	@Override
	public void onCreate() { 
		super.onCreate();
	    Parse.initialize(this, 
	    		"5mxmuCNaVB2ua1mrQRVmsNIDUg8KfcbYrlzzrulh", 
	    		"tEpFDQp3SneukuBDXXem6GID6Tx56fJe83ZZjEMt");
	    
	    PushService.setDefaultPushCallback(this, MainActivity.class);
//	    PushService.setDefaultPushCallback(this, MainActivity.class, 
//	    		R.drawable.ic_stat_ic_launcher);
	    ParseInstallation.getCurrentInstallation().saveInBackground();
	}
	
	public static void updateParseInstallation(ParseUser user) {
		ParseInstallation installation = ParseInstallation.getCurrentInstallation();
		installation.put(ParseConstants.KEY_USER_ID, user.getObjectId());
		installation.saveInBackground();
	}
}