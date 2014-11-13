package com.mediageoloc.ata.historic;

import java.util.ArrayList;
import java.util.List;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.ListView;

import com.mediageoloc.ata.R;
import com.mediageoloc.ata.drawer.DrawerActivity;
import com.mediageoloc.ata.media.StoredMedia;
import com.mediageoloc.ata.user.UsersProvider;
import com.mediageoloc.ata.utils.MediaGeolocContract.Medias;
import com.mediageoloc.ata.utils.MediaGeolocContract.Users;


public class HistoricMediaActivity extends DrawerActivity implements LoaderCallbacks<Cursor> {
	private ListView historicListView;
	private List<StoredMedia> historicList = new ArrayList<StoredMedia>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setDrawerContentView(R.layout.activity_historic_media);
		
		historicListView = (ListView) findViewById(R.id.historic_list);
		/*
		SharedPreferences settings = getSharedPreferences("historiquePreferences", 0);
		String filePath;
		String comment;
		StoredMedia media;
		for (int i = 1 ; i < 11 ; i++) {
			filePath = settings.getString("filePath"+i, "");
			if(filePath != null && filePath != ""){
				comment = settings.getString("commentaire"+i, "");
				media = new StoredMedia(filePath, comment, 0.0, 0.0, "PHOTO");
				historicList.add(media);
			}
			
		}
		
		HistoricAdapter adapterArray = new HistoricAdapter(historicList, getApplicationContext());
		historicListView.setAdapter(adapterArray);
		*/
		getLoaderManager().initLoader(0, null, this);
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
		return new CursorLoader(this,UsersProvider.CONTENT_URI_MEDIAS, projection, null, null, "_ID DESC");
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		
		String filePath;
		String comment;
		Double latitude,longitude;
		String typeMedia;
		
		StoredMedia media;
		
		if(data.moveToFirst())
	    {
	        do
	        {
	        	filePath=data.getString(data.getColumnIndex(Medias.COLUMN_NAME_CHEMINFICHIER));
	        	comment=data.getString(data.getColumnIndex(Medias.COLUMN_NAME_COMMENTAIRE));
	        	latitude=data.getDouble(data.getColumnIndex(Medias.COLUMN_NAME_LATITUDE));
	        	longitude=data.getDouble(data.getColumnIndex(Medias.COLUMN_NAME_LONGITUDE));
	        	typeMedia=data.getString(data.getColumnIndex(Medias.COLUMN_NAME_TYPEMEDIA));
	    	    
	        	media = new StoredMedia(filePath, comment, latitude, longitude, typeMedia);
	        	historicList.add(media);
	        }while(data.moveToNext());
	    }
		
		HistoricAdapter adapterArray = new HistoricAdapter(historicList, getApplicationContext());
		historicListView.setAdapter(adapterArray);		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// ne rien faire
	}
}
