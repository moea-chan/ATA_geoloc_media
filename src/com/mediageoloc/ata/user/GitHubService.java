package com.mediageoloc.ata.user;

import java.util.List;

import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;
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

	public static void getUsers(){	
		 RestAdapter restAdapter = new RestAdapter.Builder()
		 .setEndpoint(GITHUB_DOMAIN)
		 .build();
		
		 GitHubServiceStub service = restAdapter.create(GitHubServiceStub.class);
		 Observable<List<User>> users = service.users();
		
		 users.subscribeOn(Schedulers.io())
		 .observeOn(Schedulers.io())
		 .subscribe(new Observer<List<User>>() {
		
			 @Override
			 public void onCompleted() {}
			 @Override
			 public void onError(Throwable e) {
				 Log.d("debug", e.getMessage());
			 }
			 @Override
			 public void onNext(List<User> arg0) {
				 Log.d("debug", arg0.toString());
			 }
		 });
	}
}
