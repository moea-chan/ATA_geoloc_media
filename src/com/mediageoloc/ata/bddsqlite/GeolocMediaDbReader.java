package com.mediageoloc.ata.bddsqlite;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mediageoloc.ata.bddsqlite.GeolocMediaContract.Collaborators;
import com.mediageoloc.ata.bddsqlite.GeolocMediaContract.Users;

public class GeolocMediaDbReader {
	
	private static String[] projectionCollaborator = {
		    Collaborators._ID,
		    Collaborators.COLUMN_NAME_COLLABORATOR_ID,
		    Collaborators.COLUMN_NAME_LOGIN,
		    Collaborators.COLUMN_NAME_HTML_URL
		    };

	public static void readUsersDB(Context ctx){
		GeolocMediaDbHelper helper = new GeolocMediaDbHelper(ctx);
		SQLiteDatabase db = helper.getReadableDatabase();

		String[] projection = {
			    Users._ID,
			    Users.COLUMN_NAME_USER_ID,
			    Users.COLUMN_NAME_NOM,
			    Users.COLUMN_NAME_PRENOM,
			    Users.COLUMN_NAME_MAIL,
			    Users.COLUMN_NAME_TELEPHONE
			    };

		Cursor cursor = db.query(
			    Users.TABLE_NAME,      // The table to query
			    projection,            // The columns to return, null = ALL
			    null,             // WHERE clause, using ?s for values
			    null,         // The values for the WHERE clause
			    null,               // GROUP BY clause (rows grouping)
			    null,                // HAVING clause (group filtering)
			    null              // ORDER BY clause
			    );

		
		cursor.moveToFirst();
		while (! cursor.isAfterLast()) {
		    long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(Users._ID));
		    String itemUserId = cursor.getString(cursor.getColumnIndexOrThrow(Users.COLUMN_NAME_USER_ID));
		    String itemNom = cursor.getString(cursor.getColumnIndexOrThrow(Users.COLUMN_NAME_NOM));
		    String itemPrenom = cursor.getString(cursor.getColumnIndexOrThrow(Users.COLUMN_NAME_PRENOM));
		    String itemMail = cursor.getString(cursor.getColumnIndexOrThrow(Users.COLUMN_NAME_MAIL));
		    String itemTelephone = cursor.getString(cursor.getColumnIndexOrThrow(Users.COLUMN_NAME_TELEPHONE));
		    cursor.moveToNext();
		}
		cursor.close();
		
		db.close();
	}

	
	public static void readCollaboratorsDB(Context ctx){
		
		ArrayList<Collaborator> arrayListCollaborator = new ArrayList<Collaborator>();
		GeolocMediaDbHelper helper = new GeolocMediaDbHelper(ctx);
		SQLiteDatabase db = helper.getReadableDatabase();

		
		Cursor cursor = db.query(
			    Collaborators.TABLE_NAME,      // The table to query
			    projectionCollaborator,            // The columns to return, null = ALL
			    null,             // WHERE clause, using ?s for values
			    null,         // The values for the WHERE clause
			    null,               // GROUP BY clause (rows grouping)
			    null,                // HAVING clause (group filtering)
			    null              // ORDER BY clause
			    );

		
		cursor.moveToFirst();
		while (! cursor.isAfterLast()) {
			long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(Users._ID));
		    String itemCollId = cursor.getString(cursor.getColumnIndexOrThrow(Users.COLUMN_NAME_USER_ID));
		    String itemCollLogin = cursor.getString(cursor.getColumnIndexOrThrow(Users.COLUMN_NAME_NOM));
		    String itemCollHtml = cursor.getString(cursor.getColumnIndexOrThrow(Users.COLUMN_NAME_PRENOM));
		    cursor.moveToNext();
		}
		cursor.close();
		
		db.close();
	}
	public static ArrayList<Collaborator> readListCollaboratorsDB(Context ctx){
		
		ArrayList<Collaborator> arrayListCollaborator = new ArrayList<Collaborator>();
		GeolocMediaDbHelper helper = new GeolocMediaDbHelper(ctx);
		SQLiteDatabase db = helper.getReadableDatabase();

		String[] projectionCollaborator = {
			    Collaborators._ID,
			    Collaborators.COLUMN_NAME_COLLABORATOR_ID,
			    Collaborators.COLUMN_NAME_LOGIN,
			    Collaborators.COLUMN_NAME_HTML_URL
			    };
		
		Cursor cursor = db.query(
			    Collaborators.TABLE_NAME,      // The table to query
			    projectionCollaborator,            // The columns to return, null = ALL
			    null,             // WHERE clause, using ?s for values
			    null,         // The values for the WHERE clause
			    null,               // GROUP BY clause (rows grouping)
			    null,                // HAVING clause (group filtering)
			    null              // ORDER BY clause
			    );

		
		cursor.moveToFirst();
		while (! cursor.isAfterLast()) {
			Collaborator newCollaborator = new Collaborator();
			newCollaborator.setId(Long.toString((cursor.getLong(cursor.getColumnIndexOrThrow(Collaborators.COLUMN_NAME_COLLABORATOR_ID)))));
			newCollaborator.setLogin(cursor.getString(cursor.getColumnIndexOrThrow(Collaborators.COLUMN_NAME_LOGIN)));
			newCollaborator.setHtml_url(cursor.getString(cursor.getColumnIndexOrThrow(Collaborators.COLUMN_NAME_COLLABORATOR_ID)));
			arrayListCollaborator.add(newCollaborator);
		    cursor.moveToNext();
		}
		cursor.close();
		
		db.close();
		
		return arrayListCollaborator;
	}

}
