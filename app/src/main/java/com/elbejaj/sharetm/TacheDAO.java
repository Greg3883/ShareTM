package com.elbejaj.sharetm;

import android.content.ContentValues;
import android.content.Context;
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

public class TacheDAO {
    private DBManager dbmLocal;          //Gestionnaire de la BDD en local
    private SQLiteDatabase db;           //BDD en local
    private ApiInterface apiService;     //Communication avec l'API
    private boolean isConnected;         //Indique si l'utilisateur est connecté à Internet

    /**
     * Constructeur de TacheDAO
     * @param ctx : Contexte dans lequel on se trouve
     * @param isConnected : Détermine si le terminal est connecté à internet
     */
    public TacheDAO(Context ctx,boolean isConnected)
    {
        dbmLocal = new DBManager(ctx, "base", null, 13);

        db = dbmLocal.getWritableDatabase();
        Log.i("test","Je suis dans le constructeur de TacheDAO");
     

        //Si connexion, on instancie le gestionnaire de BDD en ligne
        if(isConnected) {
            Log.i("test","Instanciation du service API");
            this.apiService = STMAPI.getClient().create(ApiInterface.class);
            this.isConnected = true;
        } else {
            this.isConnected = false;
        }

}


    public void open(){
        db = dbmLocal.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public long ajouterTache(Tache t){

        ContentValues vals = new ContentValues();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String reportDate = df.format( t.getEcheanceT());
        String dateEcheance = df.format(t.getDateCreationT());
        vals.put("idTache", t.getIdTache());
        vals.put("intituleT", t.getIntituleT());
        vals.put("dateCreationT", dateEcheance);
        vals.put("descriptionT", t.getDescriptionT());
        vals.put("prioriteT", t.getPrioriteT());
        vals.put("echeanceT", reportDate);
        vals.put("etatT", t.getEtatT());
        vals.put("refGroupe", t.getRefGroupe());

        //Si on est connecté, on ajoute la tâche à la base de données sur le serveur
        if(isConnected) {
            
        }

        return db.insert("tache", null , vals);

    }

    public int supprimerTache (int id)
    {
        return db.delete("Tache", "idTache="+id, null);
    }

    public int updateTache (int id, ContentValues cv)
    {
        String nid = String.valueOf(id);
        return db.update("Tache",cv,"idTache="+nid, null);
    }

    /**
     * Retourne un booléen indiquant si la tâche existe déjà dans la base
     * @param idTache : Identifiant de la tâche à chercher
     */
    public boolean alreadyExists(String idTache) {

        Boolean response = false;

        String[] projectionIn = {"idTache","intituleT","dateCreationT","descriptionT","prioriteT","echeanceT","etatT","refGroupe"};
        String selection = "idTache='"+idTache+"'";
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String setOrder = null;
        Cursor c = db.query("tache",projectionIn, selection, selectionArgs, groupBy, having, setOrder);

        if(c.getCount() > 0) {
            response = true;
        }

        return response;

    }

   public Tache trouverTache(String id)
    {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Tache t = null;
        Log.i("test","tests");
        Cursor c = db.query("tache", new String[] {"idTache","intituleT","dateCreationT","descriptionT","prioriteT","echeanceT","etatT","refGroupe"},"id="+id,null,null,null,null);
        Log.i("test","test");
        if (c.moveToFirst())
        {
            t = new Tache();
            t.setIdTache(c.getString(0));
            t.setIntituleT(c.getString(1));
            t.setDescriptionT(c.getString(2));
            t.setPrioriteT(c.getInt(3));
            t.setEtatT(c.getInt(5));
            t.setRefGroupe(c.getString(6));
            String strEcheance   = c.getString(4);
            Date dateEcheance = null;
            try {
                dateEcheance = format.parse(strEcheance);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            t.setEcheanceT(dateEcheance);
        }


        return t;
    }

    public ArrayList <Tache> listeTache()
    {
        //@TODO : Récupérer la date de création
        //Format des dates
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        ArrayList<Tache> listeT = new ArrayList<Tache>();
        db = dbmLocal.getReadableDatabase();
        Cursor c = db.query("tache", new String[] {"idTache","intituleT","dateCreationT","descriptionT","prioriteT","echeanceT","etatT","refGroupe"},null,null,null,null,null);
        c.moveToFirst();
        while (!c.isAfterLast())
        {
            Tache t = new Tache();
            t.setIntituleT(c.getString(1));

            //Enregistrement de la date de création de la tâche
            String strCreation = c.getString(2);
            Date dateCreation = null;
            try {
                dateCreation = format.parse(strCreation);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            t.setDateCreationT(dateCreation);

            t.setDescriptionT(c.getString(3));
            t.setPrioriteT(c.getInt(4));
            t.setEtatT(c.getInt(6));
            t.setRefGroupe(c.getString(7));
            String strEcheance   = c.getString(5);

            Date dateEcheance = null;
            try {
                dateEcheance = format.parse(strEcheance);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            t.setEcheanceT(dateEcheance);
            t.setIdTache(c.getString(0));
            listeT.add(t);
            c.moveToNext();
        }

        return listeT;
    }

    /**
     * Met à jour les tâches sur le serveur local
     */
    //@TODO : Supprimer si on ne s'en sert pas
    public void syncTasks() {

        Log.i("test","Je suis dans la synchronisation de tâches");

        List<Tache> listeTaches = null;
        Call<List<Tache>> call = apiService.getAllTasks();
        Log.i("test","Je suis après le getAllATasks");

        call.enqueue(new Callback<List<Tache>>() {

            @Override
            public void onResponse(Call<List<Tache>> call, Response<List<Tache>> response) {
                Log.i("test","Je vais récupérer les tâches");
                List<Tache> lesTaches = response.body();
                for (int i = 0; i < lesTaches.size(); i++) {
                    Tache currentTache = lesTaches.get(i);
                    if(!alreadyExists(currentTache.getIdTache())) {
                        Log.i("test","Je vais ajouter une tâche !");
                        ajouterTache(lesTaches.get(i));
                    } else {
                        Log.i("test","La tache existe déjà");
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Tache>> call, Throwable t) {
                Log.i("test","Ca n'a pas fonctionné");
                Log.i("test","Erreur : " + t.getMessage());
            }




        });

    }



}
