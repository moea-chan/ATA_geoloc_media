package com.mediageoloc.ata;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class TakeMediaActivity extends Activity {
	

	private Button buttonPhoto;
	private Button buttonAudio;
	
	private Uri photoUri;
	private Intent intentPhoto;
	
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	public static final int MEDIA_TYPE_IMAGE = 1;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_take_media);
		
		initHistoriquePreferences();
		
		// add listeners on buttons
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
	            intent.putExtra("photoUri", photoUri.toString());
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
		intentPhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		photoUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
	    intentPhoto.putExtra(MediaStore.EXTRA_OUTPUT, photoUri); // set the image file name
	    
	    // start the image capture Intent
	    startActivityForResult(intentPhoto, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
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
	
	/**
	 * initHistoriquePreferences()
	 * 
	 * -Verifie l'existence de l'historiquePreferences et le complete eventuellement
	 * 		avec filePathX pour les URI et commentaireX pour les commentaires (0 < X < 11)
	 */
	private void initHistoriquePreferences(){
		
		SharedPreferences sharedpreferences  =  getSharedPreferences("historiquePreferences", MODE_PRIVATE);
		
		String existHisto = sharedpreferences.getString("filePath10", "%&%p%&defValue");
		String stKey;
		String stValue = "";
		
		if (existHisto.equals("%&%p%&defValue"))
		{
			//Historique inexistant => Création
			
			Editor editor = sharedpreferences.edit();
			for (int i =1;i<11;i++)
			{
				stKey= "filePath" + String.valueOf(i);
				editor.putString(stKey,stValue);
				stKey= "commentaire" + String.valueOf(i);
				editor.putString(stKey,stValue);
			}
			editor.commit();
		}		
		
	}

}
