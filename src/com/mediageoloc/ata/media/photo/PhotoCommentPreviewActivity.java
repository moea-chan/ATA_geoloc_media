package com.mediageoloc.ata.media.photo;



import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.mediageoloc.ata.R;
import com.mediageoloc.ata.historic.HistoricPrefManager;
import com.mediageoloc.ata.media.TakeMediaActivity;

public class PhotoCommentPreviewActivity extends Activity {
	
	private Button buttonSaveComment;
	private EditText editTextComment;
	private Uri photoUri;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_comment_preview);
		
		addButtonSaveCommentListener();
		
		//display taken picture
		Intent intent = getIntent();
		ImageView imageView = (ImageView) findViewById(R.id.photo_preview);
		editTextComment = (EditText) findViewById(R.id.edit_text_comment);
		photoUri = Uri.parse(intent.getStringExtra("photoUri"));
		Bitmap bitmap = PhotoUtils.loadPhotoInImageViewByUri(this.getContentResolver(), intent.getStringExtra("photoUri"), 190, 140);
		imageView.setImageBitmap(bitmap);
		
	}
	
	private void addButtonSaveCommentListener(){
		buttonSaveComment = (Button) findViewById(R.id.button_save_comment);
		buttonSaveComment.setOnClickListener(new View.OnClickListener() {
            @Override
			public void onClick(View v) {
            	savePictureCommented();
            }
        });
	}
	
	private void savePictureCommented(){
		HistoricPrefManager.addHistoriquePreferences(photoUri, editTextComment.getText().toString(), getApplicationContext());
		returnTakeMediaActivity();
	}
	
	
	private void returnTakeMediaActivity(){
		
		Intent intent = new Intent(this, TakeMediaActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(intent);
        //on masque l'activité courante
        finish();
        
	}
	
	
}
