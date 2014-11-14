package com.mediageoloc.ata.user;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.http.GET;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;
import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.mediageoloc.ata.utils.MediaGeolocContract.Users;

public class GitHubService
{
	// The base API endpoint
	final static public String GITHUB_DOMAIN = "https://api.github.com" ;  

	public interface GitHubServiceStub {
		// get all users
		@GET("/users")
		Observable<List<User>> users();
		
	}

	
	public void getUsers(final Context context){	
		 RestAdapter restAdapter = new RestAdapter.Builder()
		 .setEndpoint(GITHUB_DOMAIN)
		 .build();
		
		 //call github service te get all users
		 GitHubServiceStub service = restAdapter.create(GitHubServiceStub.class);
		 Observable<List<User>> users = service.users();
		
		 users.subscribeOn(Schedulers.io())
		 .observeOn(Schedulers.io())
		 .subscribe(new Observer<List<User>>() {
			 @Override
			 public void onCompleted() {}
			 @Override
			 public void onError(Throwable e) {
				 if(e != null)
					 Log.e("network - get users error", e.getMessage());
			 }
			 @Override
			 public void onNext(List<User> arg0) {
				 //insert user in DB
				for (User user : arg0) {
					// Create a new map of values, where column names are the keys
					ContentValues values = new ContentValues();
					values.put(Users.COLUMN_NAME_NOM, user.getUserLastName());
					values.put(Users.COLUMN_NAME_PRENOM, user.getUserFirstName());
					values.put(Users.COLUMN_NAME_MAIL, user.getUserMail());
					values.put(Users.COLUMN_NAME_TELEPHONE, user.getUserPhone());
					
					// Insert, the primary key value of the new row is returned

					context.getContentResolver().insert(UsersProvider.USERS_CONTENT_URI, values);

				}
			 }
		 });
	}

}
