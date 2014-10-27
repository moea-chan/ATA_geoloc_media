package com.mediageoloc.ata;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

public class PhotoFilterPreviewActivity extends Activity {
	
	private Button _buttonGoToCommentPreview;
	private Uri _photoUri;
	private ImageView _imageView;
	private CheckBox _filterAction;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_filter_preview);
		
		addButtonGoToCommentpreviewListener();
		addButtonFilterListener();
		//display taken picture
		Intent intent = getIntent();
		_imageView = (ImageView) findViewById(R.id.photo_preview);
		_photoUri = Uri.parse(intent.getStringExtra("photoUri"));
		
		try {
			Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),_photoUri);
			bitmap = Bitmap.createScaledBitmap(bitmap, 140, 190, false);
			_imageView.setImageBitmap(bitmap);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void addButtonGoToCommentpreviewListener(){
		_buttonGoToCommentPreview = (Button) findViewById(R.id.button_go_to_comment_preview);
		_buttonGoToCommentPreview.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
            	startCommentPreviewActivity();
            }
        });
	}
	private void addButtonFilterListener(){
		_filterAction = (CheckBox) findViewById(R.id.check_box_filter);
		_filterAction.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
            	doFilter();
            }
        });
	}
	
	private void startCommentPreviewActivity(){
		Intent intent = new Intent(this, PhotoCommentPreviewActivity.class);
        intent.putExtra("photoUri", _photoUri.toString());
        startActivity(intent);
	}
	
	   public void doFilter()
	   {
		   
		   if (_filterAction.isChecked())
		   {
			   
			      int value=100;
				   
			      BitmapDrawable drawable = (BitmapDrawable) _imageView.getDrawable();
			        final Bitmap src = drawable.getBitmap();
//				   Bitmap src = _imageView.getDrawingCache();
				   
			       // image size
			       int width = src.getWidth();
			       int height = src.getHeight();
			       // create output bitmap
			       Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
			       // color information
			       int A, R, G, B;
			       int pixel;
			    
			       // scan through all pixels
			       for(int x = 0; x < width; ++x) {
			           for(int y = 0; y < height; ++y) {
			               // get pixel color
			               pixel = src.getPixel(x, y);
			               A = Color.alpha(pixel);
			               R = Color.red(pixel);
			               G = Color.green(pixel);
			               B = Color.blue(pixel);
			    
			               // increase/decrease each channel
			               R += value;
			               if(R > 255) { R = 255; }
			               else if(R < 0) { R = 0; }
			    
			               G += value;
			               if(G > 255) { G = 255; }
			               else if(G < 0) { G = 0; }
			    
			               B += value;
			               if(B > 255) { B = 255; }
			               else if(B < 0) { B = 0; }
			    
			               // apply new pixel color to output bitmap
			               bmOut.setPixel(x, y, Color.argb(A, R, G, B));
			           }
			       }
			    
			       // return final image
			       _imageView.setImageBitmap(bmOut);
		   }
		   
	   }
}
