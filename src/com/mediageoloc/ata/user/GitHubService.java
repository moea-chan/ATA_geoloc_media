package com.mediageoloc.ata.user;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import com.mediageoloc.ata.utils.MediaGeoLocDbHelper;
import com.mediageoloc.ata.utils.MediaGeolocContract.Users;

import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;
import android.R.array;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class GitHubService
{
	
	final static public String GITHUB_DOMAIN = "https://api.github.com" ;  // The base API endpoint.
		
	public class Contributor {
		public String login; // GitHub username.
		public int contributions; // Commit count.
	}

	public interface GitHubServiceStub 
	{
		@GET("/repos/{owner}/{repo}/contributors")
		List<Contributor> contributors(@Path("owner") String owner,
									   @Path("repo") String repo);
		
		@GET("/users")
		Observable<List<User>> users();
	}

	public void getUsers(final Context context){	
		 RestAdapter restAdapter = new RestAdapter.Builder()
		 .setEndpoint(GITHUB_DOMAIN)
		 .build();
		
		 GitHubServiceStub service = restAdapter.create(GitHubServiceStub.class);
		 Observable<List<User>> users = service.users();
		
		 users.subscribeOn(Schedulers.io())
		 .observeOn(Schedulers.io())
		 .subscribe(new Observer<List<User>>() {
//			 List<User>
			 @Override
			 public void onCompleted() {}
			 @Override
			 public void onError(Throwable e) {
				 Log.d("debug", e.getMessage());
			 }
			 @Override
			 public void onNext(List<User> arg0) {
				 updateUsersInDatabase(context, arg0);
			 }
		 });
	}
//	List<User>
	private void updateUsersInDatabase(Context context, List<User> usersList){
		MediaGeoLocDbHelper helper = new MediaGeoLocDbHelper(context);
		SQLiteDatabase db = helper.getWritableDatabase();
		List<ContentValues> valuesToInsert = new ArrayList<>();

		for (User user : usersList) {
			// Create a new map of values, where column names are the keys
			ContentValues values = new ContentValues();
			values.put(Users.COLUMN_NAME_NOM, user.getUserLastName());
			values.put(Users.COLUMN_NAME_PRENOM, user.getUserFirstName());
			values.put(Users.COLUMN_NAME_MAIL, user.getUserMail());
			values.put(Users.COLUMN_NAME_TELEPHONE, user.getUserPhone());
			
			// Insert, the primary key value of the new row is returned
			valuesToInsert.add(values);
		}
		
		db.beginTransaction();
		try {
		    for (int i= 0; i< valuesToInsert.size(); i++){
		        // TODO prepare ContentValues object values
		        db.insert(Users.TABLE_NAME, null, valuesToInsert.get(i));
		        // In case you do larger updates: allow other threads to read
		        db.yieldIfContendedSafely();
		    }
		    db.setTransactionSuccessful(); // commit} finally {
		    db.endTransaction(); // rollback
		}catch(Throwable t){}
		
		String[] projection = {
			    Users._ID,
			    Users.COLUMN_NAME_NOM,
			    Users.COLUMN_NAME_PRENOM,
			    Users.COLUMN_NAME_MAIL,
			    Users.COLUMN_NAME_TELEPHONE
			    };

		Cursor cursor = db.query(
			    Users.TABLE_NAME,      // The table to query
			    projection,
			    null, null, null, null, null// The columns to return, null = ALL
			    );
		cursor.moveToFirst();
		while (! cursor.isAfterLast()) {
		    String pseudo = cursor.getString(cursor.getColumnIndexOrThrow(Users.COLUMN_NAME_NOM));
		    Log.d("debug", "-------------"+pseudo);
		    cursor.moveToNext();
		}
		cursor.close();

	}
}
