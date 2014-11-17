package com.mediageoloc.ata.media;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.mediageoloc.ata.R;
import com.mediageoloc.ata.drawer.DrawerActivity;
import com.mediageoloc.ata.geoloc.GetGeoLocFragment;
import com.mediageoloc.ata.historic.HistoricPrefManager;
import com.mediageoloc.ata.media.photo.PhotoFilterPreviewActivity;
import com.mediageoloc.ata.utils.ImageUtils;

public class TakeMediaActivity extends DrawerActivity {

	private Uri photoUri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setDrawerContentView(R.layout.activity_take_media);

		ButterKnife.inject(this);
		FragmentTransaction ft = getFragmentManager().beginTransaction();
	    ft.add(new GetGeoLocFragment(), "TFGPS");
	    ft.commit();
		HistoricPrefManager.initHistoriquePreferences(getApplicationContext());

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

	

	

}
