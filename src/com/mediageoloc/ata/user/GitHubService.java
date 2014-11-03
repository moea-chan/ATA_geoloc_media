package com.mediageoloc.ata.user;

import java.util.List;

import retrofit.http.GET;
import rx.Observable;

public interface GitHubService {
	@GET("/users")
	  Observable<List<User>> listUsers();
}
