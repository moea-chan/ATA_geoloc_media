package com.mediageoloc.ata;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

            	Intent intent = new Intent(MainActivity.this,
						SoundActivity.class);
				startActivity(intent);
            }
        });
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			// on appelle la méthode statique Toast.makeText, et la fonction de
			// callback show();
			Toast.makeText(this, "Settings menu was pressed.",
					Toast.LENGTH_SHORT).show();
			// le return true permet de valider l'utilisation du menu
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/*
	 *  cette méthode est appelé par le onClick du menu 3
	 *  elle doit être publique 
	 *  Pour l'instant un bug avec onClick
	 */
	public void ShowMenuOption(MenuItem item) {
		// Menu item pressed
		Toast.makeText(this, "Menu 3 menu was pressed.", Toast.LENGTH_SHORT)
				.show();
		//return true; // Indicated menu press was handled
	}
	
}
