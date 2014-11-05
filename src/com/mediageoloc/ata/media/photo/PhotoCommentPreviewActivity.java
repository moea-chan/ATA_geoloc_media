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
import com.mediageoloc.ata.media.StoredMedia;
import com.mediageoloc.ata.media.TakeMediaActivity;
import com.mediageoloc.ata.utils.ImageLoader;
import com.mediageoloc.ata.utils.ObserverImageView;

/**
 * class PhotoCommentPreviewActivity : Take comment and save in historic
 * @author Thierry
 *
 */
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

		ObserverImageView imageView = (ObserverImageView) findViewById(R.id.photo_preview);
		editTextComment = (EditText) findViewById(R.id.edit_text_comment);
		photoUri = Uri.parse(intent.getStringExtra("photoUri"));
		
		chargeImageSourcePreview();
		
		
		//Bitmap bitmap = ImageUtils.loadPhotoInImageViewByUri(this.getContentResolver(), intent.getStringExtra("photoUri"), 190, 140);
		//imageView.setImageBitmap(bitmap);		
	}
	
	/**
	 * chargeImageSourcePreview : use observable com.mediageoloc.utils.Imageloader
	 */
		private void chargeImageSourcePreview() {
			//set image with async loading
			ObserverImageView pictureView = (ObserverImageView) findViewById(R.id.photo_preview);
			StoredMedia media = new StoredMedia(photoUri.getPath(), "");
			
			Observable<Bitmap> o = Observable.create(new ImageLoader(media.getFilePath()));
			o.subscribeOn(Schedulers.newThread())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(pictureView);
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




