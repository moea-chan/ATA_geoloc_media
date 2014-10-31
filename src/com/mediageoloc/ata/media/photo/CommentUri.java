package com.mediageoloc.ata.media.photo;

import android.content.Context;
import android.net.Uri;

/**
 * Comment wrapper
 *
 */
class CommentUri {
	private Uri uri;
	private String comment;
	private Context context;

	public CommentUri(Uri uri, String comment, Context context) {
		super();
		this.uri = uri;
		this.comment = comment;
		this.context = context;
	}

	public Uri getUri() {
		return uri;
	}

	public String getComment() {
		return comment;
	}

	public Context getContext() {
		return context;
	}
}