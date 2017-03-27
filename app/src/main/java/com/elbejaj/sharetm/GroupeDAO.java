package com.elbejaj.sharetm;

/**
 * Created by Baalamor on 05/02/2017.
 */
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Calendar;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


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
        dbm = new DBGroupe(ctx, "baseGrp", null, 13);

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
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c = Calendar.getInstance();
        String reportDate = df.format( c.getTime());
        vals.put("idGroupe", g.getIdGroupe());
        vals.put("nom", g.getNom());
        vals.put("d_creation", reportDate);
        return db.insert("Groupe", null , vals);
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

    public Groupe trouverGroupe(int id)
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
            Calendar dc = null;
            try {
                dc.setTime(format.parse(strdc));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            g.setDateCreationGroupe(dc);
        }


        return g;
    }

    public ArrayList <Groupe> listeGroupe()
    {
        //Format des dates
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");

        ArrayList<Groupe> listeG = new ArrayList<Groupe>();
        db = dbm.getWritableDatabase();
        Cursor c = db.query("Groupe", new String[] {"idGroupe","nom","d_creation"},null,null,null,null,null);
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
            g.setDateCreationGroupe(dateC);
            g.setIdGroupe(c.getString(0));
            listeG.add(g);
            c.moveToNext();
        }

        return listeG;
    }

}
