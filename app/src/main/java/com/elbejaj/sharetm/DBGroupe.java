package com.elbejaj.sharetm;

/**
 * Created by Valentin on 08/02/2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBGroupe extends SQLiteOpenHelper {

    public static final String GROUPE_ID = "idGroupe";
    public static final String GROUPE_NOM = "nomGroupe";
    public static final String GROUPE_DCREATION = "dateCreationGroupe";
    public static final String GROUPE_TABLE_NAME = "groupe";
    public static final String GROUPE_TABLE_CREATE =
            "CREATE TABLE " + GROUPE_TABLE_NAME + " (" +
                    GROUPE_ID + " TEXT PRIMARY KEY, " +
                    GROUPE_NOM + " TEXT, " +
                    GROUPE_DCREATION + " TEXT);";

    public static final String GROUPE_TABLE_DROP = "DROP TABLE IF EXISTS " + GROUPE_TABLE_NAME + ";";

    public DBGroupe(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(GROUPE_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(GROUPE_TABLE_DROP);
        onCreate(db);
    }


}
