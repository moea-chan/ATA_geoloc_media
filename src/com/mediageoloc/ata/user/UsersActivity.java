package com.mediageoloc.ata.user;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.etsy.android.grid.StaggeredGridView;
import com.mediageoloc.ata.R;
import com.mediageoloc.ata.drawer.DrawerActivity;
import com.mediageoloc.ata.utils.MediaGeolocContract.Users;

public class UsersActivity extends DrawerActivity implements LoaderCallbacks<Cursor> {
	
	@InjectView(R.id.users_list)
	StaggeredGridView usersViewList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setDrawerContentView(R.layout.activity_users);
		
		ButterKnife.inject(this);
		usersViewList.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
			}
		});		
		// init github service to get all users
		GitHubService service = new GitHubService();
		service.getUsers(getApplicationContext());
		
		getLoaderManager().initLoader(0, null, this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		String[] projection = {
			    BaseColumns._ID,
			    Users.COLUMN_NAME_NOM,
			    Users.COLUMN_NAME_PRENOM,
			    Users.COLUMN_NAME_MAIL,
			    Users.COLUMN_NAME_TELEPHONE
			    };
		return new CursorLoader(this,
				   UsersProvider.USERS_CONTENT_URI, projection, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		String[] fromColumns = new String[] { Users.COLUMN_NAME_PRENOM, Users.COLUMN_NAME_TELEPHONE };
		int[] toControlIds = new int[] { R.id.user_item, R.id.follower_picture };

		UserSimpleAdapter adapter = new UserSimpleAdapter(
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
