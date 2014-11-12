package com.mediageoloc.ata.historic;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.etsy.android.grid.util.DynamicHeightTextView;
import com.mediageoloc.ata.R;
import com.mediageoloc.ata.media.StoredMedia;
import com.mediageoloc.ata.utils.ImageLoader;
import com.mediageoloc.ata.utils.ObserverImageView;


public class HistoricAdapter extends ArrayAdapter<StoredMedia>  {

	private List<StoredMedia> mediaList;
	private Context context;

	public HistoricAdapter(List<StoredMedia> mediaList, Context ctx) {
		super(ctx, R.layout.historic_item, mediaList);
		this.mediaList = mediaList;
		this.context = ctx;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// First let's verify the convertView is not null
		if (convertView == null) {
			// This a new view we inflate the new layout
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.historic_item, parent, false);
		}
		// Now we can fill the layout with the right values
		StoredMedia media = mediaList.get(position);
		
		// set comment
		DynamicHeightTextView commentView = (DynamicHeightTextView) convertView.findViewById(R.id.comment);
		commentView.setText(media.getComment());
		
		//set image with async loading
		ObserverImageView pictureView = (ObserverImageView) convertView.findViewById(R.id.picture);

		Observable<Bitmap> o = Observable.create(new ImageLoader(media.getFilePath()));
		o.subscribeOn(Schedulers.newThread())
		.observeOn(AndroidSchedulers.mainThread())
		.subscribe(pictureView);

		return convertView;
	}
	
	
	
	
}
