package com.mediageoloc.ata.user;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.etsy.android.grid.util.DynamicHeightTextView;
import com.mediageoloc.ata.R;
import com.mediageoloc.ata.utils.MediaGeolocContract.Users;
import com.squareup.picasso.Callback.EmptyCallback;
import com.squareup.picasso.Picasso;

public class UserSimpleAdapter extends SimpleCursorAdapter {

	Cursor cursor;
	Context context;

	@InjectView(R.id.user_picture)
	ImageView userImageView;
	@InjectView(R.id.user_item)
	DynamicHeightTextView userPseudo;

	public UserSimpleAdapter(Context context, int layout, Cursor c, String[] from,
			int[] to, int flags) {
		super(context, layout, c, from, to, flags);

		this.cursor = c;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.user_item, parent, false); 
		}

		ButterKnife.inject(this, convertView);

		cursor.moveToPosition(position);

		//pseudo
		String pseudo = cursor.getString(cursor.getColumnIndex(Users.COLUMN_NAME_PRENOM));
		userPseudo.setText(pseudo);

		//image
		String avatarPicUrl = cursor.getString(cursor.getColumnIndex(Users.COLUMN_NAME_TELEPHONE));

		final ProgressBar progressBar = ButterKnife.findById(convertView, R.id.loading);

		Picasso.with(context)
		.load(avatarPicUrl)
		.error(R.drawable.ic_launcher)
		.resize(120, 120)
		.centerCrop()
		.into(userImageView, new EmptyCallback() {
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
