package com.mediageoloc.ata.bddsqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mediageoloc.ata.bddsqlite.GeolocMediaContract.Collaborators;
import com.mediageoloc.ata.bddsqlite.GeolocMediaContract.Users;

/**
 * GeolocMediaDbHelper : helper
 * @author Thierry
 *
 */
public class GeolocMediaDbHelper extends SQLiteOpenHelper {

	    // Bump this for each change in the schema
	    public static final int DATABASE_VERSION = 1;
	    public static final String DATABASE_NAME = "geolocmedia.db";
	    public static final String SQL_CREATE_USERS =
	    	    "CREATE TABLE " + Users.TABLE_NAME + " (" +
	    	    Users._ID + " INTEGER PRIMARY KEY," +
	    	    Users.COLUMN_NAME_USER_ID + " TEXT," +
	    	    Users.COLUMN_NAME_NOM + " TEXT," +
	    	    Users.COLUMN_NAME_PRENOM + " TEXT," +
	    	    Users.COLUMN_NAME_MAIL + " TEXT," +
	    	    Users.COLUMN_NAME_TELEPHONE + " TEXT" +
	    	    " )"; 
	    public static final String SQL_CREATE_COLLABORATORS =
	    	    "CREATE TABLE " + Collaborators.TABLE_NAME + " (" +
    	    		Collaborators._ID + " INTEGER PRIMARY KEY," +
    	    		Collaborators.COLUMN_NAME_COLLABORATOR_ID + " TEXT," +
    	    		Collaborators.COLUMN_NAME_LOGIN + " TEXT," +
    	    		Collaborators.COLUMN_NAME_HTML_URL + " TEXT" +
	    	    " )"; 
	    
	    private static final String SQL_DELETE_USERS = "DROP TABLE IF EXISTS " + Users.TABLE_NAME;
	    private static final String SQL_DELETE_COLLABORATORS = "DROP TABLE IF EXISTS " + Collaborators.TABLE_NAME;
	    
	    
	    public GeolocMediaDbHelper(Context context) {
	        super(context, DATABASE_NAME, null, DATABASE_VERSION);
	    }

	    public void onCreate(SQLiteDatabase db) {
	        db.execSQL(SQL_CREATE_USERS);
	        db.execSQL(SQL_CREATE_COLLABORATORS);
	    }

	    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	        // This database upgrade policy is to discard the data
	    	db.execSQL(SQL_DELETE_USERS);
	    	db.execSQL(SQL_DELETE_COLLABORATORS);
	        
	        onCreate(db);
	    }


}
