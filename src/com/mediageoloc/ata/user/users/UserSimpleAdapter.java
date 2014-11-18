package com.mediageoloc.ata.user.users;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.etsy.android.grid.util.DynamicHeightTextView;
import com.mediageoloc.ata.R;
import com.mediageoloc.ata.utils.contentProvider.GeolocProvider;
import com.mediageoloc.ata.utils.contentProvider.MediaGeolocContract.Users;
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
		progressBar.setVisibility(View.VISIBLE);
		
		final Button addFollow = ButterKnife.findById(convertView, R.id.add_follow);
		addFollow.setVisibility(View.GONE);
		
		final int curPosition = position;
		
		//ajoute aux amis le user courant au click sur bouton
		addFollow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
        		cursor.moveToPosition(curPosition);
        		int currentUserId = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
        		
        		ContentValues newVvalues = new ContentValues();
        		newVvalues.put(Users.COLUMN_NAME_FOLLOWED, 1);
        		context.getContentResolver().update(
        				ContentUris.withAppendedId(GeolocProvider.FOLLOWERS_CONTENT_URI, currentUserId), newVvalues, null, null);
            }
        });
		//charge l'image du user
		Picasso.with(context)
		.load(avatarPicUrl)
		.error(R.drawable.ic_launcher)
		.resize(120, 120)
		.centerCrop()
		.into(userImageView, new EmptyCallback() {
			@Override public void onSuccess() {
				progressBar.setVisibility(View.GONE);
				addFollow.setVisibility(View.VISIBLE);
			} 
			@Override
			public void onError() {
				progressBar.setVisibility(View.GONE);
				addFollow.setVisibility(View.VISIBLE);
			} 
		});

		return convertView;
	}



}
