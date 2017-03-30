package com.elbejaj.sharetm;

/**
 * Created by Baalamor on 15/01/2017.
 */


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.os.AsyncTask;

import java.util.ArrayList;

public class GroupesActivity extends AppCompatActivity {

    GroupeDAO gd;
    LinearLayout main_layout;

    private SharedPreferences mesPreferences;
    private boolean userConnected;
    private String idRegisteredUser;
    private boolean isConnected;

    ArrayList<Groupe> tabGroupe = new ArrayList<Groupe>();

    //Menu de l'application (haut-droite)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.new_game:
                aprop();
                return true;
            case R.id.help:
                paramBtn();
                return true;
            case R.id.helpe:
                help();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void aprop(){

        Intent intent = new Intent(GroupesActivity.this, AproposActivity.class);
        startActivity(intent);
    }

    private void help(){

        Intent intent = new Intent(GroupesActivity.this, HelpActivity.class);
        startActivity(intent);
    }

    private void paramBtn(){

        Intent intent = new Intent(GroupesActivity.this, ParamActivity.class);
        startActivity(intent);
    }

    GridView simpleList;
    ArrayList birdList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groupes);


        //Récupération des données dans préférences
        mesPreferences = getSharedPreferences("ShareTaskManagerPreferences",0);
        this.userConnected = mesPreferences.getBoolean("userConnected",false);
        this.idRegisteredUser = mesPreferences.getString("idRegisteredUser","");

        //Vérifie s'il est connecté
        this.isConnected = hasActiveInternetConnection();

        Log.i("test","MAINACTIVITY - ID de l'utilisateur :"+this.idRegisteredUser);

        if(this.isConnected) {
            Log.i("test","La personne est connectée");
        } else {
            Log.i("test","La personne est hors-ligne");
        }




        //@TODO : A modifier, réuperer le isConnected du main
        gd = new GroupeDAO(this, isConnected);

        if(isConnected) {
            Log.i("test","Je vais synchroniser les tâches");
            new SynchronisationGroupesTask(this,gd).execute();
            Log.i("test","J'ai terminé la synchronisation des tâches");
        } else {
            Log.i("test","La personne n'est pas connectée, on ne peut pas synchroniser les tâches");
        }

        tabGroupe = gd.listeGroupe();
        final int N = tabGroupe.size();
        final LinearLayout[] myLinear = new LinearLayout[N];
        main_layout = (LinearLayout) findViewById(R.id.main_layout);

        for (int i = 0; i < N; i++) {
            //Creation du Linear Layout de la tâche
            final LinearLayout rowLinear = new LinearLayout(this);

            //Paramètre du Linear
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(30, 50, 30, 0);
            final String idToPass = tabGroupe.get(i).getIdGroupe();
            final int pos = i;
            Log.i("ID du groupe", String.valueOf(tabGroupe.get(i).getIdGroupe()));
            rowLinear.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intentAff = new Intent(GroupesActivity.this, AffichageGroupeActivity.class);
                    String strName = null;
                    intentAff.putExtra("idpass", idToPass);
                    intentAff.putExtra("pos",pos);
                    startActivity(intentAff);
                }
            });
            rowLinear.setLayoutParams(lp);





            rowLinear.setBackgroundColor(getResources().getColor(R.color.prio_red));

            //Ajout des layout tâche
            main_layout.addView(rowLinear);

            //On stock les taches
            myLinear[i] = rowLinear;

            //Création du nom de la tache
            final TextView nomGroupe = new TextView(this);
            rowLinear.addView(nomGroupe);
            nomGroupe.setText(tabGroupe.get(i).getNom());
            nomGroupe.setTextSize(18);
            nomGroupe.setId(i);
            nomGroupe.setTypeface(null, nomGroupe.getTypeface().BOLD);
            LinearLayout.LayoutParams nomParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 70);
            nomParam.setMargins(20, 20, 20, 10);
            nomGroupe.setLayoutParams(nomParam);



        }


    }


    public boolean hasActiveInternetConnection() {
        Log.i("test","Je suis au début du hasActiveInternetConnection");
        Context context;
        Boolean isConnected = false;
        ConnectivityManager connect = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        Log.i("test","Je suis avant le if");
        if(connect!=null) {
            Log.i("test","Connect n'est pas null");

            //NetworkInfo[] information = connect.getAllNetworkInfo();
            Network[] networks = connect.getAllNetworks();
            NetworkInfo networkInfo;

            Log.i("test","J'ai récupéré les networks");


            for(Network network : networks) {
                networkInfo = connect.getNetworkInfo(network);
                if(networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    isConnected = true;
                    break;
                }
            }

        }
        Log.i("test","Je suis juste avant le retour de la fonction");
        return isConnected;
    }


    public void taches(View view)
    {
        Intent intent = new Intent(GroupesActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void mes_taches(View view)
    {
        Intent intent = new Intent(GroupesActivity.this, TacheGroupActivity.class);
        startActivity(intent);
    }

    public void button_ajout(View view)
    {
        Intent intent = new Intent(GroupesActivity.this, AjoutGrpActivity.class);
        startActivity(intent);
    }

}
