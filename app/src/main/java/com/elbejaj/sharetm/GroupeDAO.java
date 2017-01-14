package com.elbejaj.sharetm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Bejaj on 04/01/2017.
 */

public class GroupeDAO {

    DBManager dbm;
    SQLiteDatabase db;

    public GroupeDAO(Context ctx)
    {
        dbm = new DBManager(ctx, "base", null, 1);
    }


    public void open(){
        db = dbm.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public long ajouterGroupe(Groupe g){
        ContentValues vals = new ContentValues();
        vals.put("id", g.getId());
        vals.put("nom", g.getNomG());

        return db.insert("Groupe", null , vals);
    }

    public int supprimerTache (int id)
    {
        return db.delete("Groupe", "id="+id, null);
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

    public ArrayList<Groupe> listeGroupe()
    {
        ArrayList<Groupe> listeG = new ArrayList<Groupe>();
        db = dbm.getWritableDatabase();
        Cursor c = db.query("Groupe", new String[] {"id","nom"},null,null,null,null,null);
        c.moveToFirst();
        while (!c.isAfterLast())
        {
            Groupe g = new Groupe();
            g.setId(c.getInt(1));
            g.setNomG(c.getString(2));
            listeG.add(g);
            c.moveToNext();
        }

        return listeG;
    }


}
