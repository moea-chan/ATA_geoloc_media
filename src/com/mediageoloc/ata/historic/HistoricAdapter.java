package com.mediageoloc.ata.historic;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mediageoloc.ata.R;
import com.mediageoloc.ata.media.StoredMedia;


public class HistoricAdapter extends ArrayAdapter<StoredMedia> {

	private List<StoredMedia> mediaList;
	private Context context;

	public HistoricAdapter(List<StoredMedia> mediaList, Context ctx) {
		super(ctx, R.layout.activity_historic_item, mediaList);
		this.mediaList = mediaList;
		this.context = ctx;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// First let's verify the convertView is not null
		if (convertView == null) {
			// This a new view we inflate the new layout
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.activity_historic_item, parent, false);
		}
		// Now we can fill the layout with the right values
		StoredMedia media = mediaList.get(position);
		
		// set comment
		TextView commentView = (TextView) convertView.findViewById(R.id.comment);
		commentView.setText(media.getComment());
		
		//set image with async loading
		ObserverImageView pictureView = (ObserverImageView) convertView.findViewById(R.id.picture);
		HistoricImageLoader imgLoader = new HistoricImageLoader(media, context);
		imgLoader.addObserver(pictureView);
		imgLoader.broadcast();

		return convertView;
	}
	
	
	
	/*
	 * (non-Javadoc)
	 * @see android.widget.BaseAdapter#isEnabled(int)
	 * disable click on item
	 */
	@Override
    public boolean isEnabled(int position){
		return false;
	}
}
