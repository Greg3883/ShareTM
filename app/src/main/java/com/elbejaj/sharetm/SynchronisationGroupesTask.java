package com.elbejaj.sharetm;

/**
 * Created by Valentin on 30/03/2017.
 */


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;


public class SynchronisationGroupesTask extends AsyncTask <Void,Void,Void> {

    private Context myContext;
    private GroupeDAO groupeDAO;
    final ProgressDialog progressDialog;
    private SharedPreferences mesPreferences;
    private String idUtilisateurCourant;


    public SynchronisationGroupesTask (Context context,GroupeDAO gd){
        this.myContext = context;
        this.groupeDAO = gd;

        //Instanciation du dialogue de chargement
        progressDialog = new ProgressDialog(this.myContext, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(false);
        progressDialog.setMessage("Synchronisation...");
        //Récupération de l'ID de l'utilisateur courant
        mesPreferences = this.myContext.getSharedPreferences("ShareTaskManagerPreferences",0);
        this.idUtilisateurCourant = mesPreferences.getString("idRegisteredUser","");
    }


    protected Void doInBackground(Void... params) {

        Log.i("test", "Je suis dans le onCreate de SynchronisationGroupesTask");

        ApiInterface apiInterface = STMAPI.getClient().create(ApiInterface.class);

        Log.i("test","Je vais synchroniser les tâches");
        //tacheDAO.syncTasks();

        List<Groupe> listeGroupes = null;
        //@TODO : Implémenter la méthode getTasksByUser(User us)

        Log.i("test","SynchronisationGroupesTask, id Utilisateur courant : "+this.idUtilisateurCourant);
        Call<List<Groupe>> call = apiInterface.getGroupByUser(this.idUtilisateurCourant);
        //Call<List<Tache>> call = apiInterface.getAllTasks();
        Log.i("test","Je suis après le call et avant le try");


        try {
            Response<List<Groupe>> response = call.execute();
            Log.i("test","On a réussi l'appel");
            List<Groupe> lesGroupes = response.body();
            for (int i = 0; i < lesGroupes.size(); i++) {
                Groupe currentGroupe = lesGroupes.get(i);
                if(!groupeDAO.alreadyExists(currentGroupe.getIdGroupe())) {
                    Log.i("test","Je vais ajouter un groupe !");
                    groupeDAO.ajouterGroupe(lesGroupes.get(i));
                    Log.i("test","synchronisationGroupesTask, infos du groupe : " +lesGroupes.get(i).toString() );
                } else {
                    Log.i("test","Le groupe existe déjà");
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.i("test","on est après l'objet response");

        return null;

    }
}
