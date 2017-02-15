package com.elbejaj.sharetm;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Bejaj on 14/01/2017.
 */

public class AffichageTacheActivity extends AppCompatActivity {

    LinearLayout main_layout;
    TacheDAO td;
    Tache tache;
    ArrayList<Tache> tabTache = new ArrayList<Tache>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tache_affichage);

        main_layout = (LinearLayout) findViewById(R.id.affichage_tache_layout);

        //@LAURIE
        //Vérification de la connexion
        boolean isConnected = hasActiveInternetConnection();
        TacheDAO td = new TacheDAO(this,isConnected);


        //td = new TacheDAO(this);
        //----------------------------
        tabTache = td.listeTache();
        final int N = tabTache.size();
        final RelativeLayout[] myLinear = new RelativeLayout[N];

            for (int i = 0; i < N; i++) {
                String idTache;
                Bundle extras = getIntent().getExtras();
                idTache = extras.getString("idpass");
                int idTacheI = Integer.parseInt(idTache);
                Log.i("INSIDE LIST", String.valueOf(idTacheI));
                Log.i("ID de la tache", String.valueOf(tabTache.get(i).getId()));
                if (idTacheI == tabTache.get(i).getId()) {
                    //Creation du Linear Layout de la tâche
                    final RelativeLayout rowLinear = new RelativeLayout(this);

                    //Paramètre du Linear
                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(30, 50, 30, 0);
                    rowLinear.setLayoutParams(lp);

                    if (tabTache.get(i).getPriorite() == 1) {
                        rowLinear.setBackgroundColor(getResources().getColor(R.color.prio_red));
                    } else if (tabTache.get(i).getPriorite() == 2) {
                        rowLinear.setBackgroundColor(getResources().getColor(R.color.prio_orange));
                    } else if (tabTache.get(i).getPriorite() == 3) {
                        rowLinear.setBackgroundColor(getResources().getColor(R.color.prio_yellow));
                    } else if (tabTache.get(i).getPriorite() == 4) {
                        rowLinear.setBackgroundColor(getResources().getColor(R.color.prio_green));
                    }

                    //Ajout des layout tâche
                    main_layout.addView(rowLinear);

                    //On stock les taches
                    myLinear[i] = rowLinear;

                    //Création du nom de la tache
                    final TextView nomTache = new TextView(this);
                    rowLinear.addView(nomTache);
                    nomTache.setText(tabTache.get(i).getNom());
                    nomTache.setTextSize(18);
                    nomTache.setId(R.id.nomTache);
                    nomTache.setTypeface(null, nomTache.getTypeface().BOLD);
                    RelativeLayout.LayoutParams nomParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 70);
                    nomParam.setMargins(20, 20, 20, 10);
                    nomTache.setLayoutParams(nomParam);

                    //Création du contenu
                    final TextView contenuTache = new TextView(this);
                    contenuTache.setId(R.id.contenuTache);
                    rowLinear.addView(contenuTache);
                    contenuTache.setText(tabTache.get(i).getContenu());
                    RelativeLayout.LayoutParams contenuParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    contenuParam.addRule(RelativeLayout.BELOW, R.id.dateTache);
                    contenuParam.setMargins(20, 0, 20, 30);
                    contenuTache.setLayoutParams(contenuParam);

                    //Création du date
                    final TextView dateTache = new TextView(this);
                    dateTache.setId(R.id.dateTache);
                    rowLinear.addView(dateTache);
                    dateTache.setText(String.valueOf(tabTache.get(i).getEcheance()));
                    dateTache.setTypeface(null, dateTache.getTypeface().ITALIC);
                    RelativeLayout.LayoutParams dateParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    dateParam.addRule(RelativeLayout.BELOW, R.id.nomTache);
                    dateParam.setMargins(20, 0, 20, 10);
                    dateTache.setLayoutParams(dateParam);

                }
            }
        }


    public void button_suppr(View view)
    {
        Intent intent = new Intent(AffichageTacheActivity.this, MainActivity.class);
        td.open();
        String idTache;
        Bundle extras = getIntent().getExtras();
        idTache = extras.getString("idpass");
        int idTacheI = Integer.parseInt(idTache);
        long lg = td.supprimerTache(idTacheI);
        td.close();
        startActivity(intent);
    }

    public void button_upd(View view)
    {
        Intent intent = new Intent(AffichageTacheActivity.this, UpdateTacheActivity.class);
        String idTache;
        Bundle extras = getIntent().getExtras();
        idTache = extras.getString("idpass");
        intent.putExtra("idtu", idTache);
        startActivity(intent);
    }

    public boolean hasActiveInternetConnection() {
        Context context;
        Boolean isConnected = false;
        ConnectivityManager connect = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connect!=null) {

            NetworkInfo[] information = connect.getAllNetworkInfo();
            if(information!=null) {
                for (int x = 0; x < information.length; x++) {
                    if (information[x].getState() == NetworkInfo.State.CONNECTED) {
                        isConnected = true;
                    }
                }
            } else {
                isConnected = false;
            }
        }
        return isConnected;
    }
}
