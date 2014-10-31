package com.mediageoloc.ata.user;

public class User {
	
	private String userFirstName;
	private String userLastName;
	private String userMail;
	private String userPhone;
	
	public User(String firstName, String lastName, String mail, String phone){
		super();
		userFirstName = firstName;
		userLastName = lastName;
		userMail = mail;
		userPhone = phone;
	}
	
	public String getUserFirstName() {
		return userFirstName;
	}
	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}
	public String getUserLastName() {
		return userLastName;
	}
	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}
	public String getUserMail() {
		return userMail;
	}
	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	
	public Boolean saveUserPrefs(){
		
		return true;
	}
}
