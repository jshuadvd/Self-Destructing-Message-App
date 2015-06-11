package com.jshuadvd.ribbit;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.parse.ParseAnalytics;
import com.parse.ParseUser;

public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {
	
	public static final String TAG = MainActivity.class.getSimpleName();
	
	
	// CAnstant value member variables to reference
	public static final int TAKE_PHOTO_REQUEST = 0;
	public static final int TAKE_VIDEO_REQUEST = 1;
	public static final int PICK_PHOTO_REQUEST = 2;
	public static final int PICK_VIDEO_REQUEST = 3;
	
	public static final int MEDIA_TYPE_IMAGE = 4;
	public static final int MEDIA_TYPE_VIDEO = 5;

	// Uniform resource identifier
	protected Uri mMediaUri;
	
	protected DialogInterface.OnClickListener mDialogListener = 
			new DialogInterface.OnClickListener() {		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			
			switch(which) {
			case 0: // Take a Picture
				Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				mMediaUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
				
				if(mMediaUri == null) {
					// Display error
					Toast.makeText(MainActivity.this, R.string.error_externl_storage , 
							Toast.LENGTH_LONG).show();;
				}
				
				else {
					// Adding extra data to a intent with putExtra
					takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, mMediaUri);				
					startActivityForResult(takePhotoIntent, TAKE_PHOTO_REQUEST);
				}
				break;
			case 1: // Take Video
				break;
			case 2: // Choose Picture
				break;
			case 3: // Choose Video
				break;
			
			}
			
		}

		private Uri getOutputMediaFileUri(int mediaTypeImage) {
			if(isExternalStorageAvailable()) {
				// get Uri
				
				// 1. Get external storage directory	
				String appName = MainActivity.this.getString(R.string.app_name);
				File mediaStorageDir = new File(
						Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
						appName);
				
				// 2. Create Sub directory
				if(! mediaStorageDir.exists()) {
					if(! mediaStorageDir.mkdirs()) {
						Log.e(TAG, "Failed to create directory.");
						return null;
					}
 				}
				// 3. Create a File name
				// 4. Create the file
				File mediaFile;
				Date now = new Date();
				String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(now); 
				
				String path = mediaStorageDir.getPath() + File.separator;
				if(mediaType == MEDIA_TYPE_IMAGE) {
					mediaFile = new File(path + "IMG_" + timestamp + ".jpg");
				}
				else if(mediaType == MEDIA_TYPE_VIDEO) {
					mediaFile = new FIle(path + "VID_" + timestamp + ".mp4");
				}
				else {
					return null;
				}
					
				
				// 5. Return the files Uri
				
				return null;
			}
			else {
			
				return null;
			}
		}
		private boolean isExternalStorageAvailable() {
			String state = Environment.getExternalStorageState();
			
			// If external storage is available
			if(state.equals(Environment.MEDIA_MOUNTED)) {
				return true;
			}
			else {
				return false;
			}
		}
	};

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_main);
		
		ParseAnalytics.trackAppOpened(getIntent());
		
		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser == null) {
			navigateToLogin();
		}
		else {
			Log.i(TAG, currentUser.getUsername());
		}

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(this, 
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	private void navigateToLogin() {
		Intent intent = new Intent(this, LoginActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		
		switch(itemId) {
		case R.id.action_logout:
			ParseUser.logOut();
			navigateToLogin();
		
		case R.id.action_edit_friends:
			Intent intent = new Intent(this, EditFriendsActivity.class);
			startActivity(intent);
			
		case R.id.action_camera:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setItems(R.array.camera_choices, mDialogListener);
			AlertDialog dialog = builder.create();
			dialog.show();
		}
			
		
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}


//
//	/**
//	 * A placeholder fragment containing a simple view.
//	 */
//	public static class PlaceholderFragment extends Fragment {
//		/**
//		 * The fragment argument representing the section number for this
//		 * fragment.
//		 */
//		private static final String ARG_SECTION_NUMBER = "section_number";
//
//		/**
//		 * Returns a new instance of this fragment for the given section number.
//		 */
//		public static PlaceholderFragment newInstance(int sectionNumber) {
//			PlaceholderFragment fragment = new PlaceholderFragment();
//			Bundle args = new Bundle();
//			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//			fragment.setArguments(args);
//			return fragment;
//		}
//
//		public PlaceholderFragment() {
//		}
//
//
//	}

}
