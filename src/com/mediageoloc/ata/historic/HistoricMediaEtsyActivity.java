package com.mediageoloc.ata.historic;

import java.util.ArrayList;
import java.util.List;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;
import com.mediageoloc.ata.R;
import com.mediageoloc.ata.drawer.DrawerActivity;
import com.mediageoloc.ata.media.StoredMedia;

public class HistoricMediaEtsyActivity extends DrawerActivity implements AbsListView.OnScrollListener, AbsListView.OnItemClickListener {

    private static final String TAG = "StaggeredGridActivity";
    public static final String SAVED_DATA_KEY = "SAVED_DATA";

    private StaggeredGridView historicListView;
    private boolean mHasRequestedMore;
//    private SampleAdapter mAdapter;

    private List<StoredMedia> historicList = new ArrayList<StoredMedia>();
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historic_media);

        
        historicListView = (StaggeredGridView) findViewById(R.id.historic_list);

        LayoutInflater layoutInflater = getLayoutInflater();

        
       
        
        HistoricAdapter adapterArray = new HistoricAdapter(historicList, getApplicationContext());
		historicListView.setAdapter(adapterArray);
//        mAdapter = new SampleAdapter(this, R.id.txt_line1);

		SharedPreferences settings = getSharedPreferences("historiquePreferences", 0);
		String filePath;
		String comment;
		StoredMedia media;
		for (int i = 1 ; i < 11 ; i++) {
			filePath = settings.getString("filePath"+i, "");
			if(filePath != null && filePath != ""){
				comment = settings.getString("commentaire"+i, "");
				media = new StoredMedia(filePath, comment);
				historicList.add(media);
			}
		}

        historicListView.setAdapter(adapterArray);
        historicListView.setOnScrollListener(this);
        historicListView.setOnItemClickListener(this);
    }

    
	
    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putStringArrayList(SAVED_DATA_KEY, mData);
    }

    @Override
    public void onScrollStateChanged(final AbsListView view, final int scrollState) {
        Log.d(TAG, "onScrollStateChanged:" + scrollState);
    }

    @Override
    public void onScroll(final AbsListView view, final int firstVisibleItem, final int visibleItemCount, final int totalItemCount) {
        Log.d(TAG, "onScroll firstVisibleItem:" + firstVisibleItem +
                            " visibleItemCount:" + visibleItemCount +
                            " totalItemCount:" + totalItemCount);
        // our handling
        if (!mHasRequestedMore) {
            int lastInScreen = firstVisibleItem + visibleItemCount;
            if (lastInScreen >= totalItemCount) {
                Log.d(TAG, "onScroll lastInScreen - so load more");
                mHasRequestedMore = true;
                onLoadMoreItems();
            }
        }
    }

    private void onLoadMoreItems() {
//        final ArrayList<String> sampleData = SampleData.generateSampleData();
//        for (String data : sampleData) {
//            adapterArray.add(data);
//        }
//        // stash all the data in our backing store
//        mData.addAll(sampleData);
//        // notify the adapter that we can update now
//        adapterArray.notifyDataSetChanged();
//        mHasRequestedMore = false;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Toast.makeText(this, "Item Clicked: " + position, Toast.LENGTH_SHORT).show();
    }

   

}
