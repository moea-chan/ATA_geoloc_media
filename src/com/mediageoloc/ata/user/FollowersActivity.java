package com.mediageoloc.ata.user;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentValues;
import android.content.Context;
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
import com.mediageoloc.ata.utils.SwipeListViewTouchListener;

public class FollowersActivity extends DrawerActivity implements
		LoaderCallbacks<Cursor> {

	@InjectView(R.id.followers_list)
	ListView followersViewList;
	Cursor cursor = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setDrawerContentView(R.layout.activity_followers);

		ButterKnife.inject(this);

		/*
		 * Create a ListView-specific touch listener. ListViews are given
		 * special treatment because by default they handle touches for their
		 * list items... i.e. they're in charge of drawing the pressed state
		 * (the list selector), handling list item clicks, etc.
		 */

		SwipeListViewTouchListener touchListener = new SwipeListViewTouchListener(
				followersViewList,
				new SwipeListViewTouchListener.OnSwipeCallback() {
					@Override
					public void onSwipeLeft(ListView listView,
							int[] reverseSortedPositions) {
						// Log.i(this.getClass().getName(),
						// "swipe left : pos="+reverseSortedPositions[0]);
						// TODO : YOUR CODE HERE FOR LEFT ACTION
					}

					@Override
					public void onSwipeRight(ListView listView,
							int[] reverseSortedPositions) {
						// Log.i(ProfileMenuActivity.class.getClass().getName(),
						// "swipe right : pos="+reverseSortedPositions[0]);
						Context context = getApplicationContext();
						// Create a new map of values, where column names are the keys
						
						int index = reverseSortedPositions[0];
						cursor.move(index);
						
						// Insert, the primary key value of the new row is returned
						context.getContentResolver().delete(UsersProvider.FOLLOWERS_CONTENT_URI, "_id = ?", new String[]{Integer.toString(index)});

					}
				}, true, // example : left action = dismiss
				false); // example : right action without dismiss animation
		
		followersViewList.setOnTouchListener(touchListener);

		/*
		 * Setting this scroll listener is required to ensure that during
		 * ListView scrolling, we don't look for swipes.
		 */
		followersViewList.setOnScrollListener(touchListener
				.makeScrollListener());
		
		getLoaderManager().initLoader(0, null, this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return new CursorLoader(this, UsersProvider.FOLLOWERS_CONTENT_URI,
				null, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		String[] fromColumns = new String[] { Users.COLUMN_NAME_PRENOM,
				Users.COLUMN_NAME_TELEPHONE };
		int[] toControlIds = new int[] { R.id.user_item, R.id.user_picture };

		UserSimpleAdapter adapter = new UserSimpleAdapter(
				getApplicationContext(), R.layout.user_item, data, fromColumns,
				toControlIds, 0);
		followersViewList.setAdapter(adapter);
		cursor = data;
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
	}

}
