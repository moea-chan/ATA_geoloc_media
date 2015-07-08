package com.mediageoloc.ata.user.follower;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.etsy.android.grid.StaggeredGridView;
import com.mediageoloc.ata.R;
import com.mediageoloc.ata.drawer.DrawerActivity;
import com.mediageoloc.ata.utils.contentProvider.GeolocProvider;
import com.mediageoloc.ata.utils.contentProvider.MediaGeolocContract.Users;

public class FollowersActivity extends DrawerActivity implements LoaderCallbacks<Cursor> {
	
	@InjectView(R.id.followers_list)
	StaggeredGridView followersViewList;
	
	
	FollowerSimpleAdapter adapter;
	int currentUserId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setDrawerContentView(R.layout.activity_followers);
		
		ButterKnife.inject(this);
	
		getLoaderManager().initLoader(0, null, this);
	}
	
	
	
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		String[] projection = {
			    BaseColumns._ID,
			    Users.COLUMN_NAME_PRENOM,
			    Users.COLUMN_NAME_TELEPHONE
			    };
		return new CursorLoader(this,
				   GeolocProvider.FOLLOWERS_CONTENT_URI, projection, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		String[] fromColumns = new String[] { Users.COLUMN_NAME_PRENOM, Users.COLUMN_NAME_TELEPHONE };
		int[] toControlIds = new int[] { R.id.follower_item, R.id.follower_picture };

		adapter = new FollowerSimpleAdapter(
				getApplicationContext(), 
				R.layout.follower_item, 
				data, 
				fromColumns, 
				toControlIds,
				0
				);
		
		followersViewList.setAdapter(adapter);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {}
	
	
}

