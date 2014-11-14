package com.mediageoloc.ata.geoloc;

import android.app.Fragment;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
/**
 * GetGeoLocFragment : This class get last gps position or zero values if not available.
 * @author Thierry
 *
 */
public class GetGeoLocFragment extends Fragment implements LocationListener{
	
	  private Double lastLatitute;
	  private Double lastLongitude;
	  private LocationManager locationManager;
	  private String provider;
	  private Location location;
	  
	  public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);

		     // Get the location manager and get position all 5s
	        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
		    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,-1,GetGeoLocFragment.this);
		    // Define the criteria how to select the location provider -> use default
		    Criteria criteria = new Criteria();
		    provider = locationManager.getBestProvider(criteria, false);
		    location = locationManager.getLastKnownLocation(provider);

		    // Initialize the location
		    if (location != null) {
		      onLocationChanged(location);
		    } else {
		      //System.out.println("Location not available");
		      setLastLatitute(0.0);
		      setLastLongitude(0.0);
		    }
	  }

	   /**
	    * All 5s
	    */
		@Override
		    public void onLocationChanged(Location loc) {
		        setLastLatitute(loc.getLatitude());
		        setLastLongitude(loc.getLongitude());
		    }

		    @Override
		    public void onProviderDisabled(String arg0) {
		        // nothing todo
		    }

		    @Override
		    public void onProviderEnabled(String arg0) {
		        // nothing todo
		    }

		    @Override
		    public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		        // nothing todo
		    }
		@Override
		    public void onResume() {
		        super.onResume();
		        location = locationManager.getLastKnownLocation(provider);
		            // Initialize the location fields
		            if (location != null) {
		              onLocationChanged(location);
		            } else {
		              setLastLatitute(0.0);
				      setLastLongitude(0.0);
		            }       
		}

		/**
		 * All getter / setter
		 * 
		 */
		public Double getLastLatitute() {
			return lastLatitute;
		}

		public void setLastLatitute(Double lastLatitute) {
			this.lastLatitute = lastLatitute;
		}

		public Double getLastLongitude() {
			return lastLongitude;
		}

		public void setLastLongitude(Double lastLongitude) {
			this.lastLongitude = lastLongitude;
		}
	}