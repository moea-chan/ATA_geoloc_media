package com.mediageoloc.ata;

import com.mediageoloc.ata.user.User;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

public class UserAccountActivity extends Activity {
	
	private User user;
	
	private EditText lastNameET;
	private EditText firstNameET;
	private EditText mailET;
	private EditText phoneET;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_account);
		
		lastNameET = (EditText) findViewById(R.string.user_last_name);
		firstNameET = (EditText) findViewById(R.string.user_first_name);
		mailET = (EditText) findViewById(R.string.user_mail);
		phoneET = (EditText) findViewById(R.string.user_phone);
	}
}
