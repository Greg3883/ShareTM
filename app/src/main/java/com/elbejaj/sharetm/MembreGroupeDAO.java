package com.elbejaj.sharetm;

/**
 * Created by Valentin on 02/04/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import static com.elbejaj.sharetm.R.id.dateC;
import static java.lang.Thread.sleep;

public class MembreGroupeDAO {

    private DBManager dbm;
    private SQLiteDatabase db;           //BDD en local
    private ApiInterface apiService;     //Communication avec l'API
    private boolean isConnected;         //Indique si l'utilisateur est connecté à Internet
    private String idRegisteredUser;     //Identifiant de l'utilisateur courant (enregistré)
    private SharedPreferences mesPreferences; //Préférences de l'application
    private Context ctx;
    private MembreGroupeDAO mgd;

    public MembreGroupeDAO(Context ctx, boolean isConnected)
    {
        this.ctx = ctx;
        dbm = new DBManager(ctx, "baseGrp", null, 15);

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
        Log.i("test","CREATION MEMBREGROUPE DAO : ID de l'utilisateur courant : "+this.idRegisteredUser);

    }

    public void open(){
        db = dbm.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public long ajouterMembreGroupe(MembreGroupe mg){

        ContentValues vals = new ContentValues();
        vals.put("idMembreGroupe", mg.getIdMembreGroupe());
        vals.put("idUtilisateur", mg.getIdUtilisateur());
        vals.put("idGroupe", mg.getIdGroupe());
        vals.put("estAdmin", mg.getEstAdmin());

        //Si on est connecté, on ajoute le membre à la base de données sur le serveur
        if(isConnected) {

            //Ajouter la tache sur le serveur avec l'URL /createTask avec 'idUtilisateur','intitule','description','priorite','etat','echeance','refGroupe'
            //Ajouter affectation tache sur le serveur avec l'URL /createAffectationTache avec


        }

        return db.insert("membreGroupe", null , vals);

    }

    public boolean alreadyExists(String idMembreGroupe) {

        Boolean response = false;

        open();
        String[] projectionIn = {"idMembreGroupe","idUtilisateur","idGroupe","estAdmin"};
        String selection = "idMembreGroupe='"+idMembreGroupe+"'";
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String setOrder = null;
        Cursor c = db.query("membreGroupe",projectionIn, selection, selectionArgs, groupBy, having, setOrder);

        if(c.getCount() > 0) {
            response = true;
        }

        return response;

    }

    public ArrayList<MembreGroupe> listeMembreGroupe(String id)
    {

        ArrayList<MembreGroupe> listeMG = new ArrayList<MembreGroupe>();
        db = dbm.getWritableDatabase();
        Cursor c = db.query("membreGroupe", new String[] {"idMembreGroupe","idUtilisateur","idGroupe", "estAdmin"},null,null,null,null,null);
        c.moveToFirst();
        while (!c.isAfterLast())
        {
            if(c.getString(2)==id) {
                MembreGroupe mg = new MembreGroupe();
                mg.setIdMembreGroupe(c.getString(0));
                mg.setIdUtilisateur(c.getString(1));
                mg.setIdGroupe(c.getString(2));
                int isAdmin = c.getInt(3);
                if (isAdmin == 1) {
                    mg.setEstAdmin(true);
                } else {
                    mg.setEstAdmin(false);
                }

                Log.i("MembreGroupe DAO : ", mg.getIdMembreGroupe());
                listeMG.add(mg);
            }
            c.moveToNext();
        }

        return listeMG;
    }



}
