package com.mediageoloc.ata;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mediageoloc.ata.drawer.DrawerActivity;

public class MapTestActivity extends DrawerActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_test);
        
        MapFragment monFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapTest);
        
        GoogleMap mMap = monFragment.getMap();
        
        UiSettings uiSetting = mMap.getUiSettings();
        uiSetting.setCompassEnabled(true);
        
        LatLng SUDFRANCE = new LatLng(43.6196, 3.9129);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SUDFRANCE, 10.0f));

        Marker marker = mMap.addMarker(new MarkerOptions()
        .position(SUDFRANCE)
        .title("Ou cela ?")
        .draggable(true)
        		);
        mMap.setMapType(2);

        //marker.setDraggable(false);
        //marker.setAnchor(43, 10);
        // Properties are: Position (required), Anchor, Alpha, Title, Snippet, Icon (use BitmapDescriptor), Draggable, Visible, Flat or Billboard orientation, Rotation
        
  
        
    }
    


    
    
    
    
    
}
