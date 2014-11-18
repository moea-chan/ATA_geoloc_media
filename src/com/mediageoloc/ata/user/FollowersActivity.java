package com.mediageoloc.ata.user;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.mediageoloc.ata.R;
import com.mediageoloc.ata.drawer.DrawerActivity;
import com.mediageoloc.ata.utils.MediaGeolocContract.Users;

public class FollowersActivity extends DrawerActivity implements LoaderCallbacks<Cursor> {

	@InjectView(R.id.followers_list)
	ListView followersViewList;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setDrawerContentView(R.layout.activity_followers);
		
		ButterKnife.inject(this);
	
		// init github service to get all users
		//GitHubService service = new GitHubService();
		//service.getUsers(getApplicationContext());
		
		getLoaderManager().initLoader(0, null, this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return new CursorLoader(this,
				   UsersProvider.FOLLOWERS_CONTENT_URI, null, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		String[] fromColumns = new String[] { Users.COLUMN_NAME_PRENOM, Users.COLUMN_NAME_TELEPHONE };
		int[] toControlIds = new int[] { R.id.user_item, R.id.user_picture };

		UserSimpleAdapter adapter = new UserSimpleAdapter(getApplicationContext(), R.layout.user_item, data, fromColumns, toControlIds, 0);
		followersViewList.setAdapter(adapter);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {}
	
	
}

