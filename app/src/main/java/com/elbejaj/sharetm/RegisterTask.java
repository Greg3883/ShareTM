package com.elbejaj.sharetm;

/**
 * Created by Valentin on 26/03/2017.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class RegisterTask extends AsyncTask<String,Void,Boolean> {

    private Context myContext;
    private UtilisateurDAO utilisateurDAO;

    public RegisterTask(Context context, UtilisateurDAO utilisateurDAO) {
        this.myContext = context;
        this.utilisateurDAO = utilisateurDAO;
    }

    @Override
    protected Boolean doInBackground(String... params) {

        //Détermine si le register a fonctionné
        Boolean aFonctionne = false;

        Log.i("test", "Je suis dans le doInBackground de RegisterTask");

        ApiInterface apiInterface = STMAPI.getClient().create(ApiInterface.class);

        //Récupération du nom, de l'email et du mot de passe
        String nom = params[0];
        String email = params[1];
        String mdpHash = params[2];

        //Instanciation de l'appel à la méthode register()
        Call<Utilisateur> call = apiInterface.register(nom,email,mdpHash);

        //Exécution de la méthode register()
        try {
            Response<Utilisateur> response = call.execute();
            Log.i("test","On a réussi l'appel");
            if(response.body().getIdUtilisateur() != null) {
                Utilisateur currentUtilisateur = response.body();
                utilisateurDAO.ajouterUtilisateur(currentUtilisateur);
                aFonctionne = true;
            }
        } catch (IOException e) {
            Log.i("test", e.getMessage());
        }

        return aFonctionne;
    }

    @Override
    protected void onPostExecute(Boolean aFonctionne) {
        Toast.makeText(myContext, "Inscription réussie !",Toast.LENGTH_LONG).show();
    }

}
