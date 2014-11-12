package com.mediageoloc.ata.map;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mediageoloc.ata.R;
import com.mediageoloc.ata.drawer.DrawerActivity;
import com.mediageoloc.ata.user.GitHubService;
import com.mediageoloc.ata.user.UsersProvider;
import com.mediageoloc.ata.utils.MediaGeolocContract.Users;

public class MapActivity extends DrawerActivity implements LoaderCallbacks<Cursor>{
	private GoogleMap mMap;
	private MapFragment fragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		checkGooglePlayServicesAvailable();
	}
	
	/*
	 * check if google play services are available
	 * if not redirect user to play store
	 * else launch map
	 */
	private void checkGooglePlayServicesAvailable() {
		int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		  if (status != ConnectionResult.SUCCESS) {
		    if (GooglePlayServicesUtil.isUserRecoverableError(status)) {
		      GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
		    } else {
		      Toast.makeText(this, R.string.google_services_unavailable, 
		          Toast.LENGTH_LONG).show();
		      finish();
		    }
		}
	    else{
	    	fragment = (MapFragment)getFragmentManager().findFragmentById(R.id.map);
	    }
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
				UsersProvider.CONTENT_URI, projection, null, null, null);
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
