package com.mediageoloc.ata.utils;

import android.provider.BaseColumns;

public final class MediaGeolocContract {
	
    // To prevent someone from accidentally instantiating the   
	// contract class, give it an empty and/or private constructor.
    private MediaGeolocContract() {}

    /* Inner class that defines the table contents */
    public static abstract class Users implements BaseColumns {
        public static final String USERS_TABLE_NAME = "users";
        public static final String FOLLOWERS_TABLE_NAME = "followers";
        public static final String USER_ID = "user_id";
        public static final String COLUMN_NAME_NOM = "nom";
        public static final String COLUMN_NAME_PRENOM = "prenom";
        public static final String COLUMN_NAME_MAIL = "mail";
        public static final String COLUMN_NAME_TELEPHONE = "telephone";
    }
}
