package com.elbejaj.sharetm;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Laurie on 15/03/2017.
 */

public class AjoutTask extends AsyncTask<String,Void,Boolean> {

    private Context myContext;
    private TacheDAO tDAO;
    private AffectationTacheDAO aftDAO;
    private Tache tache;
    private SharedPreferences mesPreferences;
    private AffectationTache aft;
    private String idu;

    public AjoutTask(Context context, TacheDAO tDAO, AffectationTacheDAO aftDAO, Tache tache, AffectationTache aft, String idu) {
        this.myContext = context;
        this.tDAO = tDAO;
        this.aftDAO = aftDAO;
        this.tache = tache;
        this.aft = aft;
        this.idu = idu;
    }

    @Override
    protected Boolean doInBackground(String... params) {

        //Détermine si le login a fonctionné
        Boolean aFonctionne = false;
        Boolean bFonctionne = false;
        DateFormat form = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = form.format(tache.getEcheanceT());

        Log.i("test", "Je suis dans le doInBackground de LoginTask");

        ApiInterface apiInterface = STMAPI.getClient().create(ApiInterface.class);


        //Instanciation de l'appel à la méthode login()
        Call<Tache> call = apiInterface.createTask(tache.getIntituleT(),tache.getDescriptionT(),tache.getPrioriteT(),tache.getEtatT(),formattedDate,tache.getRefGroupe(),this.idu);
        //Call<Tache> call1 = apiInterface.createAffectationTache(mesPreferences.getString("idRegisteredUser",""),tache.getIdTache(),aft.isAdmin());
        //Exécution de la méthode login()
        try {
            Response<Tache> response = call.execute();
            if(response.body()!= null) {//L'ajout a fonctionné
                if(response.body().getIdTache()!=null) {
                    aFonctionne = true;
                }
            }
        } catch (IOException e) {
            Log.i("test", e.getMessage());
        }

        /*try {
            Response<Tache> response = call1.execute();
        } catch (IOException e) {
            Log.i("test", e.getMessage());
        }*/


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
