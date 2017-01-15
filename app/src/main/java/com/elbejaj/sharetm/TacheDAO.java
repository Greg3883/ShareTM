package com.elbejaj.sharetm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Bejaj on 03/12/2016.
 */

public class TacheDAO{
    DBManager dbm;
    SQLiteDatabase db;

    public TacheDAO(Context ctx)
    {
        dbm = new DBManager(ctx, "base", null, 4);
    }


    public void open(){
        db = dbm.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public long ajouterTache(Tache t){
        ContentValues vals = new ContentValues();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String reportDate = df.format( t.getEcheance());
        vals.put("id", t.getId());
        vals.put("nom", t.getNom());
        vals.put("contenu", t.getContenu());
        vals.put("priorite", t.getPriorite());
        vals.put("echeance", reportDate);
        vals.put("etat", t.getEtat());
        vals.put("groupe", t.getGroupe());
        return db.insert("Tache", null , vals);
    }

    public int supprimerTache (int id)
    {
        return db.delete("Tache", "id="+id, null);
    }

    public int updateTache (int id, ContentValues cv)
    {
        String nid = String.valueOf(id);
        return db.update("Tache",cv,"id="+nid, null);
    }

   public Tache trouverTache(int id)
    {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Tache t = null;
        Cursor c = db.query("Tache", new String[] {"id","nom","contenu","priorite","echeance","etat","groupe"},"id="+id,null,null,null,null);
        if (c.moveToFirst())
        {
            t = new Tache();
            t.setId(c.getInt(0));
            t.setNom(c.getString(1));
            t.setContenu(c.getString(2));
            t.setPriorite(c.getInt(3));
            t.setEtat(c.getInt(5));
            t.setGroupe(c.getInt(6));
            String strEcheance   = c.getString(4);
            Date dateEcheance = null;
            try {
                dateEcheance = format.parse(strEcheance);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            t.setEcheance(dateEcheance);
        }


        return t;
    }

    public ArrayList <Tache> listeTache()
    {
        //Format des dates
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        ArrayList<Tache> listeT = new ArrayList<Tache>();
        db = dbm.getWritableDatabase();
        Cursor c = db.query("Tache", new String[] {"id","nom","contenu","priorite","echeance","etat","groupe"},null,null,null,null,null);
        c.moveToFirst();
        while (!c.isAfterLast())
        {
            Tache t = new Tache();
            t.setNom(c.getString(1));
            t.setContenu(c.getString(2));
            t.setPriorite(c.getInt(3));
            t.setEtat(c.getInt(5));
            t.setGroupe(c.getInt(6));
            String strEcheance   = c.getString(4);
            Date dateEcheance = null;
            try {
                dateEcheance = format.parse(strEcheance);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            t.setEcheance(dateEcheance);
            t.setId(c.getInt(0));
            listeT.add(t);
            c.moveToNext();
        }

        return listeT;
    }


}
