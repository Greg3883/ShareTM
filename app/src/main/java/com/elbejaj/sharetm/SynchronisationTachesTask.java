package com.elbejaj.sharetm;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Laurie on 08/03/2017.
 * Classe permettant de récupérer les tâches qui viennent du serveur vers la base de données
 * en local
 */

public class SynchronisationTachesTask extends AsyncTask <Void,Void,Void> {

    private Context myContext;
    private TacheDAO tacheDAO;
    final ProgressDialog progressDialog;

    public SynchronisationTachesTask (Context context,TacheDAO tacheDAO){
        this.myContext = context;
        this.tacheDAO = tacheDAO;

        //Instanciation du dialogue de chargement
        progressDialog = new ProgressDialog(this.myContext, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(false);
        progressDialog.setMessage("Synchronisation...");
    }

    @Override
    protected Void doInBackground(Void... params) {

        Log.i("test", "Je suis dans le onCreate de SynchronisationTachesTask");

        ApiInterface apiInterface = STMAPI.getClient().create(ApiInterface.class);

        Log.i("test","Je vais synchroniser les tâches");
        //tacheDAO.syncTasks();

        List<Tache> listeTaches = null;
        //@TODO : Implémenter la méthode getTasksByUser(User us)
        Call<List<Tache>> call = apiInterface.getAllTasks();

        try {
            Response<List<Tache>> response = call.execute();
            Log.i("test","On a réussi l'appel");
            List<Tache> lesTaches = response.body();
            for (int i = 0; i < lesTaches.size(); i++) {
                Tache currentTache = lesTaches.get(i);
                if(!tacheDAO.alreadyExists(currentTache.getIdTache())) {
                    Log.i("test","Je vais ajouter une tâche !");
                    tacheDAO.ajouterTache(lesTaches.get(i));
                    Log.i("test","synchronisationTachesTask, infos de la tache : " +lesTaches.get(i).afficherTache() );
                } else {
                    Log.i("test","La tache existe déjà");
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.i("test","on est après l'objet response");

        return null;

    }

    @Override
    protected void onProgressUpdate(Void... progress) {
        //@TODO : Voir pourquoi ça n'affiche rien
        this.progressDialog.show();
    }

    @Override
    protected void onPostExecute(Void result) {
        Toast.makeText(myContext, "Synchronisation terminée !",Toast.LENGTH_LONG).show();

    }


}
