package com.mediageoloc.ata.utils;


import rx.Observable;
import rx.Subscriber;
import android.graphics.Bitmap;

import com.mediageoloc.ata.media.StoredMedia;

/*
 * load an image from a media and return bitmap
 */
public class ImageLoader implements Observable.OnSubscribe<Bitmap>{
	
	private StoredMedia media;
	
	public ImageLoader(StoredMedia media){
		this.media = media;
	}
	
    @Override
    public void call(Subscriber<? super Bitmap> fileObserver) {
        try {
        	Bitmap bitmap = ImageUtils.decodeSampledBitmapFromFilePath(media.getFilePath(), 120, 120);
            fileObserver.onNext(bitmap);
            fileObserver.onCompleted();
        } catch (Exception e) {
            fileObserver.onError(e);
        }
    }
}
