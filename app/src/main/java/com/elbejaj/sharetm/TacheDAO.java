package com.elbejaj.sharetm;

import android.content.ContentValues;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Bejaj on 03/12/2016.
 */

public class TacheDAO{
    DBManager dbm;
    SQLiteDatabase db;

    public TacheDAO(Context ctx)
    {
        dbm = new DBManager(ctx, "base", null, 1);
    }


    public void open(){
        db = dbm.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public long ajouterTache(Tache t){
        ContentValues vals = new ContentValues();
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String reportDate = df.format( t.getEcheance());
        vals.put("id", t.getId());
        vals.put("nom", t.getNom());
        vals.put("contenu", t.getContenu());
        vals.put("priorite", t.getPriorite());
        vals.put("echeance", reportDate);
        return db.insert("Tache", null , vals);
    }

    public int supprimerTache (int id)
    {
        return db.delete("Tache", "id="+id, null);
    }

   /* public Tache trouverTache(int id)
    {
        Tache t = null;
        Cursor c = db.query("Tache", new String[] {"id","nom","contenu","priorite","echeance"},"id="+id,null,null,null,null);
        if (c.moveToFirst())
        {
            t = new Tache();
            t.setId(c.getInt(0));
            t.setNom(c.getString(1));
            t.setContenu(c.getString(2));
            t.setPriorite(c.getInt(3));
            t.setEcheance(c.getString(4));
        }


        return t;
    }*/

    public ArrayList <Tache> listeTache()
    {
        ArrayList<Tache> listeT = new ArrayList<Tache>();
        db = dbm.getWritableDatabase();
        Cursor c = db.query("Tache", new String[] {"id","nom","contenu","priorite","echeance"},null,null,null,null,null);
        c.moveToFirst();
        while (!c.isAfterLast())
        {
            Tache t = new Tache();
            t.setNom(c.getString(1));
            t.setContenu(c.getString(2));
            t.setPriorite(c.getInt(3));
            SimpleDateFormat sdf = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
            String string  = c.getString(4);
            Date date = format.parse(string);
            t.setEcheance(c.getString(4));
            listeT.add(t);
            c.moveToNext();
        }

        return listeT;
    }


}
