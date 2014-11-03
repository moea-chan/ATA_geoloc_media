package com.mediageoloc.ata.media;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.mediageoloc.ata.R;
import com.mediageoloc.ata.drawer.DrawerActivity;
import com.mediageoloc.ata.historic.HistoricMediaActivity;
import com.mediageoloc.ata.historic.HistoricPrefManager;
import com.mediageoloc.ata.media.photo.PhotoFilterPreviewActivity;
import com.mediageoloc.ata.user.GitHubService;
import com.mediageoloc.ata.utils.ImageUtils;

public class TakeMediaActivity extends DrawerActivity {

	private static Context context;
	private Uri photoUri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setDrawerContentView(R.layout.activity_take_media);
		TakeMediaActivity.context = getApplicationContext();
		ButterKnife.inject(this);

		HistoricPrefManager.initHistoriquePreferences(getApplicationContext());
//		GeolocMediaDbHelper localeDB = new GeolocMediaDbHelper(this);
//		GeolocMediaDbWriter.writeUserDB(this, 1, "nom1", "prenom1", "mail1", "telephone1");
//		GeolocMediaDbWriter.writeUserDB(this,2, "nom2", "prenom1", "mail1", "telephone1");
//		GeolocMediaDbWriter.writeUserDB(this, 3, "nom3", "prenom1", "mail1", "telephone1");
//		GeolocMediaDbWriter.writeUserDB(this, 4, "nom4", "prenom1", "mail1", "telephone1");
//		GeolocMediaDbWriter.writeUserDB(this, 5, "nom5", "prenom1", "mail1", "telephone1");
//		GeolocMediaDbWriter.writeUserDB(this, 6, "nom6", "prenom1", "mail1", "telephone1");
//		GeolocMediaDbWriter.writeUserDB(this, 7, "nom7", "prenom1", "mail1", "telephone1");
//		GeolocMediaDbWriter.writeUserDB(this, 8, "nom8", "prenom1", "mail1", "telephone1");
//		GeolocMediaDbWriter.writeUserDB(this, 9, "nom9", "prenom1", "mail1", "telephone1");
//		GeolocMediaDbWriter.writeUserDB(this, 10, "nom10", "prenom1", "mail1", "telephone1");
//		GeolocMediaDbWriter.writeUserDB(this, 11, "nom11", "prenom1", "mail1", "telephone1");
//		GeolocMediaDbWriter.writeUserDB(this, 12, "nom12", "prenom1", "mail1", "telephone1");
//		GeolocMediaDbWriter.writeUserDB(this, 13, "nom13", "prenom1", "mail1", "telephone1");
//		GeolocMediaDbReader.readUsersDB(this);
		GitHubService.getCollaborators();	
	}
/**
 * getAppContext : return application context
 * @return
 */
	public static Context getAppContext() {
	        return TakeMediaActivity.context;
	    }

	public void startPhotoActivity() {
		// create Intent to take a picture and return control to the calling
		// application
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

	@OnClick(R.id.buttonPhoto)
	void addButtonPhotoListener() {
		startPhotoActivity();

	}

	@OnClick(R.id.buttonAudio)
	void addButtonAudioListener() {
		startHistoricMediaActivity();
	}

	private void startHistoricMediaActivity() {
		Intent intent = new Intent(this, HistoricMediaActivity.class);
		startActivity(intent);
	}

}
