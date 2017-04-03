package com.elbejaj.sharetm;

/**
 * Created by Valentin on 02/04/2017.
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
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static java.lang.Thread.sleep;


public class ListeMembresActivity extends AppCompatActivity  {

    MembreGroupeDAO mgd;
    UtilisateurDAO ud;
    LinearLayout main_layout;

    private SharedPreferences mesPreferences;
    private boolean userConnected;
    private String idRegisteredUser;
    private boolean isConnected;

    ArrayList<MembreGroupe> tabMembreGroupe = new ArrayList<MembreGroupe>();
    ArrayList<Utilisateur> tabMembres = new ArrayList<Utilisateur>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_membres);


        //Récupération des données dans préférences
        mesPreferences = getSharedPreferences("ShareTaskManagerPreferences",0);
        this.userConnected = mesPreferences.getBoolean("userConnected",false);
        this.idRegisteredUser = mesPreferences.getString("idRegisteredUser","");

        //Vérifie s'il est connecté
        this.isConnected = hasActiveInternetConnection();

       // Call<List<Utilisateur>> call = apiInterface.getUsersByGroupe(this.idRegisteredUser);

        Log.i("test","MAINACTIVITY - ID de l'utilisateur :"+this.idRegisteredUser);

        if(this.isConnected) {
            Log.i("test","La personne est connectée");
        } else {
            Log.i("test","La personne est hors-ligne");
        }




        //@TODO : A modifier, réuperer le isConnected du main
        mgd = new MembreGroupeDAO(this, isConnected);
        ud = new UtilisateurDAO(this,userConnected);

        boolean fromAddingGroup = false;
        Bundle extras = getIntent().getExtras();
        final String idGrp = extras.getString("idtu");
        Log.i("test","VALEUR du IDGRP DANS LISTEMEMBRES:  "+idGrp );
        if(extras != null) {
            fromAddingGroup = extras.getBoolean("fromAjoutGroupe");
        }

        if(isConnected && !fromAddingGroup) {
            Log.i("test","Je vais synchroniser les groupes");
            new SynchronisationMembreGroupeTask(this,mgd).execute(idGrp);
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.i("test","J'ai terminé la synchronisation des groupes");
        } else {
            Log.i("test","La personne n'est pas connectée, on ne peut pas synchroniser les groupes");
        }

        tabMembreGroupe = mgd.listeMembreGroupe(idGrp);
        tabMembres = ud.listeUtilsateurs(tabMembreGroupe);
        final int N = tabMembres.size();
        final LinearLayout[] myLinear = new LinearLayout[N];
        main_layout = (LinearLayout) findViewById(R.id.main_layout);

        for (int i = 0; i < N; i++) {
            //Creation du Linear Layout de la tâche
            final LinearLayout rowLinear = new LinearLayout(this);

            //Paramètre du Linear
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(30, 50, 30, 0);
            final String idToPass = tabMembreGroupe.get(i).getIdGroupe();
            final int pos = i;
            Log.i("ID du groupe", String.valueOf(tabMembreGroupe.get(i).getIdGroupe()));
            rowLinear.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intentAff = new Intent(ListeMembresActivity.this, AffichageGroupeActivity.class);
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
            final TextView membre = new TextView(this);
            rowLinear.addView(membre);
            membre.setText(tabMembres.get(i).getNomU());
            membre.setTextSize(18);
            membre.setId(i);
            membre.setTypeface(null, membre.getTypeface().BOLD);
            LinearLayout.LayoutParams nomParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 70);
            nomParam.setMargins(20, 20, 20, 10);
            membre.setLayoutParams(nomParam);

        }

        Button ajout = (Button) findViewById(R.id.ajout_button);

        ajout.setOnClickListener(new View.OnClickListener() {

                                       @Override
                                       public void onClick(View v) {
                                           ajout(idGrp);
                                       }
                                   }
        );


    }

    public void ajout(String idGrp)
    {
        //Bundle extras = getIntent().getExtras();
        //String idGrp = extras.getString("idtu");
        Intent intent = new Intent(ListeMembresActivity.this, AjoutMembreActivity.class);
        intent.putExtra("idtu", idGrp);
        startActivity(intent);

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

}
