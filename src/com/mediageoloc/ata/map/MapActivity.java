package com.mediageoloc.ata.map;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.BaseColumns;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mediageoloc.ata.R;
import com.mediageoloc.ata.drawer.DrawerActivity;
import com.mediageoloc.ata.user.UsersProvider;
import com.mediageoloc.ata.utils.MediaGeolocContract.Medias;

public class MapActivity extends DrawerActivity implements
		LoaderCallbacks<Cursor>, GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener, GoogleMap.OnMapLoadedCallback {
	private GoogleMap mMap;
	private MapFragment fragment;

	private Double minLat=0.0;
	private Double maxLat=0.0;
	private Double minLng=0.0;
	private Double maxLng=0.0;
	private LatLngBounds.Builder builder;
	private CameraPosition cameraPosition;
	private Integer lastNbMarker;
	
	LocationClient mLocationClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setDrawerContentView(R.layout.activity_map);
		fragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
		mLocationClient = new LocationClient(this, this, this);
		getLoaderManager();
	    LoaderManager.enableDebugLogging(true);
		
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
		mMap.setOnMapLoadedCallback(this);
		getLoaderManager().initLoader(4, null, this);
	}

	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
	   super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedState) {
	   super.onRestoreInstanceState(savedState);
	   
	}

	
	/**
	 * mapMediasToMarkers
	 * @param cursor
	 * for every media found in db create a marker on map
	 * and prepare value for automatique zoom
	 */
	private void mapMediasToMarkers(Cursor cursor) {	
		Marker marker;
		String comment;
		
		cursor.moveToFirst();
		lastNbMarker=0;
		minLat=maxLat=minLng=maxLng=0.0;
		
		builder = new LatLngBounds.Builder();

		while (!cursor.isAfterLast()) {
			if (minLat>cursor.getDouble(cursor.getColumnIndexOrThrow(Medias.COLUMN_NAME_LATITUDE)))
				minLat=cursor.getDouble(cursor.getColumnIndexOrThrow(Medias.COLUMN_NAME_LATITUDE));
			if (maxLat<cursor.getDouble(cursor.getColumnIndexOrThrow(Medias.COLUMN_NAME_LATITUDE)))
				maxLat=cursor.getDouble(cursor.getColumnIndexOrThrow(Medias.COLUMN_NAME_LATITUDE));
			if (minLng>cursor.getDouble(cursor.getColumnIndexOrThrow(Medias.COLUMN_NAME_LONGITUDE)))
				minLng=cursor.getDouble(cursor.getColumnIndexOrThrow(Medias.COLUMN_NAME_LONGITUDE));
			if (maxLng<cursor.getDouble(cursor.getColumnIndexOrThrow(Medias.COLUMN_NAME_LONGITUDE)))
				maxLng=cursor.getDouble(cursor.getColumnIndexOrThrow(Medias.COLUMN_NAME_LONGITUDE));
			
			comment = cursor.getString(cursor.getColumnIndexOrThrow(Medias.COLUMN_NAME_COMMENTAIRE));
			marker = mMap.addMarker(new MarkerOptions().position(
					new LatLng(cursor.getDouble(cursor.getColumnIndexOrThrow(Medias.COLUMN_NAME_LATITUDE)), 
							cursor.getDouble(cursor.getColumnIndexOrThrow(Medias.COLUMN_NAME_LONGITUDE))))
					.title(comment));
		 
			builder.include(new LatLng(cursor.getDouble(cursor.getColumnIndexOrThrow(Medias.COLUMN_NAME_LATITUDE)), 
					cursor.getDouble(cursor.getColumnIndexOrThrow(Medias.COLUMN_NAME_LONGITUDE))));
			lastNbMarker++;
			cursor.moveToNext();
			
		}
		
	}

	/**
	 * onMapLoaded:
	 * 	Apply calculated zoom only if no personnal zoom
	 */
	@Override
	public void onMapLoaded() {
		// TODO Auto-generated method stub
		if (lastNbMarker>0){
			LatLngBounds bounds = builder.build();
			int padding = 20; // offset from edges of the map in pixels
			CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
			mMap.moveCamera(cu);
		}
	}
	
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		String[] projection = {
			    BaseColumns._ID,
			    Medias.COLUMN_NAME_CHEMINFICHIER,
			    Medias.COLUMN_NAME_COMMENTAIRE,
			    Medias.COLUMN_NAME_LATITUDE,
			    Medias.COLUMN_NAME_LONGITUDE,
			    Medias.COLUMN_NAME_TYPEMEDIA
			    };
		//return new CursorLoader(this,UsersProvider.CONTENT_URI_MEDIAS, projection, null, null, null, "_ID DESC LIMIT 10");
		return new CursorLoader(this,UsersProvider.CONTENT_URI_MEDIAS, projection, null, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		mapMediasToMarkers(data);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
	}

	@Override
	public void onConnected(Bundle connectionHint) {
	}

	@Override
	public void onDisconnected() {
	}
	
	@Override
	protected void onDestroy() {
		MapPrefsManager.setZoomMapPrefs(mMap.getMinZoomLevel(), getApplicationContext());
		super.onDestroy();
	}


}
