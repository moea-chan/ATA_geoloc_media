package com.mediageoloc.ata.user;

import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;


import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.mediageoloc.ata.R;
import com.mediageoloc.ata.drawer.DrawerActivity;

public class UserAccountActivity extends DrawerActivity {

	private User user;

	@InjectView(R.id.lastNameET)
	EditText lastNameET;
	@InjectView(R.id.firstNameET)
	EditText firstNameET;
	@InjectView(R.id.userMailET)
	EditText mailET;
	@InjectView(R.id.telET)
	EditText phoneET;
	
	@InjectView(R.id.lastNameTV)
	TextView lastNameTV;
	@InjectView(R.id.firstNameTV)
	TextView firstNameTV;
	@InjectView(R.id.mailTV)
	TextView mailTV;
	@InjectView(R.id.phoneTV)
	TextView phoneTV;
	
	@InjectView(R.id.editMode)
	RelativeLayout editMode;
	@InjectView(R.id.displayMode)
	RelativeLayout displayMode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setDrawerContentView(R.layout.activity_user_account);
		ButterKnife.inject(this);
		setUser(UserPrefsManager.getUserPrefs(getApplicationContext()));
		setUserMode();
		
	}

	private void setUser(User newUser){
		this.user = newUser;
		if(user.getUserFirstName().toString() != ""){
			lastNameET.setText(user.getUserLastName());
			firstNameET.setText(user.getUserFirstName());
			mailET.setText(user.getUserMail());
			phoneET.setText(user.getUserPhone());
		}
	}
	
	//edit or display mode of user infos
	private void setUserMode() {
		if(user == null){
			setEditModeVisible();
		}
		else{
			setDisplayModeVisible();
		}
	}

	private void setDisplayModeVisible() {
		editMode.setVisibility(View.GONE);
		displayMode.setVisibility(View.VISIBLE);
	}

	private void setEditModeVisible() {
		displayMode.setVisibility(View.GONE);
		editMode.setVisibility(View.VISIBLE);
	}
	
	
	@OnClick(R.id.editUserButton)
	public void allowEditMode() {
		setEditModeVisible();
	}
	
	@OnClick(R.id.cancelEditUserButton)
	public void cancelUserInfoEdition() {
		if(user != null)
			setDisplayModeVisible();
		else{
			resetEditFields();
		}
	}

	private void resetEditFields() {
		lastNameET.setText("");
		firstNameET.setText("");
		mailET.setText("");
		phoneET.setText("");
	}
	
	@OnClick(R.id.saveEditUserButton)
	public void saveUserInfos() {
		String lastName = lastNameET.getText().toString();
		String firstName = firstNameET.getText().toString();
		String mail = mailET.getText().toString();
		String phone = phoneET.getText().toString();
		
		User newUser = new User(firstName, lastName, mail, phone);
		UserPrefsManager.setUserPreferences(newUser, getApplicationContext());	
		setUser(UserPrefsManager.getUserPrefs(getApplicationContext()));
		resetEditFields();
		setDisplayModeVisible();
	}
	
	@OnClick(R.id.showFollowers)
	public void showFollowers() {
		//TODO
	}


}
