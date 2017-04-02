package com.elbejaj.sharetm;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Laurie on 15/03/2017.
 */

public class SynchronisationTachesAdminTask extends AsyncTask<String,Void,Boolean> {

    private Context myContext;
    private TacheDAO tDAO;
    private AffectationTacheDAO aftDAO;
    private Tache tache;
    private SharedPreferences mesPreferences;
    private AffectationTache aft;
    private String idUtilisateurCourant;

    public SynchronisationTachesAdminTask(Context context, TacheDAO tDAO, AffectationTacheDAO aftDAO, String idu) {
        this.myContext = context;
        this.tDAO = tDAO;
        this.aftDAO = aftDAO;
        this.tache = tache;
        this.idUtilisateurCourant = idu;
        this.aft = aft;
    }

    @Override
    protected Boolean doInBackground(String... params) {

        //Détermine si le login a fonctionné
        Boolean aFonctionne = false;

        Log.i("test", "Je suis dans le doInBackground de LoginTask");

        ApiInterface apiInterface = STMAPI.getClient().create(ApiInterface.class);


        //Instanciation de l'appel à la méthode login()
        Call<List<Tache>> call = apiInterface.getTaskAdminByUser(this.idUtilisateurCourant);

        //Call<Tache> call1 = apiInterface.createAffectationTache(mesPreferences.getString("idRegisteredUser",""),tache.getIdTache(),aft.isAdmin());
        //Exécution de la méthode login()
        Response<List<Tache>> response = null;
        try {
            response = call.execute();
            aFonctionne = true;
        } catch (IOException e) {
            e.printStackTrace();
            aFonctionne =false;
        }
        List<Tache> lesTaches = response.body();



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
