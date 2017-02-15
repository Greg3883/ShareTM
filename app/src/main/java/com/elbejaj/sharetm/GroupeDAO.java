package com.elbejaj.sharetm;

/**
 * Created by Baalamor on 05/02/2017.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Calendar;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class GroupeDAO {

    DBGroupe dbm;
    SQLiteDatabase db;

    public GroupeDAO(Context ctx)
    {
        dbm = new DBGroupe(ctx, "baseGrp", null, 12);
    }


    public void open(){
        db = dbm.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public long ajouterGroupe(Groupe g){
        ContentValues vals = new ContentValues();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Calendar c = Calendar.getInstance();
        String reportDate = df.format( c.getTime());
        vals.put("id", g.getId());
        vals.put("nom", g.getNom());
        vals.put("d_creation", reportDate);
        return db.insert("Groupe", null , vals);
    }

    public int supprimerGroupe (int id)
    {
        return db.delete("Groupe", "id="+id, null);
    }

    public int updateGroupe (int id, ContentValues cv)
    {
        String nid = String.valueOf(id);
        return db.update("Groupe",cv,"id="+nid, null);
    }

    public Groupe trouverGroupe(int id)
    {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Groupe g = null;
        Cursor c = db.query("Groupe", new String[] {"id","nom","d_creation"},"id="+id,null,null,null,null);
        if (c.moveToFirst())
        {
            g = new Groupe();
            g.setId(c.getInt(0));
            g.setNom(c.getString(1));
            String strdc  = c.getString(2);
            Calendar dc = null;
            try {
                dc.setTime(format.parse(strdc));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            g.setDateC(dc);
        }


        return g;
    }

    public ArrayList <Groupe> listeGroupe()
    {
        //Format des dates
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        ArrayList<Groupe> listeG = new ArrayList<Groupe>();
        db = dbm.getWritableDatabase();
        Cursor c = db.query("Groupe", new String[] {"id","nom","d_creation"},null,null,null,null,null);
        c.moveToFirst();
        while (!c.isAfterLast())
        {
            Groupe g = new Groupe();
            g.setNom(c.getString(1));
            String strdc  = c.getString(2);
            Calendar dateC= Calendar.getInstance();
            try {
                dateC.setTime(format.parse(strdc));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            g.setDateC(dateC);
            g.setId(c.getInt(0));
            listeG.add(g);
            c.moveToNext();
        }

        return listeG;
    }

}
