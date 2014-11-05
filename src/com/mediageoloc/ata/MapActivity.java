package com.mediageoloc.ata;

import android.app.FragmentTransaction;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.mediageoloc.ata.drawer.DrawerActivity;

public class MapActivity extends DrawerActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		
		GoogleMapOptions options = new GoogleMapOptions();

		options.mapType(GoogleMap.MAP_TYPE_SATELLITE)
		    .compassEnabled(false)
		    .rotateGesturesEnabled(false)
		    .tiltGesturesEnabled(false);

		MapFragment fragment = MapFragment.newInstance(options);

		FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

		fragmentTransaction.add(R.id.map, fragment);

		fragmentTransaction.commit();

	}
}
