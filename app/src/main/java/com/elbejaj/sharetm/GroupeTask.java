package com.elbejaj.sharetm;

/**
 * Created by Valentin on 27/03/2017.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Response;

public class GroupeTask extends AsyncTask<String,Void,Boolean> {

    private Context myContext;
    public GroupeDAO groupeDAO;

    public GroupeTask(Context context, GroupeDAO gd) {
        this.myContext = context;
        this.groupeDAO = gd;
    }

    protected Boolean doInBackground(String... params) {

        //Détermine si le register a fonctionné
        Boolean aFonctionne = false;

        Log.i("test", "Je suis dans le doInBackground de GroupeTask");

        ApiInterface apiInterface = STMAPI.getClient().create(ApiInterface.class);

        //Récupération du nom
        String nom = params[0];

        SharedPreferences mesPreferences = myContext.getSharedPreferences("ShareTaskManagerPreferences",0);
        String idRegisteredUser = mesPreferences.getString("idRegisteredUser","");

        Log.i("test","GroupeTAsk - ID de l'utilisateur :"+idRegisteredUser);

        //Instanciation de l'appel à la méthode createGroupe()
        Call<Groupe> call = apiInterface.createGroupe(nom,idRegisteredUser);
        //Exécution de la méthode createGroupe()
        try {
            Response<Groupe> response = call.execute();
            Log.i("test","On a réussi l'appel");
            if(response.body().getIdGroupe() != null) {
                /*Groupe currentGroupe = response.body();
                Log.i("test","av Open gd dans grTask");
                groupeDAO.open();
                Log.i("test","ap Open gd dans grTask");
                groupeDAO.ajouterGroupe(currentGroupe);
                Log.i("test","ap Close gd dans grTask");
                groupeDAO.close();
                Log.i("test","ap Close gd dans grTask");*/
                aFonctionne = true;
            }
        } catch (IOException e) {
            Log.i("test", e.getMessage());
        }

        return aFonctionne;
    }

    @Override
    protected void onPostExecute(Boolean aFonctionne) {
        Toast.makeText(myContext, "Ajout réussi !",Toast.LENGTH_LONG).show();
    }

}
