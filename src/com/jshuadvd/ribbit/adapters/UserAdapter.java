package com.jshuadvd.ribbit.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jshuadvd.ribbit.R;
import com.jshuadvd.ribbit.utilities.MD5Util;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

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
			holder.checkImageView = (ImageView)convertView.findViewById(R.id.checkImageView);
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
			String gravatarUrl = "http://www.gravatar.com/avatar/" + hash + 
					"?s=204&d=404";
			
			//Log.d("Test", gravatarUrl);
			Picasso.with(mContext)
			.load(gravatarUrl)
			.placeholder(R.drawable.avatar_empty)
		    .into(holder.userImageView);
		}
		

		holder.nameLabel.setText(user.getUsername());
		
		GridView gridView = (GridView)parent;
		if (gridView.isItemChecked(position)) {
			holder.checkImageView.setVisibility(View.VISIBLE);
		}
		
		return convertView;
		
	}
	
	private static class ViewHolder {
		ImageView userImageView;
		ImageView checkImageView;
		TextView nameLabel;
		//TextView timeLabel;
	}
	
	public void refill(List<ParseUser> users) {
		mUsers.clear();
		mUsers.addAll(users);
		notifyDataSetChanged();
	
	}

}
