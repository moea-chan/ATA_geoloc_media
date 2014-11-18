package com.mediageoloc.ata.utils.contentProvider;

import java.util.HashMap;

import com.mediageoloc.ata.utils.contentProvider.MediaGeolocContract.Medias;
import com.mediageoloc.ata.utils.contentProvider.MediaGeolocContract.Users;

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
import android.provider.SyncStateContract.Columns;
import android.text.TextUtils;

public class GeolocProvider extends ContentProvider {

	static final String PROVIDER_NAME = "com.mediageoloc.ata";
	static final String URL = "content://" + PROVIDER_NAME;

	public static final Uri USERS_CONTENT_URI = Uri.parse(URL + "/" + Users.USERS_TABLE_NAME);
	public static final Uri FOLLOWERS_CONTENT_URI = Uri.parse(URL + "/" + Users.FOLLOWERS_NAME);
	public static final Uri CONTENT_URI_MEDIAS = Uri.parse(URL + "/" + Medias.TABLE_NAME);


	static final String _ID = "_id";

	private static HashMap<String, String> USERS_PROJECTION_MAP;
	private static HashMap<String, String> MEDIAS_PROJECTION_MAP;

	static final int USERS = 1;
	static final int FOLLOWERS = 2;
	static final int USER_ID = 3;
	static final int UMEDIAS = 4;
	static final int FOLLOWER_ID = 5;

