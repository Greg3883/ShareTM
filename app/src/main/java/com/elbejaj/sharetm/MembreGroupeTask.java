package com.elbejaj.sharetm;

/**
 * Created by Valentin on 02/04/2017.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Response;

public class MembreGroupeTask extends AsyncTask<Object,Void,Boolean> {

    private Context myContext;
    public MembreGroupeDAO mgd;

    public MembreGroupeTask(Context context, MembreGroupeDAO mgd) {
        this.myContext = context;
        this.mgd = mgd;
    }

    protected Boolean doInBackground(Object... params) {

        //Détermine si le register a fonctionné
        Boolean aFonctionne = false;

        Log.i("test", "Je suis dans le doInBackground de MembreGroupeTask");

        ApiInterface apiInterface = STMAPI.getClient().create(ApiInterface.class);

        //Récupération du nom
        String idUtil = (String) params[0];
        String idGrp = (String) params[1];
        boolean estAdmin = (boolean) params[2];


        SharedPreferences mesPreferences = myContext.getSharedPreferences("ShareTaskManagerPreferences",0);
        String idRegisteredUser = mesPreferences.getString("idRegisteredUser","");

        Log.i("test","MembreGroupeTask - ID de l'utilisateur :"+idRegisteredUser);

        //Instanciation de l'appel à la méthode createGroupe()
        Call<MembreGroupe> call = apiInterface.createMembreGroupe(idUtil,idGrp,estAdmin);
        try {
            Response<MembreGroupe> response = call.execute();
            Log.i("test","On a réussi l'appel");
            //if(response.body().getIdMembreGroupe() != null) {
                aFonctionne = true;
           // }
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
