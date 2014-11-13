package com.mediageoloc.ata.historic;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.etsy.android.grid.util.DynamicHeightTextView;
import com.mediageoloc.ata.R;
import com.mediageoloc.ata.media.StoredMedia;
import com.squareup.picasso.Callback.EmptyCallback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Callback.EmptyCallback;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.Target;

public class HistoricAdapter extends ArrayAdapter<StoredMedia>  {

	private List<StoredMedia> mediaList;
	private Context context;

	@InjectView(R.id.picture)
	ImageView pictureView;
	@InjectView(R.id.comment)
	DynamicHeightTextView commentView;


	File pictureFile;

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
		ButterKnife.inject(this, convertView);
		// Now we can fill the layout with the right values
		StoredMedia media = mediaList.get(position);

		// set comment
		commentView.setText(media.getComment());


		//set image with async loading
		String filePath = media.getFilePath();
		filePath = filePath.substring(7, filePath.length());
		pictureFile = new File(filePath);
		
		final ProgressBar progressBar = ButterKnife.findById(convertView,R.id.loading);

		Picasso.with(context)
		.load(pictureFile)
		.error(R.drawable.ic_launcher)
		.resize(120, 120)
		.centerCrop()
		.into(pictureView, new EmptyCallback() {
			@Override public void onSuccess() {
				progressBar.setVisibility(View.GONE);
			} 
			@Override
			public void onError() {
				progressBar.setVisibility(View.GONE);
			} 
		});

		return convertView;
	}



}
