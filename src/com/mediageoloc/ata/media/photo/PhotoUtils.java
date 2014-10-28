package com.mediageoloc.ata.media.photo;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

public class PhotoUtils {
	
	public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	public static final int MEDIA_TYPE_IMAGE = 1;
	
	/** Create a file Uri for saving an image or video */
	public static Uri getOutputMediaFileUri(int type){
	      return Uri.fromFile(getOutputMediaFile(type));
	}
	
	/** Create a File for saving an image or video */
	public static File getOutputMediaFile(int type){
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
	
	public static Bitmap loadPhotoInImageViewByUri(ContentResolver contentResolver, String uri, int height, int width){
		Uri photoUri = Uri.parse(uri);
		try {
			Bitmap bitmap = MediaStore.Images.Media.getBitmap(contentResolver, photoUri);
			bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
			return bitmap;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 
	 * -Verifie l'existence de l'historiquePreferences et le complete eventuellement
	 * 		avec filePathX pour les URI et commentaireX pour les commentaires (0 < X < 11)
	 */
	public static void initHistoriquePreferences(SharedPreferences sharedpreferences){
		String existHisto = sharedpreferences.getString("filePath10", "%&%p%&defValue");
		String stKey;
		String stValue = "";
		
		if (existHisto.equals("%&%p%&defValue")){
			//Historique inexistant => Création
			Editor editor = sharedpreferences.edit();
			for (int i = 1; i < 11; i++){
				stKey = "filePath" + String.valueOf(i);
				editor.putString(stKey,stValue);
				stKey = "commentaire" + String.valueOf(i);
				editor.putString(stKey,stValue);
			}
			editor.commit();
		}		
	}
	
}
