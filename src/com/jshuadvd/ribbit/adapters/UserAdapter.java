package com.jshuadvd.ribbit.adapters;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jshuadvd.ribbit.R;
import com.jshuadvd.ribbit.utilities.ParseConstants;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class UserAdapter extends ArrayAdapter<ParseUser> {
	
	protected Context mContext;
	protected List<ParseUser> mUsers;
	
	public UserAdapter(Context context, List<ParseUser> users) {
		super(context, R.layout.message_item, users);
		mContext = context;
		mUsers = users;
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
			holder.timeLabel = (TextView)convertView.findViewById(R.id.timeLabel);
			convertView.setTag(holder);			
			
		}
		else {
			holder = (ViewHolder)convertView.getTag();
			
		}
	
		ParseObject message = mUsers.get(position);
		
		Date createdAt = message.getCreatedAt();
		long now = new Date().getTime();
		String convertedDate = DateUtils.getRelativeTimeSpanString(
				createdAt.getTime(), 
				now, 
				DateUtils.SECOND_IN_MILLIS).toString();
		
		holder.timeLabel.setText(convertedDate);
		
		if (message.getString(ParseConstants.KEY_FILE_TYPE).equals(ParseConstants.TYPE_IMAGE)) {
			holder.iconImageView.setImageResource(R.drawable.ic_picture);
		}
		else {
			holder.iconImageView.setImageResource(R.drawable.ic_video);
		}
		holder.nameLabel.setText(message.getString(ParseConstants.KEY_SENDER_NAME));
		
		return convertView;
		
	}
	
	private static class ViewHolder {
		ImageView iconImageView;
		TextView nameLabel;
		TextView timeLabel;
	}
	
	public void refill(List<ParseObject> messages) {
		mUsers.clear();
		mUsers.addAll(messages);
		notifyDataSetChanged();
	
	}

}
