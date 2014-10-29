package com.mediageoloc.ata.media.photo;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.mediageoloc.ata.R;


public class PhotoFilterPreviewActivity extends Activity {

	private Button buttonGoToCommentPreview;
	private Uri photoUri;
	private ImageView imageView;
	private CheckBox filterAction;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_filter_preview);

		addButtonGoToCommentpreviewListener();
		addButtonFilterListener();
		//display taken picture
		Intent intent = getIntent();
		imageView = (ImageView) findViewById(R.id.photo_preview);
		photoUri = Uri.parse(intent.getStringExtra("photoUri"));

		chargeImageSourcePreview();
	}

	private void addButtonGoToCommentpreviewListener(){
		buttonGoToCommentPreview = (Button) findViewById(R.id.button_go_to_comment_preview);
		buttonGoToCommentPreview.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startCommentPreviewActivity();
			}
		});
	}
	
	private void chargeImageSourcePreview() {
		try {
			Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),photoUri);
			bitmap = Bitmap.createScaledBitmap(bitmap, 140, 190, false);
			imageView.setImageBitmap(bitmap);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void addButtonFilterListener(){
		filterAction = (CheckBox) findViewById(R.id.check_box_filter);
		filterAction.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				doFilter();
			}
		});
	}

	private void startCommentPreviewActivity(){
		Intent intent = new Intent(this, PhotoCommentPreviewActivity.class);
		intent.putExtra("photoUri", photoUri.toString());
		startActivity(intent);
	}

	private void doFilter(){
		if (filterAction.isChecked()){
			int value=100;

			BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
			final Bitmap src = drawable.getBitmap();
			Bitmap bmOut = PhotoUtils.brightnessFilter(value, src);
			imageView.setImageBitmap(bmOut);
		}
		else{
			chargeImageSourcePreview();
		}
	}
/**
 * saveFilteredPictures : Load original picture to apply filter before save it
 */
	private void saveFilteredPictures(){
		if (filterAction.isChecked()){
			int value=100;
			try {
				final Bitmap src = MediaStore.Images.Media.getBitmap(this.getContentResolver(),photoUri);
				Bitmap bmOut = PhotoUtils.brightnessFilter(value, src);
				//MediaStore.Images.Media.insertImage(, source, title, description)
				//TODO/ Faire sauvegarde disque
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
