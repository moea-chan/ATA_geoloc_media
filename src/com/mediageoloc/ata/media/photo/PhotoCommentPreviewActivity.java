package com.mediageoloc.ata.media.photo;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import android.app.Activity;
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
import com.mediageoloc.ata.media.TakeMediaActivity;
import com.mediageoloc.ata.utils.LocalImageLoader;
import com.mediageoloc.ata.utils.ObserverImageView;

public class PhotoCommentPreviewActivity extends Activity {
	
	@InjectView(R.id.photo_preview) ObserverImageView imageView;
	@InjectView(R.id.edit_text_comment) EditText editTextComment;
	@InjectView(R.id.button_save_comment) Button buttonSaveComment;
	
	private Uri photoUri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_comment_preview);

		ButterKnife.inject(this);
		
		addButtonSaveCommentListener();

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
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new CommentObserver());
		returnTakeMediaActivity();
	}

	private void returnTakeMediaActivity() {
		Intent intent = new Intent(this, TakeMediaActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		startActivity(intent);
		// on masque l'activité courante
		finish();

	}

}




