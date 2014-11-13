package com.mediageoloc.ata.media;

public class StoredMedia {
	private String comment;
	private String filePath;
	private Double latitude;
	private Double longitude;
	private String typeMedia;
	
	public static String TYPEMEDIA_PHOTO="PHOTO";
	
	public StoredMedia(String filepath, String comment, Double latitude, double longitude, String typemedia){
		super();
		this.comment = comment;
		this.filePath = filepath;
		this.latitude = latitude;
		this.longitude = longitude;
		this.typeMedia = typemedia;
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

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getTypeMedia() {
		return typeMedia;
	}

	public void setTypeMedia(String typeMedia) {
		this.typeMedia = typeMedia;
	}
	
}
