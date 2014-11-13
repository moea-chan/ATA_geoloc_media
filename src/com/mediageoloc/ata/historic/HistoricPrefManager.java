package com.mediageoloc.ata.historic;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;

public class HistoricPrefManager {

	private static SharedPreferences sharedPreferences;
	
	public static final String HISTORIC_PREFS_NAME = "historiquePreferences";
	
	public static final String FILE_PATH = "filePath";
	public static final String COMMENT = "commentaire";
	
	
	/**
	 * initHistoriquePreferences()
	 * 
	 * -Verifie l'existence de l'historiquePreferences et le complete eventuellement
	 * 		avec filePathX pour les URI et commentaireX pour les commentaires (0 < X < 11)
	 */
	public static void initHistoriquePreferences(Context context){	
		sharedPreferences  =  context.getSharedPreferences(HISTORIC_PREFS_NAME, Context.MODE_PRIVATE);
		
		String existHisto = sharedPreferences.getString(FILE_PATH+"10", "%&%p%&defValue");
		String stKey;
		String stValue = "";
		
		if (existHisto.equals("%&%p%&defValue")){
			
			//Historique inexistant => Création
			Editor editor= sharedPreferences.edit();
			for (int i =1;i<11;i++){
				stKey= FILE_PATH + String.valueOf(i);
				editor.putString(stKey,stValue);
				stKey= COMMENT + String.valueOf(i);
				editor.putString(stKey,stValue);
			}
			editor.commit();
		}		
	}
	
	/**
	 * addHistoriquePreferences : Ajout d'un media commenté dans l'historique
	 * @param uri
	 * @param commentaire
	 */
	public static void addHistoriquePreferences(Uri uri, String commentaire, Context context){
		sharedPreferences  =  context.getSharedPreferences(HISTORIC_PREFS_NAME, Context.MODE_PRIVATE);
		
		String stKey;
		String stValue = "";
		
		//Decalage de l'Historique existant
		Editor editor = sharedPreferences.edit();
		for (int i = 9; i > 0; i--){
			stKey= FILE_PATH + String.valueOf(i);
			stValue = sharedPreferences.getString(stKey, "%&%p%&defValue");
			
			if (!stValue.equals("%&%p%&defValue")){
				stKey= FILE_PATH + String.valueOf(i+1);
				editor.putString(stKey,stValue);	
			
				stKey= COMMENT + String.valueOf(i);
				stValue = sharedPreferences.getString(stKey, "%&%p%&defValue");				
				
				stKey= COMMENT + String.valueOf(i+1);
				editor.putString(stKey,stValue);	
				
			}
		}
		//insertion nouvelles donnees en position 1
		stKey= FILE_PATH + "1";
		stValue = uri.toString();
		editor.putString(stKey,stValue);	
		
		stKey= COMMENT + "1";
		stValue = commentaire;
		editor.putString(stKey,stValue);	
		
		editor.commit();
	}

	
}
