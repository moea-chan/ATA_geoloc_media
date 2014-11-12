package com.mediageoloc.ata.user;

import android.content.Context;
import android.database.Cursor;
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
//		followersImageView = (ImageView)row.findViewById(R.id.follower_picture);
//		followersPseudo = (TextView)row.findViewById(R.id.follower_item);

		myCursor.moveToPosition(position);

		String pseudo = myCursor.getString(myCursor.getColumnIndex(Users.COLUMN_NAME_PRENOM));
		String avatarPicUrl = myCursor.getString(myCursor.getColumnIndex(Users.COLUMN_NAME_TELEPHONE));
		followersPseudo.setText(pseudo);

		String[] thumbColumns = {Users.COLUMN_NAME_PRENOM, Users.COLUMN_NAME_TELEPHONE};
//		CursorLoader thumbCursorLoader = new CursorLoader(
//				myContext, 
//				UserProvider.CONTENT_URI, 
//				thumbColumns, 
//				thumb_IMAGE_ID + "=" + myID, 
//				null, 
//				null);
//		Cursor thumbCursor = thumbCursorLoader.loadInBackground();
//
//		Bitmap myBitmap = null;
//		if(thumbCursor.moveToFirst()){
//			int thCulumnIndex = thumbCursor.getColumnIndex(thumb_DATA);
//			String thumbPath = thumbCursor.getString(thCulumnIndex);
//			myBitmap = BitmapFactory.decodeFile(thumbPath);
//			thumbV.setImageBitmap(myBitmap);
//		}

		return row;
	}


}
