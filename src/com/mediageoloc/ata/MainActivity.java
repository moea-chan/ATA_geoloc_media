package com.mediageoloc.ata;
//Thierry - une modif pour visu branch

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		Button bouton1 = (Button) findViewById(R.id.buttonTest);
        
        bouton1.setOnClickListener(new View.OnClickListener() {
        
        	@Override
            public void onClick(View v) {
            // Lancer l'activit� 1
        		launchNewActivity(v);
             }
        });             
    
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	   public void launchNewActivity(View view) {

           Button button = (Button) view;
           String label = button.getText().toString();
       	   LaunchActivityByName("PhotoIntentActivity");

   }
	   public void LaunchActivityByName(String nameActivity)
	    {
	   	 if (nameActivity.equals("PhotoIntentActivity"))
		 {
		 		Intent intent = new Intent(this, PhotoIntentActivity.class );
		 		
			    startActivity(intent);
		 }
	    }
}
