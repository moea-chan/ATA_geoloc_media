package com.mediageoloc.ata.map;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.GooglePlayServicesClient;
//import com.google.android.gms.location.LocationClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
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

public class MapActivity extends DrawerActivity implements LocationListener,
		LoaderCallbacks<Cursor>, GoogleApiClient.ConnectionCallbacks,
		GoogleApiClient.OnConnectionFailedListener {
	private GoogleMap mMap;
	private MapFragment fragment;

	GoogleApiClient mLocationClient;
	LocationRequest mLocationRequest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setDrawerContentView(R.layout.activity_map);
		fragment = (MapFragment) getFragmentManager()
				.findFragmentById(R.id.map);

		mLocationClient = new GoogleApiClient.Builder(this)
				.addApi(LocationServices.API).addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this).build();
	}

	/*
	 * Called when the Activity becomes visible.
	 */
	@Override
	protected void onStart() {
		super.onStart();
		// Connect the client.
		mLocationClient.connect();
	}

	/*
	 * Called when the Activity is no longer visible.
	 */
	@Override
	protected void onStop() {
		// Disconnecting the client invalidates it.
		mLocationClient.disconnect();
		super.onStop();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mMap = fragment.getMap();
		getUsers();
	}

	// for every user found in db create a marker on map
	private void mapUsersToMarkers(Cursor cursor) {
		cursor.moveToFirst();

		Marker marker;
		String pseudo;

		while (!cursor.isAfterLast()) {
			pseudo = cursor.getString(cursor
					.getColumnIndexOrThrow(Users.COLUMN_NAME_PRENOM));
			marker = mMap.addMarker(new MarkerOptions().position(
					new LatLng(Math.random() * 100, Math.random() * 100))
					.title(pseudo));
			cursor.moveToNext();
		}
		cursor.close();
	}

	// get github user and init loader
	private void getUsers() {
		// init github service to get all users
		GitHubService service = new GitHubService();
		service.getUsers(getApplicationContext());
		// get loader or create it
		getLoaderManager().initLoader(0, null, this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// create loader
		String[] projection = { Users.COLUMN_NAME_PRENOM };
		return new CursorLoader(this, UsersProvider.USERS_CONTENT_URI,
				projection, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		mapUsersToMarkers(data);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnectionSuspended(int i) {
		// TODO Auto-generated method stub
	}

    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000); // Update location every second

        LocationServices.FusedLocationApi.requestLocationUpdates(
        		mLocationClient, mLocationRequest, this);
    }

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}

}
