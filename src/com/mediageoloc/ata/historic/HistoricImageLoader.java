package com.mediageoloc.ata.historic;

import java.util.Observable;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import com.mediageoloc.ata.media.StoredMedia;

/*
 * load an image from a media and return bitmap
 */
public class HistoricImageLoader extends Observable{
	
	private StoredMedia media;
	private Context context;

	public HistoricImageLoader(StoredMedia media, Context context){
		super();
		this.media = media;
		this.context = context;
	}
	
	/*
	 * Image loading
	 */
	void broadcast() {
		if(media != null && context != null){
            //set change
            setChanged();
            try {
            	Uri photoUri = Uri.parse(media.getFilePath());     		
    			Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(),photoUri);
    			bitmap = Bitmap.createScaledBitmap(bitmap, 120, 120, false);
    			//notify observers for loading done with bitmap in parameter
    			notifyObservers(bitmap);
            } catch (Exception e) {}
		}
    }
}
