package com.mediageoloc.ata.historic;

import java.util.Observable;
import java.util.Observer;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * image view observer
 * observe image loading and set its image with bitmap returned
 */
public class ObserverImageView extends ImageView implements Observer {

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
	public void update(Observable observable, Object data) {
		this.setImageBitmap((Bitmap)data);
	}
	
}