	static final UriMatcher uriMatcher;
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(PROVIDER_NAME, Users.USERS_TABLE_NAME, USERS);
		uriMatcher.addURI(PROVIDER_NAME, Users.FOLLOWERS_NAME, FOLLOWERS);
		uriMatcher.addURI(PROVIDER_NAME, Users.USERS_TABLE_NAME + "/#", USER_ID);
		uriMatcher.addURI(PROVIDER_NAME, Medias.TABLE_NAME, UMEDIAS);
		uriMatcher.addURI(PROVIDER_NAME, Users.FOLLOWERS_NAME + "/#", FOLLOWER_ID);
	}

	/**
	 * Database specific constant declarations
	 */
	private SQLiteDatabase db;
	static final String DATABASE_NAME = "MediaGeoLoc";
	static final String USERS_TABLE_NAME = Users.USERS_TABLE_NAME;
	static final int DATABASE_VERSION = 4;

	private static final String SQL_DELETE_USERS = "DROP TABLE IF EXISTS " + Users.USERS_TABLE_NAME + ";";
	private static final String SQL_CREATE_USERS = 
			"CREATE TABLE " + Users.USERS_TABLE_NAME + " (" + 
					Users._ID + " INTEGER PRIMARY KEY," + 
					Users.COLUMN_NAME_NOM + " TEXT," + 
					Users.COLUMN_NAME_PRENOM + " TEXT," + 
					Users.COLUMN_NAME_MAIL + " TEXT," + 
					Users.COLUMN_NAME_TELEPHONE + " TEXT," + 
					Users.COLUMN_NAME_FOLLOWED + " INTEGER" + 
					" );";

	private static final String SQL_DELETE_MEDIAS = "DROP TABLE IF EXISTS " + Medias.TABLE_NAME + ";";
	private static final String SQL_CREATE_MEDIAS =
			"CREATE TABLE " + Medias.TABLE_NAME + " (" +
					Medias._ID + " INTEGER PRIMARY KEY," +
					Medias.COLUMN_NAME_CHEMINFICHIER + " TEXT," +
					Medias.COLUMN_NAME_COMMENTAIRE + " TEXT," +
					Medias.COLUMN_NAME_LATITUDE + " REAL," +
					Medias.COLUMN_NAME_LONGITUDE + " REAL," +
					Medias.COLUMN_NAME_TYPEMEDIA + " TEXT" +
					" );";

	/**
	 * Helper class that actually creates and manages the provider's underlying
	 * data repository.
	 */
	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(SQL_CREATE_USERS);
			db.execSQL(SQL_CREATE_MEDIAS);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL(SQL_DELETE_USERS);
			db.execSQL(SQL_DELETE_MEDIAS);
			onCreate(db);
		}

		@Override
		public void onDowngrade(SQLiteDatabase db, int oldVersion,
				int newVersion) {
			onUpgrade(db, oldVersion, newVersion);
		}
	}

	@Override
	synchronized public boolean onCreate() {
		Context context = getContext();
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		/**
		 * Create a writable database which will trigger its creation if it
		 * doesn't already exist.
		 */
		db = dbHelper.getWritableDatabase();
		return (db == null) ? false : true;
	}

	@Override
	synchronized public Uri insert(Uri uri, ContentValues values) {
		/**
		 * Add a new record
		 */
		long rowID;

		switch (uriMatcher.match(uri)) {
		case USERS:
			rowID = db.insert(USERS_TABLE_NAME, null, values);
			if (rowID > 0) {
				Uri _uri = ContentUris.withAppendedId(USERS_CONTENT_URI, rowID);
				getContext().getContentResolver().notifyChange(uri, null);
				return _uri;
			}
			break;
		case FOLLOWERS:
			rowID = db.insert(USERS_TABLE_NAME, null, values);
			if (rowID > 0) {
				Uri _uri = ContentUris.withAppendedId(FOLLOWERS_CONTENT_URI,
						rowID);
				getContext().getContentResolver().notifyChange(_uri, null);
				return _uri;
			}
			break;

		case UMEDIAS:
			rowID = db.insert(Medias.TABLE_NAME, null, values);
			if (rowID > 0)
			{
				Uri _uri = ContentUris.withAppendedId(CONTENT_URI_MEDIAS, rowID);
				getContext().getContentResolver().notifyChange(_uri, null);
				return _uri;
			}
			throw new SQLException("Failed to add a record into " + uri);
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		throw new SQLException("Failed to add a record into " + uri);
	}

	@Override
	synchronized public Cursor query(Uri uri, String[] projection,
			String selection, String[] selectionArgs, String sortOrder) {

		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		Cursor c = null;
		String selection_ = null;

		switch (uriMatcher.match(uri)) {
		case USERS:
			qb.setTables(USERS_TABLE_NAME);
			qb.setProjectionMap(USERS_PROJECTION_MAP);
			selection_ = Users.COLUMN_NAME_FOLLOWED+" = 0";
			c = qb.query(db, projection, selection_, selectionArgs, null, null,
					sortOrder, "10");
			break;
		case FOLLOWERS:
			qb.setTables(USERS_TABLE_NAME);
			qb.setProjectionMap(USERS_PROJECTION_MAP);
			selection_ = Users.COLUMN_NAME_FOLLOWED+" = 1";
			//			String[] selectionArgs_ = new String[] { USERS_TABLE_NAME + "."
			//					+ Users.COLUMN_NAME_FOLLOWED };
			c = qb.query(db, projection, selection_, selectionArgs, null, null,
					sortOrder, "10");
			break;
		case USER_ID:
			qb.setTables(USERS_TABLE_NAME);
			qb.setProjectionMap(USERS_PROJECTION_MAP);
			qb.appendWhere(_ID + "=" + uri.getPathSegments().get(1));
			c = qb.query(db, projection, selection, selectionArgs, null, null,
					null, "10");
			break;
		case UMEDIAS:
			qb.setTables(Medias.TABLE_NAME);
			qb.setProjectionMap(MEDIAS_PROJECTION_MAP);
			c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder, "10");
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		/**
		 * register to watch a content URI for changes
		 */
		if (c != null) {
			c.setNotificationUri(getContext().getContentResolver(), uri);
		}

		return c;
	}

	@Override
	synchronized public int delete(Uri uri, String selection, String[] selectionArgs) {
		int count = 0;

		// switch (uriMatcher.match(uri)){
		// case STUDENTS:
		// count = db.delete(STUDENTS_TABLE_NAME, selection, selectionArgs);
		// break;
		// case STUDENT_ID:
		// String id = uri.getPathSegments().get(1);
		// count = db.delete( STUDENTS_TABLE_NAME, _ID + " = " + id +
		// (!TextUtils.isEmpty(selection) ? " AND (" +
		// selection + ')' : ""), selectionArgs);
		// break;
		// default:
		// throw new IllegalArgumentException("Unknown URI " + uri);
		// }
		//
		// getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	synchronized public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {

		int mRowsUpdated = 0;
		int idStr;
		String where;
		switch (uriMatcher.match(uri)){
		
		case FOLLOWER_ID://set follower field to false
			idStr = Integer.parseInt(uri.getLastPathSegment());
			where = Columns._ID + " = " + idStr;
			if (!TextUtils.isEmpty(selection)) {
				where += " AND " + selection;
			}
			// Defines a variable to contain the number of updated rows
			mRowsUpdated = db.update(
					Users.USERS_TABLE_NAME,
					values,
					where,
					selectionArgs);
			break;
		
		case USER_ID://set follower field to true
			idStr = Integer.parseInt(uri.getLastPathSegment());
			where = Columns._ID + " = " + idStr;
			if (!TextUtils.isEmpty(selection)) {
				where += " AND " + selection;
			}
			// Defines a variable to contain the number of updated rows
			mRowsUpdated = db.update(
					Users.USERS_TABLE_NAME,
					values,
					where,
					selectionArgs);
			break;
		}
		if (mRowsUpdated > 0) {
			//avertit les observer de l'uri followers et users
			getContext().getContentResolver().notifyChange(GeolocProvider.FOLLOWERS_CONTENT_URI, null);
			getContext().getContentResolver().notifyChange(GeolocProvider.USERS_CONTENT_URI, null);
		}
		return mRowsUpdated;

	}

	@Override
	public String getType(Uri uri) {
		// switch (uriMatcher.match(uri)){
		// /**
		// * Get all student records
		// */
		// case STUDENTS:
		// return "vnd.android.cursor.dir/vnd.example.students";
		// /**
		// * Get a particular student
		// */
		// case STUDENT_ID:
		// return "vnd.android.cursor.item/vnd.example.students";
		// default:
		// throw new IllegalArgumentException("Unsupported URI: " + uri);
		// }
		return "TODO";
	}

}
