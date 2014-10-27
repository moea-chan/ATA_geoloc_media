package com.mediageoloc.ata;

public class StoredMedia {
	private String _comment;
	private String _filePath;
	
	public StoredMedia(String fp, String c){
		super();
		_comment = c;
		_filePath = fp;
	}
	
	public String get_comment() {
		return _comment;
	}
	public void set_comment(String _comment) {
		this._comment = _comment;
	}
	public String get_filePath() {
		return _filePath;
	}
	public void set_filePath(String _filePath) {
		this._filePath = _filePath;
	}
	
	
}
