package com.mediageoloc.ata.user;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.mediageoloc.ata.R;
import com.mediageoloc.ata.utils.MediaGeolocContract.Users;

public class UserSimpleAdapter extends SimpleCursorAdapter {
	
	Cursor myCursor;
	Context myContext;
	
	@InjectView(R.id.follower_picture)
	ImageView followersImageView;
	@InjectView(R.id.follower_item)
	TextView followersPseudo;
	
	public UserSimpleAdapter(Context context, int layout, Cursor c, String[] from,
			int[] to, int flags) {
		super(context, layout, c, from, to, flags);
		
		myCursor = c;
		myContext = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if(row == null){
			LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.follower_item, parent, false); 
		}
		ButterKnife.inject(this, row);
		
		myCursor.moveToPosition(position);

		String pseudo = myCursor.getString(myCursor.getColumnIndex(Users.COLUMN_NAME_PRENOM));
		String avatarPicUrl = myCursor.getString(myCursor.getColumnIndex(Users.COLUMN_NAME_TELEPHONE));
		followersPseudo.setText(pseudo);

		Bitmap myBitmap = null;

//			myBitmap = BitmapFactory.decodeByteArray(data, offset, length);
//					decodeFile(avatarPicUrl);
//			followersImageView.setImageBitmap(myBitmap);

		return row;
	}

	
	
}
