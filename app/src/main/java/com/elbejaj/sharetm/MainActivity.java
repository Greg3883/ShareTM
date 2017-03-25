package com.elbejaj.sharetm;

//LAURIE

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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    LinearLayout main_layout;
    TextView aff_name_tache;
    TextView aff_content_tache;
    Button ajout_button;                   //Bouton d'ajout d'une tâche

    ArrayList<Tache> tabTache = new ArrayList<Tache>();

    private SharedPreferences mesPreferences;
    private boolean userConnected;
    private String idRegisteredUser;
    private boolean isConnected;

    //Menu de l'application (haut-droite)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i("test","Je rentre dans le onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tache);

        //Récupération des données dans préférences
        mesPreferences = getSharedPreferences("ShareTaskManagerPreferences",0);
        this.userConnected = mesPreferences.getBoolean("userConnected",false);
        this.idRegisteredUser = mesPreferences.getString("idRegisteredUser","");

        //Récupération des informations envoyées à l'activité
        Bundle extras = getIntent().getExtras();
        if(extras==null) {
            this.isConnected = false;
        } else {
            this.isConnected=extras.getBoolean("isConnected");
        }

        Log.i("test","MAINACTIVITY - ID de l'utilisateur :"+this.idRegisteredUser);

        if(this.isConnected) {
            Log.i("test","La personne est connectée");
        } else {
            Log.i("test","La personne est hors-ligne");
        }

        TacheDAO td = new TacheDAO(this,isConnected);

        //Si on est connecté, on synchronise les tâches avec le serveur
        if(isConnected) {
            Log.i("test","Je vais synchroniser les tâches");
            new SynchronisationTachesTask(this,td).execute();
            Log.i("test","J'ai terminé la synchronisation des tâches");
        } else {
            Log.i("test","La personne n'est pas connectée, on ne peut pas synchroniser les tâches");
        }


        Log.i("test","Je suis après le sync");
        tabTache = td.listeTache();
        Log.i("test","normalement ca marche");
        final int N = tabTache.size();

        //Si on est hors ligne, on ne peut pas voir le bouton d'ajout d'une tâche
        ajout_button = (Button) findViewById(R.id.ajout_button);
        if(!isConnected) {
            ajout_button.setVisibility(View.INVISIBLE);
        }

        Log.i("test","Je suis après le isConnected");

        main_layout = (LinearLayout) findViewById(R.id.main_layout);
        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        final LinearLayout[] myLinear = new LinearLayout[N];

       for (int i = 0; i < N; i++) {

           Log.i("test","Je suis dans le for du mainActivity");

           //Creation du Linear Layout de la tâche
           final LinearLayout principLinear = new LinearLayout(this);

           //Creation du Linear Layout des infos
           final LinearLayout rowLinear = new LinearLayout(this);

           //Creation du Linear Layout des états
           final LinearLayout rowEtat = new LinearLayout(this);

           //Paramètre du Linear
           LinearLayout.LayoutParams lpb = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 300);
           lpb.setMargins(30, 50, 0, 0);
           final String idToPassb = tabTache.get(i).getIdTache();
           Log.i("ID de la tache", String.valueOf(tabTache.get(i).getIdTache()));
           rowEtat.setOnClickListener(new View.OnClickListener() {

               @Override
               public void onClick(View v) {
                   Intent intentAff = new Intent(MainActivity.this, AffichageTacheActivity.class);
                   String strName = null;
                   intentAff.putExtra("idpass", idToPassb);
                   startActivity(intentAff);
               }
           });
           rowEtat.setLayoutParams(lpb);

           //Paramètre du Linear
           LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 300);
           lp.setMargins(0, 50, 30, 0);
           rowLinear.setOrientation(LinearLayout.VERTICAL);
           final String idToPass = tabTache.get(i).getIdTache();
           Log.i("ID de la tache", String.valueOf(tabTache.get(i).getIdTache()));
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
            tabTache.get(i).estRetard();
           if (tabTache.get(i).getPrioriteT() == 1) {
               rowLinear.setBackgroundColor(getResources().getColor(R.color.prio_red));
           }else if (tabTache.get(i).getPrioriteT() == 2) {
               rowLinear.setBackgroundColor(getResources().getColor(R.color.prio_orange));
           } else if (tabTache.get(i).getPrioriteT() == 3) {
               rowLinear.setBackgroundColor(getResources().getColor(R.color.prio_green));
           }

           if (tabTache.get(i).getPrioriteT() == 1) {
               rowEtat.setBackgroundColor(getResources().getColor(R.color.prio_red));
           }else if (tabTache.get(i).getPrioriteT() == 2) {
               rowEtat.setBackgroundColor(getResources().getColor(R.color.prio_orange));
           } else if (tabTache.get(i).getPrioriteT() == 3) {
               rowEtat.setBackgroundColor(getResources().getColor(R.color.prio_green));
           }


           //On stock les taches

            myLinear[i] = rowLinear;

           //Création du nom de la tache
           final TextView nomTache = new TextView(this);
           rowLinear.addView(nomTache);
           nomTache.setText(tabTache.get(i).getIntituleT());
           nomTache.setTextSize(18);
           nomTache.setId(R.id.nomTache);
           nomTache.setTextSize(20);
           nomTache.setTypeface(null, nomTache.getTypeface().BOLD);
           LinearLayout.LayoutParams nomParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
           nomParam.setMargins(20, 20, 0, 0);
           nomTache.setLayoutParams(nomParam);

           //Création du contenu
           final TextView contenuTache = new TextView(this);
           contenuTache.setId(R.id.contenuTache);
           rowLinear.addView(contenuTache);
           contenuTache.setTextSize(18);
           contenuTache.setText(tabTache.get(i).getDescriptionT());
           LinearLayout.LayoutParams contenuParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
           contenuParam.setMargins(20, 0, 20, 0);
           contenuTache.setLayoutParams(contenuParam);

           //Création du date
           final TextView dateTache = new TextView(this);
           dateTache.setId(R.id.dateTache);
           rowLinear.addView(dateTache);
           dateTache.setTextSize(16);
           Date preDate = tabTache.get(i).getEcheanceT();
           SimpleDateFormat preDateb = new SimpleDateFormat("dd/MM/yyyy");
           String newDate = preDateb.format( preDate );
           dateTache.setText(newDate);
           dateTache.setTypeface(null, dateTache.getTypeface().ITALIC);
           LinearLayout.LayoutParams dateParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
           dateParam.setMargins(20, 0, 0, 0);
           dateTache.setLayoutParams(dateParam);


           ImageView etatImg = new ImageView(this);
           int state = tabTache.get(i).getEtatT();
           if (state == 1) {
               etatImg.setImageResource(R.drawable.encours);
           } else if (state == 2) {
               etatImg.setImageResource(R.drawable.attente);
           } else if (state == 3) {
               etatImg.setImageResource(R.drawable.valide);
           } else if (state == 4) {
               etatImg.setImageResource(R.drawable.retard);
           }

           rowEtat.addView(etatImg);
           etatImg.getLayoutParams().height = 150;
           etatImg.getLayoutParams().width = 150;
           etatImg.requestLayout();
           LinearLayout.LayoutParams lpc = new LinearLayout.LayoutParams(150, LinearLayout.LayoutParams.WRAP_CONTENT);
           lpc.setMargins(10, 0, 0, 0);
           etatImg.setLayoutParams(lpc);
           //Ajout des layout tâche
           principLinear.addView(rowEtat);

           //Ajout des layout tâche
           principLinear.addView(rowLinear);

           //Ajout des layout tâche
           main_layout.addView(principLinear);


        }




        Log.i("test","Je suis après le for");

    }



    public void button_ajout(View view)
    {
        Intent intent = new Intent(MainActivity.this, AjoutActivity.class);
        startActivity(intent);
    }

    public void intent_groupe(View view)
    {
        Intent intent = new Intent(MainActivity.this, GroupesActivity.class);
        startActivity(intent);
    }

    public void intent_tachegroup(View view)
    {
        Intent intent = new Intent(MainActivity.this, TacheGroupActivity.class);
        startActivity(intent);
    }

    /**
     * @brief Determine si le terminal est connecté à Internet
     * @return Vrai si le terminal est connecté à Internet
     */
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
