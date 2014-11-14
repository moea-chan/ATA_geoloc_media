package com.mediageoloc.ata.media.photo;

import rx.Observable;
import rx.Subscriber;
import android.content.Context;
import android.net.Uri;
import android.widget.EditText;

/**
 * Comment uploader observable
 */
class CommentUploader implements Observable.OnSubscribe<CommentUri> {

	private Uri uri;
	private EditText comment;
	private Context context;

	public CommentUploader(Uri uri, EditText comment, Context context) {
		this.uri = uri;
		this.comment = comment;
		this.context = context;
	}

	@Override
	public void call(Subscriber<? super CommentUri> commentObserver) {
		try {
			commentObserver.onNext(new CommentUri(uri, comment.getText()
					.toString(), context));
			commentObserver.onCompleted();
		} catch (Exception e) {
			commentObserver.onError(e);
		}
	}
}

