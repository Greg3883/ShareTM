package com.elbejaj.sharetm;

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
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Bejaj on 03/12/2016.
 */

public class AffectationTacheDAO {
    private DBManager dbmLocal;          //Gestionnaire de la BDD en local
    private SQLiteDatabase db;           //BDD en local
    private ApiInterface apiService;     //Communication avec l'API
    private boolean isConnected;         //Indique si l'utilisateur est connecté à Internet
    private String idRegisteredUser;     //Identifiant de l'utilisateur courant (enregistré)
    private SharedPreferences mesPreferences; //Préférences de l'application
    private Context ctx;

    /**
     * Constructeur de TacheDAO
     * @param ctx : Contexte dans lequel on se trouve
     * @param isConnected : Détermine si le terminal est connecté à internet
     */
    public AffectationTacheDAO(Context ctx,boolean isConnected)
    {
        this.ctx = ctx;
        dbmLocal = new DBManager(ctx, "base", null, 13);
        db = dbmLocal.getWritableDatabase();

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
        Log.i("test","CREATION AFFECTTACHE DAO : ID de l'utilisateur courant : "+this.idRegisteredUser);

    }


    public void open(){
        db = dbmLocal.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public long ajouterAffectationTache(AffectationTache at){


        ContentValues vals = new ContentValues();
        vals.put("idAffectTache", at.getIdAffectationTache());
        vals.put("estAdminTache", at.isAdmin());
        vals.put("idUtilisateur", at.getIdUtilisateur());
        vals.put("idGroupe", at.getIdTache());


        //Si on est connecté, on ajoute la tâche à la base de données sur le serveur
        if(isConnected) {

            //Ajouter la tache sur le serveur avec l'URL /createTask avec 'idUtilisateur','intitule','description','priorite','etat','echeance','refGroupe'
            //Ajouter affectation tache sur le serveur avec l'URL /createAffectationTache avec
        }

        return db.insert("affectationTache", null , vals);

    }

    public int supprimerAffectTache (String id)
    {
        return db.delete("affectationTache", "idAffectTache="+id, null);
    }

    public int updateAffectTache (String id, ContentValues cv)
    {
        return db.update("affectationTache",cv,"idAffectTache='"+id+"'", null);
    }

}
