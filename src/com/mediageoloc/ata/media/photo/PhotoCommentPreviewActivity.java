package com.mediageoloc.ata.media.photo;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.mediageoloc.ata.R;
import com.mediageoloc.ata.drawer.DrawerActivity;
import com.mediageoloc.ata.geoloc.GetGeoLocFragment;
import com.mediageoloc.ata.media.StoredMedia;
import com.mediageoloc.ata.media.TakeMediaActivity;
import com.mediageoloc.ata.utils.LocalImageLoader;
import com.mediageoloc.ata.utils.contentProvider.GeolocProvider;
import com.mediageoloc.ata.utils.contentProvider.MediaGeolocContract.Medias;
import com.mediageoloc.ata.utils.ObserverImageView;

public class PhotoCommentPreviewActivity extends DrawerActivity {
	
	@InjectView(R.id.photo_preview) ObserverImageView imageView;
	@InjectView(R.id.edit_text_comment) EditText editTextComment;
	@InjectView(R.id.button_save_comment) Button buttonSaveComment;
	
	private Uri photoUri;
	private GetGeoLocFragment fragmentGeoloc;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setDrawerContentView(R.layout.activity_photo_comment_preview);

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
		Observable<Bitmap> o = Observable.create(new LocalImageLoader(intent.getStringExtra("photoUri")));
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
				.subscribeOn(AndroidSchedulers.mainThread())
				.observeOn(Schedulers.newThread())
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
		Uri mUri = this.getContentResolver().insert(GeolocProvider.CONTENT_URI_MEDIAS, values);
	}
	
	private void returnTakeMediaActivity() {
		Intent intent = new Intent(this, TakeMediaActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

		startActivity(intent);
		// on masque l'activité courante
		finish();

	}

}




