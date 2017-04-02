package com.elbejaj.sharetm;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Laurie on 26/03/2017.
 */

public class UpdateTacheTask extends AsyncTask<Object,Void,Boolean> {

    private Context myContext;
    private TacheDAO tacheDAO;

    public UpdateTacheTask(Context context, TacheDAO tacheDAO) {
        this.myContext = context;
        this.tacheDAO = tacheDAO;
        }

    @Override
    protected Boolean doInBackground(Object... params) {

        //Détermine si la modification a fonctionné
        Boolean aFonctionne = false;

        Log.i("test", "Je suis dans le doInBackground de UpdateTacheTask");

        ApiInterface apiInterface = STMAPI.getClient().create(ApiInterface.class);

        //Récupération de l'id de la tâche
        String idTache = (String) params[0];
        String intituleT = (String) params[1];
        String descriptionT = (String) params[2];
        int prioriteT = (int) params[3];
        int etatT = (int) params[4];
        String echeanceT = (String) params[5];

        Log.i("test", "updateTacheTask : id de la tâche à modifier : " + idTache);
        Log.i("test", "updateTacheTask : intitule de la tâche à modifier : " + intituleT);
        Log.i("test", "updateTacheTask : description de la tâche à modifier : " + descriptionT);
        Log.i("test", "updateTacheTask : priorite de la tâche à modifier : " + prioriteT);
        Log.i("test", "updateTacheTask : etat de la tâche à modifier : " + etatT);
        Log.i("test", "updateTacheTask : echeance de la tâche à modifier : " + echeanceT);


        //Instanciation de l'appel à la méthode updateTask()
        Call<Tache> call = apiInterface.updateTask(idTache, intituleT, descriptionT, prioriteT, etatT, echeanceT);

        //Exécution de la méthode login()
        try {
            Response<Tache> response = call.execute();
            if (response.body() != null) { //Le login a fonctionné
                Log.i("test","updateTacheTask :response n'est pas null");
                Tache currentTache = response.body();
                aFonctionne = true;

            } else {
                Log.i("test", "updateTacheTask :response est nul");
            }
        } catch (IOException e) {
            Log.i("test", e.getMessage());
        }
        return aFonctionne;
    }

    @Override
    protected void onProgressUpdate(Void... progress) {
        //@TODO : Voir pourquoi ça n'affiche rien
        //this.progressDialog.show();
        }

    @Override
    protected void onPostExecute(Boolean aFonctionne) {
        Toast.makeText(myContext, "Connexion réussie !",Toast.LENGTH_LONG).show();
        }
    }
