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
		
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.message_item, null);
			
			// Initialize holder as new ViewHolder
			holder = new ViewHolder();
			holder.iconImageView = (ImageView)convertView.findViewById(R.id.messageIcon) ;
			holder.nameLabel = (TextView)convertView.findViewById(R.id.senderLabel);
		}
		else {
			holder = (ViewHolder).convertView.getTag();
		}
		
		ParseObject message = mMessages.get(position);
		holder.iconImageView.setImageResource(R.drawable.ic_action_picture);
		
			return convertView;
		
	}
	
	private static class ViewHolder {
		ImageView iconImageView;
		TextView nameLabel;
	}

}
