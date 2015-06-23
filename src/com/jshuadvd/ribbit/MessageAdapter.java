package com.jshuadvd.ribbit;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;

public class MessageAdapter extends ArrayAdapter<ParseObject> {
	
	protected Context mContext;
	protected List<ParseObject> mMessages;
	
	public MessageAdapter(Context context, List<ParseObject> messages) {
		super(context, R.layout.message_item, messages);
		mContext = context;
		mMessages = messages;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		
		convertView = LayoutInflater.from(mContext).inflate(R.layout.message_item, null);
		
		// Initialize holder as new ViewHolder
		holder = new ViewHolder();
		holder.iconImageView = (ImageView)convertView.findViewById(R.id.messageIcon) ;
		holder.nameLabel = (TextView)convertView.findViewById(R.id.senderLabel);
		return convertView;
	}
	
	private static class ViewHolder {
		ImageView iconImageView;
		TextView nameLabel;
	}

}
