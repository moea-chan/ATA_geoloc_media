package com.mediageoloc.ata.bddsqlite;

import android.provider.BaseColumns;

/**
 * GeolocMediaContract : Contract SQLite for local database 
 * @author Thierry
 *
 */
public final class GeolocMediaContract {

	    private GeolocMediaContract() {}

	    /* table contents */
	    public static abstract class Users implements BaseColumns {
	        public static final String TABLE_NAME = "user";
	        public static final String COLUMN_NAME_USER_ID = "userid";
	        public static final String COLUMN_NAME_NOM = "nom";
	        public static final String COLUMN_NAME_PRENOM = "prenom";
	        public static final String COLUMN_NAME_MAIL = "mail";
	        public static final String COLUMN_NAME_TELEPHONE = "telephone";
	        
	    }
	    /* table contents Collaborators */
	    public static abstract class Collaborators implements BaseColumns {
	        public static final String TABLE_NAME = "collaborator";
	        public static final String COLUMN_NAME_COLLABORATOR_ID = "id";
	        public static final String COLUMN_NAME_LOGIN = "login";
	        public static final String COLUMN_NAME_HTML_URL = "html_url";
	    }
	
}
