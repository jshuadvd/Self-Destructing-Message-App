package com.jshuadvd.ribbit;

import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.parse.ParseObject;

public class MessageAdapter extends ArrayAdapter<ParseObject> {
	
	protected Context mContext;
	protected List<ParseObject> mMessages;

}
