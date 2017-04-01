package com.elbejaj.sharetm;

/**
 * Created by Valentin on 26/03/2017.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Response;

import static java.lang.Thread.sleep;

public class RegisterTask extends AsyncTask<String,Void,String> {

    private Context myContext;
    private UtilisateurDAO utilisateurDAO;

    public RegisterTask(Context context, UtilisateurDAO utilisateurDAO) {
        this.myContext = context;
        if(utilisateurDAO != null) {
            this.utilisateurDAO = utilisateurDAO;
        } else {
            Log.i("test","RegisterTask - Constructeur : UtilisateurDAO est null");
            this.utilisateurDAO = new UtilisateurDAO(this.myContext,true);
        }

    }

    @Override
    protected String doInBackground(String... params) {

        //Détermine si le register a fonctionné
        String aFonctionne = "";

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
            if(response.body() != null) {
                Utilisateur currentUtilisateur = response.body();
                if(currentUtilisateur!=null && currentUtilisateur.getIdUtilisateur()!=null) {
                    //On peut ajouter
                    Log.i("test","idDel'utilisateur a ajouter : "+currentUtilisateur.getIdUtilisateur());
                    if(currentUtilisateur.getIdUtilisateur().equals("PB_MAIL")) {
                        Log.i("test","currentUtilisateur non null et idUtilisateur PB_MAIL");
                        aFonctionne = "PB_MAIL";
                        //Toast.makeText(myContext,"Cet email est déjà enregistré",Toast.LENGTH_LONG).show();
                    } else {
                        this.utilisateurDAO.ajouterUtilisateur(currentUtilisateur);

                        //Mise à jour des préférences
                        SharedPreferences settings = myContext.getSharedPreferences("ShareTaskManagerPreferences", 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putBoolean("userConnected", true);
                        String idRegisteredUser = currentUtilisateur.getIdUtilisateur();
                        editor.putString("idRegisteredUser",idRegisteredUser);
                        editor.commit();



                        aFonctionne = "USER_CREATED";
                    }
                } else {
                    aFonctionne = "USER_NOT_CREATED";
                    Log.i("test","Problème d'inscription");
                }

            }
        } catch (IOException e) {
            Log.i("test", e.getMessage());
        }

        return aFonctionne;
    }

    @Override
    protected void onPostExecute(String aFonctionne) {
        Toast.makeText(myContext, "Inscription réussie !",Toast.LENGTH_LONG).show();
    }

}
