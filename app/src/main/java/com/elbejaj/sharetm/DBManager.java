package com.elbejaj.sharetm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Bejaj on 03/12/2016.
 */

public class DBManager extends SQLiteOpenHelper {
    public static final String TACHE_ID = "id";
    public static final String TACHE_NOM = "nom";
    public static final String TACHE_CONTENU = "contenu";
    public static final String TACHE_PRIORITE = "priorite";
    public static final String TACHE_ECHEANCE = "echeance";

    public static final String TACHE_TABLE_NAME = "Tache";
    public static final String TACHE_TABLE_CREATE =
            "CREATE TABLE " + TACHE_TABLE_NAME + " (" +
                    TACHE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TACHE_NOM + " TEXT, " +
                    TACHE_CONTENU + " TEXT, " +
                    TACHE_PRIORITE + " INTEGER, " + TACHE_ECHEANCE + " TEXT);";

    public static final String TACHE_TABLE_DROP = "DROP TABLE IF EXISTS " + TACHE_TABLE_NAME + ";";

    public DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TACHE_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TACHE_TABLE_DROP);
        onCreate(db);
    }
}
