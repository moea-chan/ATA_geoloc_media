package com.mediageoloc.ata.historic;

import java.util.ArrayList;
import java.util.List;

import android.content.SharedPreferences;
import android.os.Bundle;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.etsy.android.grid.StaggeredGridView;
import com.mediageoloc.ata.R;
import com.mediageoloc.ata.drawer.DrawerActivity;
import com.mediageoloc.ata.media.StoredMedia;

public class HistoricMediaEtsyActivity extends DrawerActivity  {
//	implements AbsListView.OnScrollListener, AbsListView.OnItemClickListener

    private List<StoredMedia> historicList = new ArrayList<StoredMedia>();
	
    @InjectView(R.id.historic_list)
    StaggeredGridView historicListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDrawerContentView(R.layout.activity_historic_media);

        ButterKnife.inject(this);
        
        HistoricAdapter adapterArray = new HistoricAdapter(historicList, getApplicationContext());
		historicListView.setAdapter(adapterArray);

		SharedPreferences settings = getSharedPreferences(HistoricPrefManager.HISTORIC_PREFS_NAME, 0);
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

        historicListView.setAdapter(adapterArray);
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

    
}
