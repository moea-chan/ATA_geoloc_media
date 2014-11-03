package com.mediageoloc.ata.web;

import android.accounts.Account;


public interface AccountServiceInterface {

	  @GET("/account/{accountNumber}") // only the “relative” part of the URL
	  Account getAccount(
	      @Path("accountNumber") String accountNb);

}