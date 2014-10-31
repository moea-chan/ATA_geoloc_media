package com.mediageoloc.ata.media;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import com.mediageoloc.ata.R;
import com.mediageoloc.ata.drawer.DrawerActivity;
import com.mediageoloc.ata.historic.HistoricMediaActivity;
import com.mediageoloc.ata.historic.HistoricPrefManager;
import com.mediageoloc.ata.media.photo.PhotoFilterPreviewActivity;
import com.mediageoloc.ata.utils.ImageUtils;

public class TakeMediaActivity extends DrawerActivity {

	private Button buttonPhoto;
	private Button buttonAudio;
	
	private Uri photoUri;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_take_media);
		
		setDrawerContentView(R.layout.activity_take_media);


		HistoricPrefManager.initHistoriquePreferences(getApplicationContext());

		
		// add listeners on buttons
		addButtonPhotoListener();
		addButtonAudioListener();
	}

	
	public void startPhotoActivity(){
		// create Intent to take a picture and return control to the calling application
		Intent intentPhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		photoUri = ImageUtils.getOutputMediaFileUri(ImageUtils.MEDIA_TYPE_IMAGE); // create a file to save the image
	    intentPhoto.putExtra(MediaStore.EXTRA_OUTPUT, photoUri); // set the image file name
	    
	    // start the image capture Intent
	    startActivityForResult(intentPhoto, ImageUtils.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == ImageUtils.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
	        if (resultCode == RESULT_OK) {
	            // Image captured and saved to fileUri specified in the Intent
	            Intent intent = new Intent(this, PhotoFilterPreviewActivity.class);
	            intent.putExtra("photoUri", photoUri.toString());
	            startActivity(intent);
	        } else if (resultCode == RESULT_CANCELED) {
	            // User cancelled the image capture
	        } else {
	            // Image capture failed, advise user
	        }
	    } 
	}
	
	private void addButtonPhotoListener(){
		buttonPhoto = (Button) findViewById(R.id.buttonPhoto);
		buttonPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
            	startPhotoActivity();
            }
        });
	}
	
	private void addButtonAudioListener(){
		buttonAudio = (Button) findViewById(R.id.buttonAudio);
		buttonAudio.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
            	startHistoricMediaActivity();
            }
        });
	}
	
	private void startHistoricMediaActivity(){
		Intent intent = new Intent(this, HistoricMediaActivity.class);
        startActivity(intent);
	}

}
