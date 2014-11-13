package com.mediageoloc.ata.utils;


import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import rx.Observable;
import rx.Subscriber;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/*
 * load an image from a url and return bitmap
 */
public class DistantImageLoader implements Observable.OnSubscribe<Bitmap>{
	
	private URL imageUrl;
	
	public DistantImageLoader(URL imageUrl){
		this.imageUrl = imageUrl;
	}
	
    @Override
    public void call(Subscriber<? super Bitmap> fileObserver) {
    	try {
            final URLConnection conn = imageUrl.openConnection();
            conn.connect();
            final BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
            final Bitmap bm = BitmapFactory.decodeStream(bis);
            bis.close();
            fileObserver.onNext(bm);
            fileObserver.onCompleted();
        } catch (IOException e) {
        	fileObserver.onError(e);
        }
        
        
    }
}
