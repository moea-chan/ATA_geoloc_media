package com.mediageoloc.ata.user;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.mediageoloc.ata.utils.MediaGeolocContract.Users;

public class UserProvider extends ContentProvider {

   static final String PROVIDER_NAME = "com.mediageoloc.ata";
   static final String URL = "content://" + PROVIDER_NAME + "/" + Users.TABLE_NAME;
   public static final Uri CONTENT_URI = Uri.parse(URL);

   static final String _ID = "_id";

   private static HashMap<String, String> USERS_PROJECTION_MAP;

   static final int USERS = 1;
   static final int USER_ID = 2;

   static final UriMatcher uriMatcher;
   static{
      uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
      uriMatcher.addURI(PROVIDER_NAME, Users.TABLE_NAME, USERS);
      uriMatcher.addURI(PROVIDER_NAME, Users.TABLE_NAME + "/#", USER_ID);
   }

   /**
    * Database specific constant declarations
    */
   private SQLiteDatabase db;
   static final String DATABASE_NAME = "MediaGeoLoc";
   static final String USERS_TABLE_NAME = Users.TABLE_NAME;
   static final int DATABASE_VERSION = 2;
   
   private static final String SQL_DELETE_USERS = "DROP TABLE IF EXISTS " + Users.TABLE_NAME + ";";
   private static final String SQL_CREATE_USERS =
   	    "CREATE TABLE " + Users.TABLE_NAME + " (" +
   	    Users._ID + " INTEGER PRIMARY KEY," +
   	    Users.COLUMN_NAME_NOM + " TEXT," +
   	    Users.COLUMN_NAME_PRENOM + " TEXT," +
   	    Users.COLUMN_NAME_MAIL + " TEXT," +
   	    Users.COLUMN_NAME_TELEPHONE + " TEXT" +
   	    " );";

   /**
    * Helper class that actually creates and manages 
    * the provider's underlying data repository.
    */
   private static class DatabaseHelper extends SQLiteOpenHelper {
       DatabaseHelper(Context context){
          super(context, DATABASE_NAME, null, DATABASE_VERSION);
       }

       @Override
       public void onCreate(SQLiteDatabase db){
          db.execSQL(SQL_CREATE_USERS);
       }
       
       @Override
       public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	  db.execSQL("DROP TABLE IF EXISTS user;");
          db.execSQL("DROP TABLE IF EXISTS " +  USERS_TABLE_NAME + ";");
          onCreate(db);
       }
   }

   @Override
   synchronized public boolean onCreate() {
      Context context = getContext();
      DatabaseHelper dbHelper = new DatabaseHelper(context);
      /**
       * Create a writable database which will trigger its 
       * creation if it doesn't already exist.
       */
      db = dbHelper.getWritableDatabase();
      return (db == null)? false:true;
   }

   @Override
   synchronized public Uri insert(Uri uri, ContentValues values) {
      /**
       * Add a new record
       */
      long rowID = db.insert(USERS_TABLE_NAME, null, values);
      /** 
       * If record is added successfully
       */
      if (rowID > 0)
      {
         Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
         getContext().getContentResolver().notifyChange(_uri, null);
         return _uri;
      }
      throw new SQLException("Failed to add a record into " + uri);
   }

   
   
   @Override
   synchronized public Cursor query(Uri uri, String[] projection, String selection,	
                       String[] selectionArgs, String sortOrder) {
      
      SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
      qb.setTables(USERS_TABLE_NAME);
      
      switch (uriMatcher.match(uri)) {
      case USERS:
         qb.setProjectionMap(USERS_PROJECTION_MAP);
         break;
      case USER_ID:
         qb.appendWhere( _ID + "=" + uri.getPathSegments().get(1));
         break;
      default:
         throw new IllegalArgumentException("Unknown URI " + uri);
      }
      
      Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, null, "10");
    		 
      /** 
       * register to watch a content URI for changes
       */
      c.setNotificationUri(getContext().getContentResolver(), uri);

      return c;
   }

   @Override
   public int delete(Uri uri, String selection, String[] selectionArgs) {
      int count = 0;

//      switch (uriMatcher.match(uri)){
//      case STUDENTS:
//         count = db.delete(STUDENTS_TABLE_NAME, selection, selectionArgs);
//         break;
//      case STUDENT_ID:
//         String id = uri.getPathSegments().get(1);
//         count = db.delete( STUDENTS_TABLE_NAME, _ID +  " = " + id + 
//                (!TextUtils.isEmpty(selection) ? " AND (" + 
//                selection + ')' : ""), selectionArgs);
//         break;
//      default: 
//         throw new IllegalArgumentException("Unknown URI " + uri);
//      }
//      
//      getContext().getContentResolver().notifyChange(uri, null);
      return count;
   }

   @Override
   public int update(Uri uri, ContentValues values, String selection, 
                     String[] selectionArgs) {
      int count = 0;
      
//      switch (uriMatcher.match(uri)){
//      case STUDENTS:
//         count = db.update(STUDENTS_TABLE_NAME, values, 
//                 selection, selectionArgs);
//         break;
//      case STUDENT_ID:
//         count = db.update(STUDENTS_TABLE_NAME, values, _ID + 
//                 " = " + uri.getPathSegments().get(1) + 
//                 (!TextUtils.isEmpty(selection) ? " AND (" +
//                 selection + ')' : ""), selectionArgs);
//         break;
//      default: 
//         throw new IllegalArgumentException("Unknown URI " + uri );
//      }
//      getContext().getContentResolver().notifyChange(uri, null);
      return count;
   }

   @Override
   public String getType(Uri uri) {
//      switch (uriMatcher.match(uri)){
//      /**
//       * Get all student records 
//       */
//      case STUDENTS:
//         return "vnd.android.cursor.dir/vnd.example.students";
//      /** 
//       * Get a particular student
//       */
//      case STUDENT_ID:
//         return "vnd.android.cursor.item/vnd.example.students";
//      default:
//         throw new IllegalArgumentException("Unsupported URI: " + uri);
//      }
	   return "TODO";
   }

}


