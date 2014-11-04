package com.mediageoloc.ata.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.mediageoloc.ata.R;
import com.mediageoloc.ata.drawer.DrawerActivity;
import com.mediageoloc.ata.media.photo.PhotoFilterPreviewActivity;
import com.mediageoloc.ata.utils.GitHubService;

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
		
	}
	
	@OnClick(R.id.showFollowers)
	public void showFollowers() {
		Intent intent = new Intent(this, FollowersActivity.class);
        startActivity(intent);
	}


}
