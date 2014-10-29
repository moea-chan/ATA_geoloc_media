package com.mediageoloc.ata.historic;


import java.util.concurrent.Future;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import com.mediageoloc.ata.media.StoredMedia;

/*
 * load an image from a media and return bitmap
 */
public class HistoricImageLoader implements Observable.OnSubscribe<Bitmap>{
	
	private StoredMedia media;
	private Context context;
	private Bitmap bitmap;
	
	public HistoricImageLoader(StoredMedia media, Context context){
		ObserverImageView observer = new ObserverImageView(context);
		this.media = media;
		this.context = context;
//		Subscription sub = Observable.from()
//			    .subscribeOn(Schedulers.newThread())
//			    .observeOn(AndroidSchedulers.mainThread())
//			    .subscribe(observer);
	}
	
	        @Override
	        public void call(Subscriber<? super Bitmap> fileObserver) {
	            try {
	                
	                //fileObserver.onNext(file);
	                fileObserver.onCompleted();
	            } catch (Exception e) {
	                fileObserver.onError(e);
	            }
	        }

	
	/*
	 * Image loading
	 */
//	void broadcast() {
//		if(media != null && context != null){
//            //set change
//            setChanged();
//            try {
//            	Uri photoUri = Uri.parse(media.getFilePath());     		
//    			Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(),photoUri);
//    			bitmap = Bitmap.createScaledBitmap(bitmap, 120, 120, false);
//    			//notify observers for loading done with bitmap in parameter
//    			notifyObservers(bitmap);
//            } catch (Exception e) {}
//		}
//    }
}
