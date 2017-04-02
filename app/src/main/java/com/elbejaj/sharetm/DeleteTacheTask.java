package com.elbejaj.sharetm;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

        //Détermine si le login a fonctionné
        Boolean aFonctionne = false;

        Log.i("test", "Je suis dans le doInBackground de LoginTask");

        ApiInterface apiInterface = STMAPI.getClient().create(ApiInterface.class);


        Call<List<Tache>> call2 = apiInterface.getTaskAdminByUser(this.idu);
        Response<List<Tache>> response2 = null;
        try {
            response2 = call2.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Tache> lesTaches = response2.body();
        Boolean  suppression = false;
        Boolean stop = false;
        int cmpt = 0;
        Log.i("COMPORTEMENT", String.valueOf(lesTaches.size()));
        while (cmpt <= lesTaches.size() && stop == false){
            Log.i("BAH OUAI", String.valueOf(cmpt));
            Tache tacheAcompare = lesTaches.get(cmpt);
            Log.i("JE SUIS DACOMPORTEMENT", tacheAcompare.getIntituleT());
            if (tacheAcompare == tache){
                Call<Void> call = apiInterface.deleteTask(tache.getIdTache());
                try {
                    Response<Void> response = call.execute();
                    suppression = true;
                    stop = true;
                } catch (IOException e) {
                    Log.i("test", e.getMessage());
                }
            }
            cmpt++;
        }
        /*for (int i = 0; i < lesTaches.size(); i++) {
            Tache tacheAcompare = lesTaches.get(i);
            if (tacheAcompare == tache){
                Call<Void> call = apiInterface.deleteTask(tache.getIdTache());
                try {
                    Response<Void> response = call.execute();
                    suppression = true;

                } catch (IOException e) {
                    Log.i("test", e.getMessage());
                }
            }
            Log.i("Test","TEsto : "+tacheAcompare.getIntituleT());
        }*/
        Log.i("--||TEST||--","Est-ce supprimer ? "+suppression);

        /*try {
            Response<Tache> response = call1.execute();
        } catch (IOException e) {
            Log.i("test", e.getMessage());
        }*/


        return suppression;
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
