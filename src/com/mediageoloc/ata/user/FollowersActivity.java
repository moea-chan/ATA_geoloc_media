package com.mediageoloc.ata.user;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.mediageoloc.ata.R;
import com.mediageoloc.ata.utils.GitHubService;
import com.mediageoloc.ata.utils.MediaGeolocContract.Users;

public class FollowersActivity extends Activity {

	@InjectView(R.id.followers_list)
	ListView followersViewList;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_followers);
		
		ButterKnife.inject(this);
		
		// init github service to get all users
		GitHubService service = new GitHubService();
		service.getUsers(getApplicationContext());
		
		String[] projection = {
			    Users._ID,
			    Users.COLUMN_NAME_NOM,
			    Users.COLUMN_NAME_PRENOM,
			    Users.COLUMN_NAME_MAIL,
			    Users.COLUMN_NAME_TELEPHONE
			    };
		
		String[] fromColumns = new String[] { Users.COLUMN_NAME_PRENOM, Users.COLUMN_NAME_TELEPHONE };
		int[] toControlIds = new int[] { R.id.follower_item, R.id.follower_picture };

		CursorLoader loader = new CursorLoader(this,
		   UserProvider.CONTENT_URI, projection, null, null, null);
		Cursor cursor = loader.loadInBackground();
		// create a SimpleCursorAdapter
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.activity_follower_item, cursor, fromColumns, toControlIds);
		followersViewList.setAdapter(adapter);
	}
	
	
}
