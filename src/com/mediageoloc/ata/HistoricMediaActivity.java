package com.mediageoloc.ata;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;


public class HistoricMediaActivity extends Activity {
	private ListView _historicListView;
	private List<StoredMedia> _historicList = new ArrayList<StoredMedia>();
	private HistoricAdapter _historicAdpt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_historic_media);
		
		_historicListView = (ListView) findViewById(R.id.historic_list);
		
		SharedPreferences settings = getSharedPreferences("historiquePreferences", 0);
		String filePath;
		String comment;
		StoredMedia media;
		for (int i = 1 ; i < 11 ; i++) {
			filePath = settings.getString("filePath"+i, "");
			if(filePath != null && filePath != ""){
				comment = settings.getString("commentaire"+i, "");
				media = new StoredMedia(filePath, comment);
				_historicList.add(media);
			}
		}
		
		HistoricAdapter adapterArray = new HistoricAdapter(_historicList, getApplicationContext());
		_historicListView.setAdapter(adapterArray);
	}
}
