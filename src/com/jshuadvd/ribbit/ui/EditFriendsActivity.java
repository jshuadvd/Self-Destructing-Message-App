package com.jshuadvd.ribbit.ui;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jshuadvd.ribbit.R;
import com.jshuadvd.ribbit.adapters.UserAdapter;
import com.jshuadvd.ribbit.utilities.ParseConstants;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class EditFriendsActivity extends Activity {
	
	public static final String TAG = EditFriendsActivity.class.getSimpleName();
	
	protected List<ParseUser> mUsers;
	protected ParseRelation<ParseUser> mFriendsRelation;
	protected ParseUser mCurrentUser;
	protected GridView mGridView;
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.user_grid);
		
		mGridView = (GridView)findViewById(R.id.friendsGrid); 
		mGridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE);
		mGridView.setOnItemClickListener(mOnItemClickListener);
		

		TextView emptyTextView = (TextView)findViewById(android.R.id.empty);
		mGridView.setEmptyView(emptyTextView);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		mCurrentUser = ParseUser.getCurrentUser();
		mFriendsRelation = mCurrentUser.getRelation(ParseConstants.KEY_FRIENDS_RELATION);
		
		setProgressBarIndeterminateVisibility(true);
		
		ParseQuery<ParseUser> query = ParseUser.getQuery();
		query.orderByAscending(ParseConstants.KEY_USERNAME);
		query.setLimit(1000);
		query.findInBackground(new FindCallback<ParseUser>() {
			
			@Override
			public void done(List<ParseUser> users, ParseException e) {
				setProgressBarIndeterminateVisibility(false);
				
				if(e == null) {
					// Success!
					mUsers = users;
					String[] usernames = new String[mUsers.size()]; 
					int i = 0;
					for(ParseUser user : mUsers) {
						usernames[i] = user.getUsername();
						i++;
					}
					
					if (mGridView.getAdapter() == null) {
						UserAdapter adapter = new UserAdapter(EditFriendsActivity.this, mUsers);
						mGridView.setAdapter(adapter);
						
					}
					else {
						((UserAdapter)mGridView.getAdapter()).refill(mUsers);
					
					}
					
					addFriendCheckmarks(); 
				}
				else {
					Log.e(TAG, e.getMessage());
					AlertDialog.Builder builder = new AlertDialog.Builder(EditFriendsActivity.this);
					builder.setMessage(e.getMessage())
						   .setTitle(R.string.error_title)
						   .setPositiveButton(android.R.string.ok, null);
					AlertDialog dialog = builder.create();
					dialog.show();
				}
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	
	private void addFriendCheckmarks() {
		mFriendsRelation.getQuery().findInBackground(new FindCallback<ParseUser>() {

			@Override
			public void done(List<ParseUser> friends, ParseException e) {
				if (e == null) {
					// list returned - look for a match
					for (int i = 0; i < mUsers.size(); i++) {
						ParseUser user = mUsers.get(i);				
						
					for(ParseUser friend : friends) {
						if (friend.getObjectId().equals(user.getObjectId())) {
								mGridView.setItemChecked(i, true);
							}
							
						 }
					 }
				}
				else {
					Log.e(TAG, e.getMessage());
				}
			}
		});
	}
	
	protected OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			
			ImageView checkImageView = (ImageView)view.findViewById(R.id.checkImageView); 		
			
			// If "check" is added we add the friend locally
			
			if(mGridView.isItemChecked(position)) {
				// add friend
				mFriendsRelation.add(mUsers.get(position));
				checkImageView.setVisibility(View.VISIBLE);
				mCurrentUser.saveInBackground(new SaveCallback() {
					
					@Override
					public void done(ParseException e) {
						if(e != null) {
							Log.e(TAG, e.getMessage());
						}			
					}
				});
			}	
			
			// If "Check" is removed, we remove them from the relation then below we save that instance to the back end
			else {
				// remove friend
				mFriendsRelation.remove(mUsers.get(position));
				checkImageView.setVisibility(View.INVISIBLE);
				
				// Now we save the friend to the backend
				
				mCurrentUser.saveInBackground(new SaveCallback() {
					
					@Override
					public void done(ParseException e) {
						if(e != null) {
							Log.e(TAG, e.getMessage());
						}			
					}
				});
			}
		}
	};
	
	
}
