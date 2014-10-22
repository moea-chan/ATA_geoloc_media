package com.mediageoloc.ata;



import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class PhotoCommentPreviewActivity extends Activity {
	
	private Button _buttonSaveComment;
	private EditText _editTextComment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_comment_preview);
		
		addButtonSaveCommentListener();
		
		//display taken picture
		Intent intent = getIntent();
		ImageView imageView = (ImageView) findViewById(R.id.photo_preview);
		Uri photoUri = Uri.parse(intent.getStringExtra("photoUri"));
		
		try {
			Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),photoUri);
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
	
	private void savePictureCommented(){
		_editTextComment = (EditText)findViewById(R.id.edit_text_comment);
		String data = _editTextComment.getText().toString();
		// TODO
		//recuperation du fichier local de stockage
//		try {
//	         FileOutputStream fOut = openFileOutput("photos_backup",MODE_WORLD_READABLE);
//	         fOut.write(data.getBytes());
//	         fOut.close();
//	         Toast.makeText(getBaseContext(),"file saved",
//	         Toast.LENGTH_SHORT).show();
//	      } catch (Exception e) {
//	         e.printStackTrace();
//	      }
	}
}
