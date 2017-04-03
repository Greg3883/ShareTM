package com.elbejaj.sharetm;

/**
 * Created by Valentin on 02/04/2017.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class SynchronisationMembreGroupeTask extends AsyncTask<String,Void,Void>  {

    private Context myContext;
    private MembreGroupeDAO mgd;
    final ProgressDialog progressDialog;
    private SharedPreferences mesPreferences;
    private String idUtilisateurCourant;


    public SynchronisationMembreGroupeTask (Context context,MembreGroupeDAO mgd){
        this.myContext = context;
        this.mgd = mgd;

        //Instanciation du dialogue de chargement
        progressDialog = new ProgressDialog(this.myContext, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(false);
        progressDialog.setMessage("Synchronisation...");
        //Récupération de l'ID de l'utilisateur courant
        mesPreferences = this.myContext.getSharedPreferences("ShareTaskManagerPreferences",0);
        this.idUtilisateurCourant = mesPreferences.getString("idRegisteredUser","");
    }

    protected Void doInBackground(String... params) {

        Log.i("test", "Je suis dans le onCreate de SynchronisationGroupesTask");

        ApiInterface apiInterface = STMAPI.getClient().create(ApiInterface.class);

        Log.i("test","Je vais synchroniser les membres");
        String idGrp = params[0];


        List<Groupe> listeGroupes = null;
        //@TODO : Implémenter la méthode getTasksByUser(User us)

        Log.i("test","SynchronisationGroupesTask, id Utilisateur courant : "+this.idUtilisateurCourant);
        Call<List<MembreGroupe>> call = apiInterface.getMembreByGrpId(idGrp);
        Log.i("test","Je suis après le call et avant le try");


        try {
            Response<List<MembreGroupe>> response = call.execute();
            Log.i("test","On a réussi l'appel");
            List<MembreGroupe> liste = response.body();
            if (!(liste==null)) {
                for (int i = 0; i < liste.size(); i++) {
                    MembreGroupe currentMembreGroupe = liste.get(i);
                    mgd.ajouterMembreGroupe(liste.get(i));
                }
            } else {
                Log.i("test","pas de membre");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.i("test","on est après l'objet response");

        return null;

    }

}
