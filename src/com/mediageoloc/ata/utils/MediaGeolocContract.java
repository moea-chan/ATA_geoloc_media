package com.mediageoloc.ata.utils;

import android.provider.BaseColumns;

public final class MediaGeolocContract {
	
    // To prevent someone from accidentally instantiating the   
	// contract class, give it an empty and/or private constructor.
    private MediaGeolocContract() {}

    /* Inner class that defines the table contents */
    public static abstract class Users implements BaseColumns {
        public static final String TABLE_NAME = "users";
        public static final String COLUMN_NAME_NOM = "nom";
        public static final String COLUMN_NAME_PRENOM = "prenom";
        public static final String COLUMN_NAME_MAIL = "mail";
        public static final String COLUMN_NAME_TELEPHONE = "telephone";
    }
    
    /* Inner class that defines the table Medias contents */
    public static abstract class Medias implements BaseColumns {
        public static final String TABLE_NAME = "medias";
        public static final String COLUMN_NAME_CHEMINFICHIER = "cheminfichier";
        public static final String COLUMN_NAME_COMMENTAIRE = "commentaire";
        public static final String COLUMN_NAME_LATITUDE = "latitude";
        public static final String COLUMN_NAME_LONGITUDE = "longitude";
        public static final String COLUMN_NAME_TYPEMEDIA = "typemedia";
    }
}
