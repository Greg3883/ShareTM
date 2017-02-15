package com.elbejaj.sharetm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Bejaj on 03/12/2016.
 */

public class UtilisateurDAO{
    DBManager dbm;
    SQLiteDatabase db;

    public UtilisateurDAO(Context ctx)
    {
        dbm = new DBManager(ctx, "base", null, 12);
    }


    public void open(){
        db = dbm.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public long ajouterUtilisateur(Utilisateur u){
        Log.d("grgre", "AJOUT VALIDEEEEEEEEEEE");
        ContentValues vals = new ContentValues();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String reportDate = df.format( u.getDateCreationU());
        vals.put("idUtilisateur", u.getIdUtilisateur());
        vals.put("nomU", u.getNomU());
        vals.put("email", u.getEmail());
        vals.put("mdpHash", u.getMdpHash());
        vals.put("apiKey", u.getApiKey());
        return db.insert("Utilisateur", null , vals);
    }

    public int supprimerUtilisateur (int id)
    {
        return db.delete("Utilisateur", "idUtilisateur="+id, null);
    }

    public int updateUtilisateur (int id, ContentValues cv)
    {
        String nid = String.valueOf(id);
        return db.update("Utilisateur",cv,"idUtilisateur="+nid, null);
    }

    public Boolean trouverUtilisateur(String email)
    {
        Boolean valide = false;
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Utilisateur u = null;
        Cursor c = db.query("Utilisateur", new String[] {"idUtilisateur","nomU","email","mdpHash","apiKey","dateCreationU"},"email="+"'"+email+"'",null,null,null,null);
        if (c.moveToFirst())
        {
            u = new Utilisateur();
            u.setIdUtilisateur(c.getInt(0));
            u.setNomU(c.getString(1));
            u.setEmail(c.getString(2));
            u.setMdpHash(c.getString(3));
            u.setApiKey(c.getString(4));
            valide = true;
        }


        return valide;
    }

    /**public ArrayList <Tache> listeTache()
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
     }**/


}
