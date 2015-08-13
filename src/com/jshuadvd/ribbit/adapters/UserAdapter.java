package com.jshuadvd.ribbit.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jshuadvd.ribbit.R;
import com.jshuadvd.ribbit.utilities.MD5Util;
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
			convertView = LayoutInflater.from(mContext).inflate(R.layout.user_item, null);
			
			// Initialize holder as new ViewHolder
			holder = new ViewHolder();
			holder.userImageView = (ImageView)convertView.findViewById(R.id.userImageView) ;
			holder.nameLabel = (TextView)convertView.findViewById(R.id.nameLabel);
			//holder.timeLabel = (TextView)convertView.findViewById(R.id.timeLabel);
			convertView.setTag(holder);			
			
		}
		else {
			holder = (ViewHolder)convertView.getTag();
			
		}
	
		ParseUser user = mUsers.get(position);
		String email = user.getEmail().toLowerCase();
		
		if (email.equals("")) {
			holder.userImageView.setImageResource(R.drawable.avatar_empty);
		}
		else {
			String hash = MD5Util.md5Hex(email);
		}
		
//		if (user.getString(ParseConstants.KEY_FILE_TYPE).equals(ParseConstants.TYPE_IMAGE)) {
//			holder.iconImageView.setImageResource(R.drawable.ic_picture);
//		}
//		else {
//			holder.iconImageView.setImageResource(R.drawable.ic_video);
//		}
		holder.nameLabel.setText(user.getUsername());
		
		// Commit when reopened
		
		return convertView;
		
	}
	
	private static class ViewHolder {
		ImageView userImageView;
		TextView nameLabel;
		//TextView timeLabel;
	}
	
	public void refill(List<ParseUser> users) {
		mUsers.clear();
		mUsers.addAll(users);
		notifyDataSetChanged();
	
	}

}
