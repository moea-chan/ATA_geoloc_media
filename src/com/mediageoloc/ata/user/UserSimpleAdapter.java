package com.mediageoloc.ata.user;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.mediageoloc.ata.R;
import com.mediageoloc.ata.utils.DistantImageLoader;
import com.mediageoloc.ata.utils.LocalImageLoader;
import com.mediageoloc.ata.utils.MediaGeolocContract.Users;
import com.mediageoloc.ata.utils.ObserverImageView;

public class UserSimpleAdapter extends SimpleCursorAdapter {
	
	Cursor myCursor;
	Context myContext;
	
	@InjectView(R.id.user_picture)
	ObserverImageView userImageView;
	@InjectView(R.id.user_item)
	TextView userPseudo;
	
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
			row = inflater.inflate(R.layout.user_item, parent, false); 
		}
		ButterKnife.inject(this, row);
		
		myCursor.moveToPosition(position);

		String pseudo = myCursor.getString(myCursor.getColumnIndex(Users.COLUMN_NAME_PRENOM));
		String avatarPicUrl = myCursor.getString(myCursor.getColumnIndex(Users.COLUMN_NAME_TELEPHONE));
		userPseudo.setText(pseudo);
		
		URL imageUrl;
		try {
			imageUrl = new URL(avatarPicUrl);
			Observable<Bitmap> o = Observable.create(new DistantImageLoader(imageUrl));
			o.subscribeOn(Schedulers.newThread())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(userImageView);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		return row;
	}

	
	
}
