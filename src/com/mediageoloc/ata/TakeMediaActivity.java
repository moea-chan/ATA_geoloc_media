package com.mediageoloc.ata;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class TakeMediaActivity extends DrawerActivity {
	

	private Button _buttonPhoto;
	private Button _buttonAudio;
	
	private Uri _photoUri;
	private Intent _intentPhoto;
	
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	public static final int MEDIA_TYPE_IMAGE = 1;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        //get the main layout from xml
        LinearLayout mainLayout = (LinearLayout)findViewById(R.id.content_frame);
 
        //create a view to inflate the new layout
        View view = getLayoutInflater().inflate(R.layout.activity_take_media, mainLayout, true);
 

		//add the view to the main layout
        //mainLayout.addView(view);

        //add listeners on buttons
		addButtonPhotoListener();
		addButtonAudioListener();
	}
    
	/** Create a file Uri for saving an image or video */
	private static Uri getOutputMediaFileUri(int type){
	      return Uri.fromFile(getOutputMediaFile(type));
	}
	
	/** Create a File for saving an image or video */
	private static File getOutputMediaFile(int type){
	    // To be safe, you should check that the SDCard is mounted
	    // using Environment.getExternalStorageState() before doing this.

	    File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
	              Environment.DIRECTORY_PICTURES), "MyCameraApp");
	    // This location works best if you want the created images to be shared
	    // between applications and persist after your app has been uninstalled.

	    // Create the storage directory if it does not exist
	    if (! mediaStorageDir.exists()){
	        if (! mediaStorageDir.mkdirs()){
	            Log.d("MyCameraApp", "failed to create directory");
	            return null;
	        }
	    }

	    // Create a media file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    File mediaFile;
	    if (type == MEDIA_TYPE_IMAGE){
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	        "IMG_"+ timeStamp + ".jpg");
	    } else {
	        return null;
	    }
	    return mediaFile;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
	        if (resultCode == RESULT_OK) {
	            // Image captured and saved to fileUri specified in the Intent
	            Intent intent = new Intent(this, PhotoFilterPreviewActivity.class);
	            intent.putExtra("photoUri", _photoUri.toString());
	            startActivity(intent);
	            
	        } else if (resultCode == RESULT_CANCELED) {
	            // User cancelled the image capture
	        } else {
	            // Image capture failed, advise user
	        }
	    } 
	}
	
	private void startPhotoActivity(){
		// create Intent to take a picture and return control to the calling application
		_intentPhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		_photoUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
	    _intentPhoto.putExtra(MediaStore.EXTRA_OUTPUT, _photoUri); // set the image file name
	    
	    // start the image capture Intent
	    startActivityForResult(_intentPhoto, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	}
	
	
	private void addButtonPhotoListener(){
		_buttonPhoto = (Button) findViewById(R.id.buttonPhoto);
		_buttonPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
            	startPhotoActivity();
            }
        });
	}
	
	private void addButtonAudioListener(){
		_buttonAudio = (Button) findViewById(R.id.buttonAudio);
		_buttonAudio.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
            }
        });
	}

}
