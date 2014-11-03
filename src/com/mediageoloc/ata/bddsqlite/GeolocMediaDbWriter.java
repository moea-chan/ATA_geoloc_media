package com.mediageoloc.ata.bddsqlite;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.mediageoloc.ata.bddsqlite.GeolocMediaContract.Collaborators;
import com.mediageoloc.ata.bddsqlite.GeolocMediaContract.Users;
import com.mediageoloc.ata.user.User;

/**
 * GeolocMediaDbWriter : Class for writting access on local database
 * @author Thierry
 *
 */
public class GeolocMediaDbWriter {

	
	/**
	 * writeUserDB : write user in local Database from fields
	 * @param ctx
	 * @param id
	 * @param nom
	 * @param prenom
	 * @param mail
	 * @param telephone
	 * @return
	 */
	public static long writeUserDB(Context ctx, long id, String nom, String prenom, String mail, String telephone ){
		
		GeolocMediaDbHelper helper = new GeolocMediaDbHelper(ctx);

		SQLiteDatabase db = helper.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(Users.COLUMN_NAME_USER_ID, id);
		values.put(Users.COLUMN_NAME_NOM, nom);
		values.put(Users.COLUMN_NAME_PRENOM, prenom);
		values.put(Users.COLUMN_NAME_MAIL, mail);
		values.put(Users.COLUMN_NAME_TELEPHONE, telephone);
		
		long newRowId = db.insert(
		         Users.TABLE_NAME,
		         null, // nullColumnHack
		         values);
		
		db.close();
		
		return newRowId;

	}
	
	/**
	 * writeUserDB : write user in local Database from User instance
	 * @param ctx
	 * @param id
	 * @param user
	 * @return
	 */
	public static long writeUserDB(Context ctx, long id, User user){
		
		GeolocMediaDbHelper helper = new GeolocMediaDbHelper(ctx);
		SQLiteDatabase db = helper.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(Users.COLUMN_NAME_USER_ID, id);
		values.put(Users.COLUMN_NAME_NOM, user.getUserLastName());
		values.put(Users.COLUMN_NAME_PRENOM, user.getUserFirstName());
		values.put(Users.COLUMN_NAME_MAIL, user.getUserMail());
		values.put(Users.COLUMN_NAME_TELEPHONE, user.getUserPhone());
		
		long newRowId = db.insert(
		         Users.TABLE_NAME,
		         null, // nullColumnHack
		         values);
		
		db.close();
		
		return newRowId;

	}

	/**
	 * writeUserDB : write user in local Database from List of User instance
	 * @param ctx
	 * @param id
	 * @param user
	 * @return
	 */
	public static void writeListUserDB(Context ctx, ArrayList<User> listUser){
		
		GeolocMediaDbHelper helper = new GeolocMediaDbHelper(ctx);
		SQLiteDatabase db = helper.getWritableDatabase();

		db.beginTransaction();
		try {
		           
			    for(int i = 0; i < listUser.size(); i++)
			    {
					ContentValues values = new ContentValues();
					//values.put(Users.COLUMN_NAME_USER_ID, id);
					values.put(Users.COLUMN_NAME_NOM, (listUser.get(i)).getUserLastName());
					values.put(Users.COLUMN_NAME_PRENOM, (listUser.get(i)).getUserFirstName());
					values.put(Users.COLUMN_NAME_MAIL, (listUser.get(i)).getUserMail());
					values.put(Users.COLUMN_NAME_TELEPHONE, (listUser.get(i)).getUserPhone());
					
					long newRowId = db.insert(Users.TABLE_NAME, null, values);
				                 
				    db.yieldIfContendedSafely();
			    }
			    db.setTransactionSuccessful(); // commit
			  } 
		    finally {
		    	db.endTransaction(); // rollback
		    }
		    db.close();
		
	}


public static long writeCollaboratorDB(Context ctx, long id, String login, String html_url){
		
		GeolocMediaDbHelper helper = new GeolocMediaDbHelper(ctx);

		SQLiteDatabase db = helper.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(Collaborators.COLUMN_NAME_COLLABORATOR_ID, id);
		values.put(Collaborators.COLUMN_NAME_LOGIN, login);
		values.put(Collaborators.COLUMN_NAME_HTML_URL, html_url);
		
		long newRowId = db.insert(
		         Users.TABLE_NAME,
		         null, // nullColumnHack
		         values);
		
		db.close();
		
		return newRowId;

	}
	
public static void writeListCollaboratorDB(Context ctx, ArrayList<Collaborator> listCollaborator){
	
	GeolocMediaDbHelper helper = new GeolocMediaDbHelper(ctx);
	SQLiteDatabase db = helper.getWritableDatabase();

	db.beginTransaction();
	try {
	           
		    for(int i = 0; i < listCollaborator.size(); i++)
		    {
				ContentValues values = new ContentValues();
				//values.put(Users.COLUMN_NAME_USER_ID, id);
				values.put(Collaborators.COLUMN_NAME_COLLABORATOR_ID, (listCollaborator.get(i)).id);
				values.put(Collaborators.COLUMN_NAME_LOGIN, (listCollaborator.get(i)).login);
				values.put(Collaborators.COLUMN_NAME_HTML_URL, (listCollaborator.get(i)).html_url);

				long newRowId = db.insert(Collaborators.TABLE_NAME, null, values);
			                 
			    db.yieldIfContendedSafely();
		    }
		    db.setTransactionSuccessful(); // commit
		  } 
	    finally {
	    	db.endTransaction(); // rollback
	    }
	    db.close();
	
}
}
