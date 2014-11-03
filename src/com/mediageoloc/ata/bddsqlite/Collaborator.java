package com.mediageoloc.ata.bddsqlite;

public class Collaborator {
	public String id;
	public String login; 
	public String html_url; 
	
	public Collaborator(){
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getHtml_url() {
		return html_url;
	}

	public void setHtml_url(String html_url) {
		this.html_url = html_url;
	}

}
