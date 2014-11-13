package com.mediageoloc.ata.media.photo;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import android.app.Activity;
import android.app.Application;
import android.app.FragmentTransaction;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.mediageoloc.ata.R;
import com.mediageoloc.ata.geoloc.GetGeoLocFragment;
import com.mediageoloc.ata.media.StoredMedia;
import com.mediageoloc.ata.media.TakeMediaActivity;
import com.mediageoloc.ata.media.utils.MediasProvider;
import com.mediageoloc.ata.user.UsersProvider;
import com.mediageoloc.ata.utils.ImageLoader;
import com.mediageoloc.ata.utils.ObserverImageView;
import com.mediageoloc.ata.utils.MediaGeolocContract.Medias;
import com.mediageoloc.ata.utils.MediaGeolocContract.Users;
/**
 * PhotoCommentPreviewActivity Class: save photo with comment and location in bdd
 * @author Thierry
 *
 */
public class PhotoCommentPreviewActivity extends Activity implements LoaderCallbacks<Cursor>{

	@InjectView(R.id.photo_preview) ObserverImageView imageView;
	@InjectView(R.id.edit_text_comment) EditText editTextComment;
	@InjectView(R.id.button_save_comment) Button buttonSaveComment;
	
	private Uri photoUri;
	private GetGeoLocFragment fragmentGeoloc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_comment_preview);
		ButterKnife.inject(this);
		
		addButtonSaveCommentListener();
		// For localisation
		fragmentGeoloc = new GetGeoLocFragment();
		FragmentTransaction ft = getFragmentManager().beginTransaction();
	    ft.add(fragmentGeoloc, "TFGPS");
	    ft.commit();
		// display taken picture
		Intent intent = getIntent();
		photoUri = Uri.parse(intent.getStringExtra("photoUri"));
		//set image with async loading
		Observable<Bitmap> o = Observable.create(new ImageLoader(intent.getStringExtra("photoUri")));
		o.subscribeOn(Schedulers.newThread())
		.observeOn(AndroidSchedulers.mainThread())
		.subscribe(imageView);
	}
	
	private void addButtonSaveCommentListener(){
		buttonSaveComment.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				savePictureCommented();
			}
		});
	}

	private void savePictureCommented() {
		Observable
				.create(new CommentUploader(photoUri, editTextComment,
						getApplicationContext()))
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new CommentObserver());
		SaveMediaBDD();
		returnTakeMediaActivity();
	}

	private void SaveMediaBDD(){
		// Create a new map of values
		ContentValues values = new ContentValues();
		values.put(Medias.COLUMN_NAME_CHEMINFICHIER, photoUri.toString());
		values.put(Medias.COLUMN_NAME_COMMENTAIRE, editTextComment.getText().toString());
		values.put(Medias.COLUMN_NAME_LATITUDE, fragmentGeoloc.getLastLatitute());
		values.put(Medias.COLUMN_NAME_LONGITUDE, fragmentGeoloc.getLastLongitude());
		values.put(Medias.COLUMN_NAME_TYPEMEDIA, StoredMedia.TYPEMEDIA_PHOTO);
		
		// use then UsersProvider on MEDIAS URI
		Uri mUri = this.getContentResolver().insert(UsersProvider.CONTENT_URI_MEDIAS, values);
	}
	
	private void returnTakeMediaActivity() {
		Intent intent = new Intent(this, TakeMediaActivity.class);
		// Empty the backstack with call activity on top
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(intent);
		// finish current activity
		finish();
	}
/**
 * define the projection on medias table
 */
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		String[] projection = {
			    BaseColumns._ID,
			    Medias.COLUMN_NAME_CHEMINFICHIER,
			    Medias.COLUMN_NAME_COMMENTAIRE,
			    Medias.COLUMN_NAME_LATITUDE,
			    Medias.COLUMN_NAME_LONGITUDE,
			    Medias.COLUMN_NAME_TYPEMEDIA
			    };
		return new CursorLoader(this, MediasProvider.CONTENT_URI, projection, null, null, null);
	}


	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		//Nothing todo
	}
	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		//Nothing todo
	}

}




