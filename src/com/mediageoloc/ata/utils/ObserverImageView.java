package com.mediageoloc.ata.utils;



import rx.Observer;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * image view observer
 * observe image loading and set its image with bitmap returned
 */
public class ObserverImageView extends ImageView implements Observer<Bitmap> {
	
	public ObserverImageView(Context context) {
		super(context);
	}
	
	public ObserverImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public ObserverImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void onError(Throwable arg0) {
		Log.e("rxjava", "image load failed");
	}

	@Override
	public void onNext(Bitmap arg0) {
		this.setImageBitmap(arg0);
	}

	@Override
	public void onCompleted() {}
	
}
