package com.mediageoloc.ata.user.account;

import com.mediageoloc.ata.user.User;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class UserPrefsManager {
	private static SharedPreferences sharedPreferences;

	private static final String USER_PREFS_NAME = "userPreferences";

	private static final String ID = "id";
	private static final String FIRST_NAME = "firstName";
	private static final String LAST_NAME = "lastName";
	private static final String MAIL = "mail";
	private static final String PHONE = "phone";



	/** 
	 * Récupère les infos user si elles existent
	 * sinon renvoie un user null
	 */
	public static User getUserPrefs(Context context){	
		String firstName, lastName, mail, phone = "";

		sharedPreferences  =  context.getSharedPreferences(USER_PREFS_NAME, Context.MODE_PRIVATE);

		firstName = sharedPreferences.getString(FIRST_NAME, "");
		lastName = sharedPreferences.getString(LAST_NAME, "");
		mail = sharedPreferences.getString(MAIL, "");
		phone = sharedPreferences.getString(PHONE, "");

		User user = new User(firstName, lastName, mail, phone);
		return user;
	}

	/**
	 * addHistoriquePreferences : Ajout d'un media commenté dans l'historique
	 * @param uri
	 * @param commentaire
	 */
	public static void setUserPreferences(User user, Context context){
		if(user != null){
			sharedPreferences  =  context.getSharedPreferences(USER_PREFS_NAME, Context.MODE_PRIVATE);
			Editor editor = sharedPreferences.edit();
			editor.clear();
			editor.apply();

			String stKey;
			String stValue = "";

			//insertion données user
			stKey= ID;
			stValue = user.getId();
			editor.putString(stKey,stValue);	

			stKey= FIRST_NAME;
			stValue = user.getUserFirstName();
			editor.putString(stKey,stValue);	

			stKey= LAST_NAME;
			stValue = user.getUserLastName();
			editor.putString(stKey,stValue);	

			stKey= MAIL;
			stValue = user.getUserMail();
			editor.putString(stKey,stValue);	

			stKey= PHONE;
			stValue = user.getUserPhone();
			editor.putString(stKey,stValue);

			editor.apply();
		}
	}
}
