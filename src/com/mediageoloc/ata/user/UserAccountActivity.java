package com.mediageoloc.ata.user;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.mediageoloc.ata.R;

public class UserAccountActivity extends Activity {
	
	private User user;
	
	 @InjectView(R.string.user_last_name) EditText lastNameET;
	 @InjectView(R.string.user_first_name) EditText firstNameET;
	 @InjectView(R.string.user_mail) EditText mailET;
	 @InjectView(R.string.user_phone) EditText phoneET;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_account);
		ButterKnife.inject(this);
	}
}
