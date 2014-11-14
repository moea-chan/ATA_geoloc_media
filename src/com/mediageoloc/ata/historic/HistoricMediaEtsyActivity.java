package com.mediageoloc.ata.historic;

import java.util.ArrayList;
import java.util.List;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.etsy.android.grid.StaggeredGridView;
import com.mediageoloc.ata.R;
import com.mediageoloc.ata.drawer.DrawerActivity;
import com.mediageoloc.ata.media.StoredMedia;
import com.mediageoloc.ata.user.UsersProvider;
import com.mediageoloc.ata.utils.MediaGeolocContract.Medias;

public class HistoricMediaEtsyActivity extends DrawerActivity implements LoaderCallbacks<Cursor> {
//	implements AbsListView.OnScrollListener, AbsListView.OnItemClickListener

    private List<StoredMedia> historicList = new ArrayList<StoredMedia>();
	
    @InjectView(R.id.historic_list)
    StaggeredGridView historicListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historic_media);

        ButterKnife.inject(this);
        
        HistoricAdapter adapterArray = new HistoricAdapter(historicList, getApplicationContext());
		historicListView.setAdapter(adapterArray);

		/*SharedPreferences settings = getSharedPreferences(HistoricPrefManager.HISTORIC_PREFS_NAME, 0);
		String filePath;
		String comment;
		StoredMedia media;
		
		//get 10 last post
		for (int i = 1 ; i < 11 ; i++) {
			filePath = settings.getString(HistoricPrefManager.FILE_PATH + i, "");
			if(filePath != null && filePath != ""){
				comment = settings.getString(HistoricPrefManager.COMMENT + i, "");
				media = new StoredMedia(filePath, comment);
				historicList.add(media);
			}
		}
*/
		getLoaderManager().initLoader(0, null, this);
//        historicListView.setAdapter(adapterArray);
//        historicListView.setOnScrollListener(this);
//        historicListView.setOnItemClickListener(this);
    }


//    @Override
//    public void onScrollStateChanged(final AbsListView view, final int scrollState) {
//        Log.d(TAG, "onScrollStateChanged:" + scrollState);
//    }
//
//    @Override
//    public void onScroll(final AbsListView view, final int firstVisibleItem, final int visibleItemCount, final int totalItemCount) {
//        Log.d(TAG, "onScroll firstVisibleItem:" + firstVisibleItem +
//                            " visibleItemCount:" + visibleItemCount +
//                            " totalItemCount:" + totalItemCount);
//    }
//
//    @Override
//    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//        Toast.makeText(this, "Item Clicked: " + position, Toast.LENGTH_SHORT).show();
//    }
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
