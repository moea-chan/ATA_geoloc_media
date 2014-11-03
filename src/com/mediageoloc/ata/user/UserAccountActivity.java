package com.mediageoloc.ata.user;

import android.os.Bundle;
import android.widget.EditText;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.mediageoloc.ata.R;
import com.mediageoloc.ata.drawer.DrawerActivity;

public class UserAccountActivity extends DrawerActivity {

	private User user;

	@InjectView(R.id.lastName)
	EditText lastNameET;
	@InjectView(R.id.firstName)
	EditText firstNameET;
	@InjectView(R.id.userMail)
	EditText mailET;
	@InjectView(R.id.tel)
	EditText phoneET;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setDrawerContentView(R.layout.activity_user_account);
		ButterKnife.inject(this);
		GitHubService service = new GitHubService();
		service.getUsers(getApplicationContext());
	}
	
	


}
