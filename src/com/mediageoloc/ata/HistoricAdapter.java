package com.mediageoloc.ata;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HistoricAdapter extends ArrayAdapter<StoredMedia> {

	private List<StoredMedia> _mediaList;
	private Context context;

	public HistoricAdapter(List<StoredMedia> mediaList, Context ctx) {
		super(ctx, R.layout.activity_historic_item, mediaList);
		this._mediaList = mediaList;
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
		TextView commentView = (TextView) convertView.findViewById(R.id.comment);
		ImageView pictureView = (ImageView) convertView.findViewById(R.id.picture);
		StoredMedia media = _mediaList.get(position);

		commentView.setText(media.get_comment());

		Uri photoUri = Uri.parse(media.get_filePath());
		// Display picture in correct size
		try {
			Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),photoUri);
			bitmap = Bitmap.createScaledBitmap(bitmap, 120, 120, false);
			pictureView.setImageBitmap(bitmap);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return convertView;
	}
}
