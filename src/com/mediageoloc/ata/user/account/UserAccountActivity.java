package com.mediageoloc.ata.user.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.mediageoloc.ata.R;
import com.mediageoloc.ata.drawer.DrawerActivity;
import com.mediageoloc.ata.user.User;
import com.mediageoloc.ata.user.follower.FollowersActivity;

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

	static final String CURRENT_MODE = "currentMode";
	static final String EDIT_MODE = "edit";
	static final String DISPLAY_MODE = "display";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setDrawerContentView(R.layout.activity_user_account);
		ButterKnife.inject(this);
		
		//si  changement d'orientation, récupération du mode précédent
		if (savedInstanceState != null) {
		    String curMode = savedInstanceState.getString(CURRENT_MODE);
		    if(curMode == "edit"){
		    	setEditModeVisible();
		    }
		    else{
		    	setDisplayModeVisible();
		    }
		}
		else{
			//récupération du user dans les shared pref
			setUser(UserPrefsManager.getUserPrefs(getApplicationContext()));
			//set layout en fonction de la présence des infos dans shared pref
			setUserMode();
		}
		
	}
	
	@Override
	protected void onSaveInstanceState (Bundle outState) {
	    super.onSaveInstanceState(outState);
	    //enregistre le mode courant avant changement d'orientation
	    if(editMode.getVisibility() == View.VISIBLE)
	    	outState.putString(CURRENT_MODE, EDIT_MODE);
	    else
	    	outState.putString(CURRENT_MODE, DISPLAY_MODE);
	}
	
	// set le user et affiche ses infos dans le layout
	private void setUser(User newUser){
		this.user = newUser;
		if(user.getUserFirstName().toString() != ""){
			lastNameET.setText(user.getUserLastName());
			firstNameET.setText(user.getUserFirstName());
			mailET.setText(user.getUserMail());
			phoneET.setText(user.getUserPhone());
			
			lastNameTV.setText(user.getUserLastName());
			firstNameTV.setText(user.getUserFirstName());
			mailTV.setText(user.getUserMail());
			phoneTV.setText(user.getUserPhone());
		}
	}
	
	//edit or display mode of user infos
	private void setUserMode() {
		if(user.getUserFirstName().toString() == ""){
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
		String firstName = firstNameET.getText().toString();
		//si le prénom n'est pas renseigné alors 
		//pas d'enregistrement et affichage du message d'erreur
		if(firstName.isEmpty()){
			Toast.makeText(getApplicationContext(), R.string.no_info_toast, Toast.LENGTH_LONG).show();
		}
		else{
			String lastName = lastNameET.getText().toString();
			String mail = mailET.getText().toString();
			String phone = phoneET.getText().toString();
			
			User newUser = new User(firstName, lastName, mail, phone);
			UserPrefsManager.setUserPreferences(newUser, getApplicationContext());	
			setUser(UserPrefsManager.getUserPrefs(getApplicationContext()));
			
			setDisplayModeVisible();
			
			hideKeyboard();
		}
	}

	private void hideKeyboard() {
		InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
		View view = this.getCurrentFocus();
		inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}
	
	@OnClick(R.id.showFollowers)
	public void showFollowers() {
		Intent intent = new Intent(this, FollowersActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
	}


}
