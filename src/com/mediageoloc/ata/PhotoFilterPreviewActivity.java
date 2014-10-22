package com.mediageoloc.ata;

import java.net.URI;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

public class PhotoFilterPreviewActivity extends Activity {

	 protected ImageView imgFavorite;
	 protected EditText leCommentaire;
	 protected URI photoURI;
	
	 

	   @Override
	   protected void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.activity_photo_filter_preview);
	      
	      //setContentView(R.layout.effet_nb);
	      imgFavorite = (ImageView)findViewById(R.id.imageView1);
	      leCommentaire = (EditText)findViewById(R.id.editTextComment);
	      
	      
	      Button boutonnext = (Button) findViewById(R.id.buttonNext);
	
	      boutonnext.setOnClickListener(new View.OnClickListener() {
	      	@Override
	          public void onClick(View v) {
	          // Lancer l'activit� 1
	      		doNext();
	           }
	      });   
	      
       CheckBox filterAction = (CheckBox) findViewById(R.id.checkBoxFilter);
	      
	      
       filterAction.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			    doFilter();
	           }
	      });   
	      
	      //TODO: A supprimer  pour version en chargement URI
	      // photoURI = .....
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
	   public void doFilter()
	   {
		   //TODO : tout faire !!
		   CheckBox filterAction = (CheckBox) findViewById(R.id.checkBoxFilter);
		   
		   if (filterAction.isChecked())
		   {
			   
			      int value=100;
				   
				   
				   Bitmap src = imgFavorite.getDrawingCache();
				   
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
			       imgFavorite.setImageBitmap(bmOut);
		   }
		   
	   }
	   
	   /**
	    * Aller vers Commentaire
	    */
	   public void doNext()
	   {
		   //TODO : tout faire !!
		
		   
	   }
}
