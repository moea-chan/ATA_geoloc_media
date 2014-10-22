package com.mediageoloc.ata;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class PhotoFilterPreviewActivity extends Activity {
	
	private Button _buttonGoToCommentPreview;
	private Uri _photoUri;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_filter_preview);
		addButtonGoToCommentpreviewListener();
		
		Intent intent = getIntent();
		ImageView imageView = (ImageView) findViewById(R.id.photo_preview);
		_photoUri = Uri.parse(intent.getStringExtra("photoUri"));
		
		try {
			Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),_photoUri);
			bitmap = Bitmap.createScaledBitmap(bitmap, 140, 190, false);
			imageView.setImageBitmap(bitmap);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void addButtonGoToCommentpreviewListener(){
		_buttonGoToCommentPreview = (Button) findViewById(R.id.button_go_to_comment_preview);
		_buttonGoToCommentPreview.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
            	startCommentPreviewActivity();
            }
        });
	}
	
	private void startCommentPreviewActivity(){
		Intent intent = new Intent(this, PhotoCommentPreviewActivity.class);
        intent.putExtra("photoUri", _photoUri.toString());
        startActivity(intent);
	}
}
