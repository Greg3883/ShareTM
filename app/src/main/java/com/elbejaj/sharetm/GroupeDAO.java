package com.elbejaj.sharetm;

/**
 * Created by Valentin on 05/02/2017.
 */
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class GroupeDAO {


    private DBGroupe dbm;          //Gestionnaire de la BDD en local
    private SQLiteDatabase db;           //BDD en local
    private ApiInterface apiService;     //Communication avec l'API
    private boolean isConnected;         //Indique si l'utilisateur est connecté à Internet
    private String idRegisteredUser;     //Identifiant de l'utilisateur courant (enregistré)
    private SharedPreferences mesPreferences; //Préférences de l'application
    private Context ctx;

    public GroupeDAO(Context ctx, boolean isConnected)
    {
        this.ctx = ctx;
        dbm = new DBGroupe(ctx, "baseGrp", null, 15);

        //Si connexion, on instancie le gestionnaire de BDD en ligne
        if(isConnected) {
            Log.i("test","Instanciation du service API");
            this.apiService = STMAPI.getClient().create(ApiInterface.class);
            this.isConnected = true;
        } else {
            this.isConnected = false;
        }

        //Récupération des données dans préférences
        this.mesPreferences = ctx.getSharedPreferences("ShareTaskManagerPreferences",0);
        this.idRegisteredUser = mesPreferences.getString("idRegisteredUser","");
        Log.i("test","CREATION GROUPE DAO : ID de l'utilisateur courant : "+this.idRegisteredUser);

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
        vals.put("nomGroupe", g.getNom());
        vals.put("dateCreationGroupe", g.date_toString());
        Log.i("test","id groupe avant insert : "+vals.getAsString("idGroupe"));
        Log.i("test","nom groupe avant insert : "+g.getNom());
        Log.i("test","date creation groupe avant insert : "+ g.date_toString());
        return db.insert("groupe", null , vals);
    }

    public int supprimerGroupe (int id)
    {
        return db.delete("Groupe", "idGroupe="+id, null);
    }

    public int updateGroupe (int id, ContentValues cv)
    {
        String nid = String.valueOf(id);
        return db.update("Groupe",cv,"idGroupe="+nid, null);
    }

    /*public Groupe trouverGroupe(int id)
    {
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Groupe g = null;
        Cursor c = db.query("Groupe", new String[] {"idGroupe","nom","d_creation"},"idGroupe="+id,null,null,null,null);
        if (c.moveToFirst())
        {
            g = new Groupe();
            g.setIdGroupe(c.getString(0));
            g.setNom(c.getString(1));
            String strdc  = c.getString(2);
            Date dc = null;
            try {
                dc.setTime(format.parse(strdc));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            g.setDateCreationGroupe(dc);
        }


        return g;
    }*/


    /**
     * Retourne un booléen indiquant si le groupee existe déjà dans la base
     * @param idGroupe : Identifiant de le groupe à chercher
     */
    public boolean alreadyExists(String idGroupe) {

        Boolean response = false;

        open();
        String[] projectionIn = {"idGroupe","nomGroupe","dateCreationGroupe"};
        String selection = "idGroupe='"+idGroupe+"'";
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String setOrder = null;
        Cursor c = db.query("Groupe",projectionIn, selection, selectionArgs, groupBy, having, setOrder);

        if(c.getCount() > 0) {
            response = true;
        }

        return response;

    }

    public ArrayList <Groupe> listeGroupe()
    {
        //Format des dates
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");

        ArrayList<Groupe> listeG = new ArrayList<Groupe>();
        db = dbm.getWritableDatabase();
        Cursor c = db.query("groupe", new String[] {"idGroupe","nomGroupe","dateCreationGroupe"},null,null,null,null,null);
        c.moveToFirst();
        while (!c.isAfterLast())
        {
            Groupe g = new Groupe();
            g.setNom(c.getString(1));
            String strdc  = c.getString(2);
            Date dateC = null;
            try {
                dateC = format.parse(strdc);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            g.setDateCreationGroupe(dateC);
            g.setIdGroupe(c.getString(0));

            Log.i("Groupe DAO : ",g.getIdGroupe());
            listeG.add(g);
            c.moveToNext();
        }

        return listeG;
    }

}
