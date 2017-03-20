package com.elbejaj.sharetm;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Laurie on 15/03/2017.
 */

public class LoginTask extends AsyncTask<String,Void,Boolean> {

    private Context myContext;
    private UtilisateurDAO utilisateurDAO;

    public LoginTask(Context context, UtilisateurDAO utilisateurDAO) {
        this.myContext = context;
        this.utilisateurDAO = utilisateurDAO;
    }

    @Override
    protected Boolean doInBackground(String... params) {

        //Détermine si le login a fonctionné
        Boolean aFonctionne = false;

        Log.i("test", "Je suis dans le doInBackground de LoginTask");

        ApiInterface apiInterface = STMAPI.getClient().create(ApiInterface.class);

        //Récupération de l'email et du mot de passe
        String email = params[0];
        String mdpHash = params[1];

        //Instanciation de l'appel à la méthode login()
        Call<Utilisateur> call = apiInterface.login(email,mdpHash);

        //Exécution de la méthode login()
        try {
            Response<Utilisateur> response = call.execute();
            if(response.body().getIdUtilisateur() != null) { //Le login a fonctionné

                Utilisateur currentUtilisateur = response.body();
                utilisateurDAO.ajouterUtilisateur(currentUtilisateur);

                //Mise à jour des préférences
                 SharedPreferences settings = myContext.getSharedPreferences("ShareTaskManagerPreferences", 0);
                 SharedPreferences.Editor editor = settings.edit();
                 editor.putBoolean("userConnected", true);
                //@TODO : Stocker l'email de l'utilisateur actuellement connecté
                 editor.commit();
                Log.i("test","Variables userConnected maj");
                aFonctionne = true;
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
