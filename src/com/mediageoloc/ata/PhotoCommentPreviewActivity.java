package com.mediageoloc.ata;



import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class PhotoCommentPreviewActivity extends Activity {
	
	private Button _buttonSaveComment;
	private EditText _editTextComment;
	private Uri _photoUri;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_comment_preview);
		
		addButtonSaveCommentListener();
		
		//display taken picture
		Intent intent = getIntent();
		ImageView imageView = (ImageView) findViewById(R.id.photo_preview);
		_editTextComment = (EditText) findViewById(R.id.edit_text_comment);
		_photoUri = Uri.parse(intent.getStringExtra("photoUri"));
		
		try {
			Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),_photoUri);
			bitmap = Bitmap.createScaledBitmap(bitmap, 140, 190, false);
			imageView.setImageBitmap(bitmap);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void addButtonSaveCommentListener(){
		_buttonSaveComment = (Button) findViewById(R.id.button_save_comment);
		_buttonSaveComment.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
            	savePictureCommented();
            }
        });
	}
	
	/**
	 * Via les sharedReference
	 */
	private void savePictureCommented(){
		
		gestionHistoriquePreferences();
		returnTakeMediaActivity();
		
	}
	
	
	private void gestionHistoriquePreferences(){
		
		SharedPreferences sharedpreferences  =  getSharedPreferences("historiquePreferences", MODE_PRIVATE);
		
		String stKey;
		String stValue = "";
		
	
		//Decalage Historique 
		
		Editor editor = sharedpreferences.edit();
		for (int i = 9;i >0;i--)
		{
			stKey= "filePath" + String.valueOf(i);
			stValue = sharedpreferences.getString(stKey, "%&%p%&defValue");
			
			if (!stValue.equals("%&%p%&defValue"))
			{
				stKey= "filePath" + String.valueOf(i+1);
				editor.putString(stKey,stValue);	
			
				stKey= "commentaire" + String.valueOf(i);
				stValue = sharedpreferences.getString(stKey, "%&%p%&defValue");
				
				stKey= "commentaire" + String.valueOf(i+1);
				editor.putString(stKey,stValue);	
				
			}
		}
		//insertion nouvelles donnees
		stKey= "filePath1";
		stValue = _photoUri.toString();
		editor.putString(stKey,stValue);	
		
		stKey= "commentaire1";
		stValue = _editTextComment.getText().toString();
		editor.putString(stKey,stValue);	
		
		editor.commit();

	}
	
	private void returnTakeMediaActivity(){
		
		Intent intent = new Intent(this, TakeMediaActivity.class);
		intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);
        //on masque l'activité courante
        finish();
        
	}
	
}
