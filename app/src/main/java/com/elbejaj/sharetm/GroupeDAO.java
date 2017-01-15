package com.elbejaj.sharetm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Bejaj on 15/01/2017.
 */

public class GroupeDAO {

    DBManager dbm;
    SQLiteDatabase db;

    public GroupeDAO(Context ctx)
    {
        dbm = new DBManager(ctx, "base", null, 4);
    }

    public void open(){
        db = dbm.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public long ajouterGroupe(Groupe g){
        ContentValues vals = new ContentValues();
        vals.put("idGroupe", g.getIdGroupe());
        vals.put("nomGroupe", g.getNomGroupe());
        return db.insert("Groupe", null , vals);
    }

    public Groupe trouverGroupe(int id) {
        Groupe g = null;
        Cursor c = db.query("Groupe", new String[]{"idGroupe", "nomGroupe"}, "idGroupe=" + id, null, null, null, null);
        if (c.moveToFirst()) {
            g = new Groupe();
            g.setIdGroupe(c.getInt(0));
            g.setNomGroupe(c.getString(1));
        }
        return g;
    }
}
