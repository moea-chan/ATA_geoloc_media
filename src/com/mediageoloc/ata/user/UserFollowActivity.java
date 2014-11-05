package com.mediageoloc.ata.user;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;

import com.mediageoloc.ata.R;
import com.mediageoloc.ata.R.layout;
import com.mediageoloc.ata.bddsqlite.Collaborator;
import com.mediageoloc.ata.bddsqlite.GeolocMediaDbReader;

/**
 * Liste des personnes suivies (Temporairement tables des collaborators
 * @author Thierry
 *
 */
public class UserFollowActivity extends Activity {

	ArrayList<Collaborator> arrayListUsersFollow;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_follow);
		
		//Chargement de la table Collaborator
		arrayListUsersFollow = GeolocMediaDbReader.readListCollaboratorsDB(getApplicationContext());	
	}
	
	
}
