package com.mediageoloc.ata.utils;


import rx.Observable;
import rx.Subscriber;
import android.graphics.Bitmap;

/*
 * load an image from a media and return bitmap
 */
public class ImageLoader implements Observable.OnSubscribe<Bitmap>{
	
	private String imagePath;
	
	public ImageLoader(String imagePath){
		this.imagePath = imagePath;
	}
	
    @Override
    public void call(Subscriber<? super Bitmap> fileObserver) {
        try {
        	Bitmap bitmap = ImageUtils.decodeSampledBitmapFromFilePath(imagePath, 120, 120);
            fileObserver.onNext(bitmap);
            fileObserver.onCompleted();
        } catch (Exception e) {
            fileObserver.onError(e);
        }
    }
}
