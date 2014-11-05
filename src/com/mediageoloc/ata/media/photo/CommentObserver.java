package com.mediageoloc.ata.media.photo;

import rx.Observer;
import android.util.Log;

import com.mediageoloc.ata.historic.HistoricPrefManager;

/**
 * Comment uploader observer
 * 
 */
public class CommentObserver implements Observer<CommentUri> {

	@Override
	public void onError(Throwable arg0) {
		Log.e("rxjava", "Comments upload failed");
	}

	@Override
	public void onNext(CommentUri arg0) {
		HistoricPrefManager.addHistoriquePreferences(arg0.getUri(),
				arg0.getComment(), arg0.getContext());
	}

	@Override
	public void onCompleted() {
	}

}