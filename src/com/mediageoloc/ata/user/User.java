package com.mediageoloc.ata.user;

public class User {
	
	private String login;
	private String url;
	private String repos_url;
	private String avatar_url;
	
	public User(String firstName, String lastName, String mail, String phone){
		super();
		login = firstName;
		url = lastName;
		repos_url = mail;
		avatar_url = phone;
	}
	
	public String getUserFirstName() {
		return login;
	}
	public void setUserFirstName(String userFirstName) {
		this.login = userFirstName;
	}
	public String getUserLastName() {
		return url;
	}
	public void setUserLastName(String userLastName) {
		this.url = userLastName;
	}
	public String getUserMail() {
		return repos_url;
	}
	public void setUserMail(String userMail) {
		this.repos_url = userMail;
	}
	public String getUserPhone() {
		return avatar_url;
	}
	public void setUserPhone(String userPhone) {
		this.avatar_url = userPhone;
	}
	
	public Boolean saveUserPrefs(){
		
		return true;
	}
}
