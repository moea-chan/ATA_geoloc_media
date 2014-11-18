package com.mediageoloc.ata.user;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentValues;
import android.content.Context;
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
	Cursor cursor = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setDrawerContentView(R.layout.activity_users);
		
		ButterKnife.inject(this);
		usersViewList.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Context context = getApplicationContext();
				// Create a new map of values, where column names are the keys
				ContentValues values = new ContentValues();
				int index = cursor.getCount()- (int)arg3;
				cursor.move(index);
				//values.put(Users.COLUMN_NAME_NOM, cursor.getString(cursor.getColumnIndex(Users.COLUMN_NAME_NOM)));
				//values.put(Users.COLUMN_NAME_PRENOM, cursor.getString(cursor.getColumnIndex(Users.COLUMN_NAME_PRENOM)));
				//values.put(Users.COLUMN_NAME_MAIL, cursor.getString(cursor.getColumnIndex(Users.COLUMN_NAME_MAIL)));
				//values.put(Users.COLUMN_NAME_TELEPHONE, cursor.getString(cursor.getColumnIndex(Users.COLUMN_NAME_TELEPHONE)));
				values.put(Users.COLUMN_NAME_FOLLOWED, 1);
				
				// Insert, the primary key value of the new row is returned
				context.getContentResolver().update(UsersProvider.FOLLOWERS_CONTENT_URI, values, "_id = ?", new String[]{Integer.toString(index)});
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
		int[] toControlIds = new int[] { R.id.user_item, R.id.user_picture };

		UserSimpleAdapter adapter = new UserSimpleAdapter(getApplicationContext(), R.layout.user_item, data, fromColumns, toControlIds, 0);
		usersViewList.setAdapter(adapter);
		cursor = data;
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {}
	
	
}
