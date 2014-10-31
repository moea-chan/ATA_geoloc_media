package com.mediageoloc.ata.historic;


import rx.Observable;
import rx.Subscriber;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import com.mediageoloc.ata.media.StoredMedia;
import com.mediageoloc.ata.media.photo.PhotoUtils;

/*
 * load an image from a media and return bitmap
 */
public class HistoricImageLoader implements Observable.OnSubscribe<Bitmap>{
	
	private StoredMedia media;
	
	public HistoricImageLoader(StoredMedia media){
		this.media = media;
	}
	
    @Override
    public void call(Subscriber<? super Bitmap> fileObserver) {
        try {
        	Bitmap bitmap = PhotoUtils.decodeSampledBitmapFromFilePath(media.getFilePath(), 120, 120);
            fileObserver.onNext(bitmap);
            fileObserver.onCompleted();
        } catch (Exception e) {
            fileObserver.onError(e);
        }
    }
}
