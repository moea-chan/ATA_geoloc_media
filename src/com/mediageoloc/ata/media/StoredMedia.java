package com.mediageoloc.ata.media;

public class StoredMedia {
	private String comment;
	private String filePath;
	
	public StoredMedia(String filepath, String comment){
		super();
		this.comment = comment;
		this.filePath = filepath;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	
	
	
}
