package com.elbejaj.sharetm;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Laurie on 15/03/2017.
 */

public class DeleteTacheTask extends AsyncTask<String,Void,Boolean> {

    private Context myContext;
    private TacheDAO tDAO;
    private AffectationTacheDAO aftDAO;
    private Tache tache;
    private SharedPreferences mesPreferences;
    private AffectationTache aft;
    private String idu;
    private List<Tache> lesTaches;

    public DeleteTacheTask(Context context, TacheDAO tDAO, AffectationTacheDAO aftDAO, Tache tache, AffectationTache aft, String idu) {
        this.myContext = context;
        this.tDAO = tDAO;
        this.aftDAO = aftDAO;
        this.tache = tache;
        this.aft = aft;
        this.idu = idu;
    }

    @Override
    protected Boolean doInBackground(String... params) {

        Log.i("test", "Je suis dans le doInBackground de DeleteTacheTask");

        //VARIABLES
        Boolean aFonctionne = false;
        Boolean  suppression = false;
        Boolean stop = false;
        int cmpt = 0;

        ApiInterface apiInterface = STMAPI.getClient().create(ApiInterface.class);

        //Récupération des tâches dont l'utilisateur est administrateur
        Call<List<Tache>> call2 = apiInterface.getTaskAdminByUser(this.idu);
        Response<List<Tache>> response2 = null;
        try {
            response2 = call2.execute();
            this.lesTaches = response2.body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int tailleTableau = this.lesTaches.size();
        Log.i("test", "Taille du tableau de tâches : "+String.valueOf(tailleTableau));

        while ((cmpt <= tailleTableau) && !stop){
            Log.i("test","compteur : "+String.valueOf(cmpt));
            Tache tacheAcompare = this.lesTaches.get(cmpt);
            Log.i("test","Tache du tableau : "+tache.getIntituleT());
            Log.i("test","Tache en cours : "+ tacheAcompare.getIntituleT());
            if (tacheAcompare.getIdTache().equals(tache.getIdTache())){
                Log.i("test","Les deux tâches sont égales");
                Call<Void> call = apiInterface.deleteTask(tache.getIdTache());
                try {
                    Response<Void> response = call.execute();
                    suppression = true;
                    stop = true;
                } catch (IOException e) {
                    Log.i("Exception : ", e.getMessage());
                }
            }
            cmpt++;
        }

        Log.i("test","Suppression effectuée ? "+suppression);

        return suppression;
    }

    @Override
    protected void onProgressUpdate(Void... progress) {

    }

    @Override
    protected void onPostExecute(Boolean aFonctionne) {
    }

}
