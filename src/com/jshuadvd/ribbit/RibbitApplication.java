package com.jshuadvd.ribbit;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class RibbitApplication extends Application {
	
	@Override
	public void onCreate() { 
		super.onCreate();
	    Parse.initialize(this, "5mxmuCNaVB2ua1mrQRVmsNIDUg8KfcbYrlzzrulh", "tEpFDQp3SneukuBDXXem6GID6Tx56fJe83ZZjEMt");
	    
	    
	}
}
