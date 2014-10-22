package com.mediageoloc.ata;


import java.net.URI;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class PhotoCommentPreviewActivity extends Activity {

 protected ImageView imgFavorite;
 protected EditText leCommentaire;
 protected URI photoURI;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_photo_comment_preview);
      //setContentView(R.layout.effet_nb);
      imgFavorite = (ImageView)findViewById(R.id.imageView1);
      leCommentaire = (EditText)findViewById(R.id.editTextComment);
      
      Button boutonSave = (Button) findViewById(R.id.buttonSave);
      
      //TODO: A supprimer  pour version en chargement URI
      // photoURI = .....
    
      boutonSave.setOnClickListener(new View.OnClickListener() {
      	@Override
          public void onClick(View v) {
          // Lancer l'activit� 1
      		saveComment();
           }
      });   
    /*  open();
      */
      // fin a supprimer
   }
   
   /**
    * Lance l'appli par défault
    * 
    */
  /* public void open(){
	 
      Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
      startActivityForResult(intent, 0);
   }

   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      // TODO Auto-generated method stub
      super.onActivityResult(requestCode, resultCode, data);
      //Le resultat est recupéré
      Bitmap bp = (Bitmap) data.getExtras().get("data");
      imgFavorite.setImageBitmap(bp);
   }
   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.main, menu);
      return true;
   }
  */
   
   //Sauvegarde sur click button
   public void saveComment()
   {
	   //TODO : tout faire !!
	   String stComment = (leCommentaire.getText()).toString();
	   
   }
}
