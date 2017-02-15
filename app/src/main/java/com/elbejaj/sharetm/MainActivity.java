package com.elbejaj.sharetm;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    LinearLayout main_layout;
    //TacheDAO td;                                            //Gestionnaire des tâches
    //GroupeDAO gd;                                           //Gestionnaire des groupes
    ArrayList<Tache> tabTache;     //Liste des tâches
    ImageView add_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tache);

        //@LAURIE
        boolean isConnected = hasActiveInternetConnection();
        TacheDAO td = new TacheDAO(this,isConnected);

        td.syncTasks();

        Log.i("test","Je suis après le sync");



        tabTache = td.listeTache();
        final int N = tabTache.size();

                //Récupération des tâches sur le serveur distant

        add_button = (ImageView) findViewById(R.id.add_button);

        //Si on est hors ligne, on ne peut pas voir le bouton d'ajout de tâche
        if(!isConnected) {
            add_button.setVisibility(View.INVISIBLE);
        }

        Log.i("test","Je suis après le isConnected");

        main_layout = (LinearLayout) findViewById(R.id.main_layout);
        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");



        final LinearLayout[] myLinear = new LinearLayout[N];

        Groupe g = new Groupe();
        g.setNomGroupe("Personnel");
        g.setIdGroupe(1);

        Log.i("test","Je suis avant le groupe");
        Groupe gb = new Groupe();
        Log.i("test","Je suis après le nouveau groupe");

        GroupeDAO gd = new GroupeDAO(this);

        gd.open();
            gb = gd.trouverGroupe(1);
            if (gb == null) {
                Log.i("INSIDE LIST", "AJOUT");
                long lg = gd.ajouterGroupe(g);
            }
        gd.close();

        Log.i("test","Je suis après le close");

       for (int k = 0; k < N; k++) {
           Log.i("test","Je suis dans le for");

           //Creation du Linear Layout de la tâche
           final LinearLayout rowLinear = new LinearLayout(this);

           //Paramètre du Linear
           LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 200);
           lp.setMargins(30, 50, 30, 0);
           rowLinear.setOrientation(LinearLayout.VERTICAL);
           final String idToPass = Integer.toString(tabTache.get(k).getId());
           Log.i("ID de la tache", String.valueOf(tabTache.get(k).getId()));
           rowLinear.setOnClickListener(new View.OnClickListener() {

               @Override
               public void onClick(View v) {
                   Intent intentAff = new Intent(MainActivity.this, AffichageTacheActivity.class);
                   String strName = null;
                   intentAff.putExtra("idpass", idToPass);
                   startActivity(intentAff);
               }
           });
           rowLinear.setLayoutParams(lp);

           Log.i("test","je suis avec le get(i)");
           if (tabTache.get(k).getPriorite() == 1) {
               rowLinear.setBackgroundColor(getResources().getColor(R.color.prio_red));
           }else if (tabTache.get(k).getPriorite() == 2) {
               rowLinear.setBackgroundColor(getResources().getColor(R.color.prio_orange));
           } else if (tabTache.get(k).getPriorite() == 3) {
               rowLinear.setBackgroundColor(getResources().getColor(R.color.prio_green));
           }

           if (tabTache.get(k).getEtat() == 2) {
               rowLinear.setBackgroundColor(getResources().getColor(R.color.en_attente));
           } else if (tabTache.get(k).getEtat() == 3) {
               rowLinear.setBackgroundColor(getResources().getColor(R.color.bleu_flat));
           }



           //Ajout des layout tâche
            main_layout.addView(rowLinear);

           //On stock les taches
            myLinear[k] = rowLinear;

           //Création du nom de la tache
           final TextView nomTache = new TextView(this);
           rowLinear.addView(nomTache);
           nomTache.setText(tabTache.get(k).getNom());
           nomTache.setTextSize(18);
           nomTache.setId(R.id.nomTache);
           nomTache.setTypeface(null, nomTache.getTypeface().BOLD);
           LinearLayout.LayoutParams nomParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
           nomParam.setMargins(20, 20, 0, 0);
           nomTache.setLayoutParams(nomParam);

           //Affichage du contenu
           final TextView contenuTache = new TextView(this);
           contenuTache.setId(R.id.contenuTache);
           rowLinear.addView(contenuTache);
           contenuTache.setText(tabTache.get(k).getContenu());
           LinearLayout.LayoutParams contenuParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 70);
           contenuParam.setMargins(20, 0, 0, 0);
           contenuTache.setLayoutParams(contenuParam);

           //Affichage de la date d'échéance
           final TextView dateTache = new TextView(this);
           dateTache.setId(R.id.dateTache);
           rowLinear.addView(dateTache);
           dateTache.setText(String.valueOf(tabTache.get(k).getEcheance()));
           dateTache.setTypeface(null, dateTache.getTypeface().ITALIC);
           LinearLayout.LayoutParams dateParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 70);
           dateParam.setMargins(20, 0, 0, 0);
           dateTache.setLayoutParams(dateParam);


        }

    }

    public void button_ajout(View view)
    {
        Intent intent = new Intent(MainActivity.this, AjoutActivity.class);
        startActivity(intent);
    }

    public void intent_groupe(View view)
    {
        Intent intent = new Intent(MainActivity.this, GroupeActivity.class);
        startActivity(intent);
    }

    /**
     * @brief Determine si le terminal est connecté à Internet
     * @return Vrai si le terminal est connecté à Internet
     */
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
