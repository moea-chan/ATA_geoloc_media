package com.mediageoloc.ata.map;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mediageoloc.ata.R;
import com.mediageoloc.ata.drawer.DrawerActivity;
import com.mediageoloc.ata.user.UserProvider;
import com.mediageoloc.ata.utils.GitHubService;
import com.mediageoloc.ata.utils.MediaGeolocContract.Users;

public class MapActivity extends DrawerActivity implements LoaderCallbacks<Cursor>{
	private GoogleMap mMap;
	private MapFragment fragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		fragment = (MapFragment)getFragmentManager().findFragmentById(R.id.map);
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		mMap = fragment.getMap();
		getUsers();
	}
	
	//for every user found in db create a marker on map
	private void mapUsersToMarkers(Cursor cursor) {
		cursor.moveToFirst();
		
		Marker marker;
		String pseudo;
		
		while (!cursor.isAfterLast()) {
			pseudo = cursor.getString(cursor.getColumnIndexOrThrow(Users.COLUMN_NAME_PRENOM));
			marker = mMap.addMarker(new MarkerOptions()
			.position(new LatLng(Math.random()*100, Math.random()*100))
			.title(pseudo));
		    cursor.moveToNext();
		}
		cursor.close();
	}
	
	//get github user and init loader
	private void getUsers() {
		// init github service to get all users
		GitHubService service = new GitHubService();
		service.getUsers(getApplicationContext());
		//get loader or create it
		getLoaderManager().initLoader(0, null, this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		//create loader 
		String[] projection = {Users.COLUMN_NAME_PRENOM};
		return new CursorLoader(this,
				UserProvider.CONTENT_URI, projection, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		mapUsersToMarkers(data);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// TODO Auto-generated method stub
	}
}
