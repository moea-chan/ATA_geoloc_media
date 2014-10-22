package com.mediageoloc.ata;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MainActivity extends Activity {
	

	private Button _buttonPhoto;
	private Button _buttonVideo;
	private Button _buttonAudio;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// add listeners on buttons
		addButtonPhotoListener();
		addButtonAudioListener();
		addButtonVideoListener();


	}
	
	
	private void addButtonPhotoListener(){
		_buttonPhoto = (Button) findViewById(R.id.buttonPhoto);
		_buttonPhoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            	Intent intent = new Intent(MainActivity.this,
						PhotoActivity.class);
				startActivity(intent);
            }
        });
	}
	
	private void addButtonVideoListener(){
		_buttonVideo = (Button) findViewById(R.id.buttonVideo);
		_buttonVideo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
            }
        });
	}
	
	private void addButtonAudioListener(){
		_buttonAudio = (Button) findViewById(R.id.buttonAudio);
		_buttonAudio.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
            }
        });
	}
	

	
}
