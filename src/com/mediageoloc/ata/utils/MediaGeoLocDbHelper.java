package com.mediageoloc.ata.utils;

import com.mediageoloc.ata.utils.MediaGeolocContract.Users;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MediaGeoLocDbHelper extends SQLiteOpenHelper {

	// Bump this for each change in the schema
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MediaGeoLoc";

    public MediaGeoLocDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USERS);
    }

	private static final String SQL_DELETE_USERS = "DROP TABLE IF EXISTS " + Users.TABLE_NAME;
	private static final String SQL_CREATE_USERS =
    	    "CREATE TABLE " + Users.TABLE_NAME + " (" +
    	    Users._ID + " INTEGER PRIMARY KEY," +
    	    Users.COLUMN_NAME_NOM + " TEXT," +
    	    Users.COLUMN_NAME_PRENOM + " TEXT," +
    	    Users.COLUMN_NAME_MAIL + " TEXT," +
    	    Users.COLUMN_NAME_TELEPHONE + " TEXT" +
    	    " )";
	
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database upgrade policy is to discard the data
        db.execSQL(SQL_DELETE_USERS);
        onCreate(db);
    }
    

}
