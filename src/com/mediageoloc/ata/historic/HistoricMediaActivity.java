package com.mediageoloc.ata.historic;

import java.util.ArrayList;
import java.util.List;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

import com.mediageoloc.ata.R;
import com.mediageoloc.ata.media.StoredMedia;


public class HistoricMediaActivity extends Activity {
	private ListView historicListView;
	private List<StoredMedia> historicList = new ArrayList<StoredMedia>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_historic_media);
		
		historicListView = (ListView) findViewById(R.id.historic_list);
		
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
		
		HistoricAdapter adapterArray = new HistoricAdapter(historicList, getApplicationContext());
		historicListView.setAdapter(adapterArray);
	}
}
