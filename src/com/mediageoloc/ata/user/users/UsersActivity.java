package com.mediageoloc.ata.user.users;

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
import com.mediageoloc.ata.utils.service.GitHubService;

public class UsersActivity extends DrawerActivity implements LoaderCallbacks<Cursor> {
	
	@InjectView(R.id.users_list)
	StaggeredGridView usersViewList;
	
	UserSimpleAdapter adapter;
	int currentUserId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setDrawerContentView(R.layout.activity_users);
		
		ButterKnife.inject(this);
				
		// init github service to get all users
		GitHubService service = new GitHubService();
		service.getUsers(getApplicationContext());
		
		getLoaderManager().initLoader(0, null, this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		String[] projection = {
			    BaseColumns._ID,
			    Users.COLUMN_NAME_PRENOM,
			    Users.COLUMN_NAME_TELEPHONE
			    };
		Loader<Cursor> cursorLoader = null;
		switch (id) {
		//get all users
		case 0:
			return new CursorLoader(this,
					   GeolocProvider.USERS_CONTENT_URI, projection, null, null, null);
		default:
			break;
		}
		return cursorLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		String[] fromColumns = new String[] { Users.COLUMN_NAME_PRENOM, Users.COLUMN_NAME_TELEPHONE };
		int[] toControlIds = new int[] { R.id.user_item, R.id.user_picture };

		adapter = new UserSimpleAdapter(
				getApplicationContext(), 
				R.layout.user_item, 
				data, 
				fromColumns, 
				toControlIds, 0);
		usersViewList.setAdapter(adapter);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {}
	
	
}
