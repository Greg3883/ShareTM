package com.elbejaj.sharetm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Bejaj on 14/01/2017.
 */

public class AffichageTacheActivity extends AppCompatActivity {

    LinearLayout main_layout;
    TacheDAO td;
    Tache tache;
    AffectationTache affectTache;
    Tache tacheEncours;
    AffectationTacheDAO taf;
    private String idUtilisateurCourant;
    private SharedPreferences mesPreferences;
    ArrayList<Tache> tabTache = new ArrayList<Tache>();

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
            case R.id.param:
                paramBtn();
                return true;
            case R.id.aprop:
                aprop();
                return true;
            case R.id.helpe:
                help();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void aprop(){

        Intent intent = new Intent(AffichageTacheActivity.this, AproposActivity.class);
        startActivity(intent);
    }

    private void help(){

        Intent intent = new Intent(AffichageTacheActivity.this, HelpActivity.class);
        startActivity(intent);
    }

    private void paramBtn(){

        Intent intent = new Intent(AffichageTacheActivity.this, ParamActivity.class);
        startActivity(intent);
    }

    private void logoutBtn(){
        Intent intent = new Intent(AffichageTacheActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tache_affichage);

        //On cache les boutons de suppression et de modification si non connecté à Internet
        Button supp_button = (Button) findViewById(R.id.supp_button);
        Button modif_button = (Button) findViewById(R.id.modif_button);
        LinearLayout boutons = (LinearLayout) findViewById(R.id.boutons);

        if (hasActiveInternetConnection()) {
            boutons.setVisibility(View.VISIBLE);
            //supp_button.setVisibility(View.VISIBLE);
            //modif_button.setVisibility(View.VISIBLE);
        } else {
            boutons.setVisibility(View.INVISIBLE);

            //supp_button.setVisibility(View.INVISIBLE);
            //supp_button.setVisibility(View.INVISIBLE);
        }



        AffectationTacheDAO taf = new AffectationTacheDAO(this,true);
        main_layout = (LinearLayout) findViewById(R.id.layout_affichage);
        mesPreferences = getSharedPreferences("ShareTaskManagerPreferences",0);
        this.idUtilisateurCourant = mesPreferences.getString("idRegisteredUser","");
        //@TODO Changer ca
        td = new TacheDAO(this,true);
        tabTache = td.listeTache();
        final int N = tabTache.size();
        final RelativeLayout[] myLinear = new RelativeLayout[N];

            for (int i = 0; i < N; i++) {
                String idTacheI;
                Bundle extras = getIntent().getExtras();
                idTacheI = extras.getString("idpass");
                Log.i("INSIDE LIST", String.valueOf(idTacheI));
                Log.i("ID de la tache", String.valueOf(tabTache.get(i).getIdTache()));
                if (idTacheI.equals(tabTache.get(i).getIdTache())) {
                    //Creation du Linear Layout de la tâche
                    tacheEncours = tabTache.get(i);
                    final RelativeLayout rowLinear = new RelativeLayout(this);

                    //Paramètre du Linear
                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(30, 50, 30, 0);
                    rowLinear.setLayoutParams(lp);


                    //Ajout des layout tâche
                    main_layout.addView(rowLinear);

                    //On stock les taches
                    myLinear[i] = rowLinear;

                    //Création du nom de la tache
                    final TextView nomTache = new TextView(this);
                    rowLinear.addView(nomTache);
                    nomTache.setText(tabTache.get(i).getIntituleT().toUpperCase());
                    nomTache.setTextSize(24);
                    nomTache.setId(R.id.nomTache);
                    nomTache.setTypeface(null, nomTache.getTypeface().BOLD);
                    RelativeLayout.LayoutParams nomParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 70);
                    nomParam.setMargins(20, 20, 20, 10);
                    nomTache.setLayoutParams(nomParam);

                    //Création du contenu
                    final TextView contenuTache = new TextView(this);
                    contenuTache.setId(R.id.contenuTache);
                    rowLinear.addView(contenuTache);
                    contenuTache.setText(tabTache.get(i).getDescriptionT());
                    RelativeLayout.LayoutParams contenuParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    contenuParam.addRule(RelativeLayout.BELOW, R.id.dateTache);
                    contenuParam.setMargins(20, 0, 20, 30);
                    contenuTache.setLayoutParams(contenuParam);

                    //Création du date
                    final TextView dateTache = new TextView(this);
                    dateTache.setId(R.id.dateTache);
                    rowLinear.addView(dateTache);
                    Date preDate = tabTache.get(i).getEcheanceT();
                    SimpleDateFormat preDateb = new SimpleDateFormat("dd-MM-yyyy");
                    String newDate = preDateb.format( preDate );
                    dateTache.setText(newDate);
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
        Object aSuppr = false;
        Intent intent = new Intent(AffichageTacheActivity.this, MainActivity.class);
        td.open();
        String idTache;
        Bundle extras = getIntent().getExtras();
        idTache = extras.getString("idpass");
        Log.i("test","Id tache en cours :"+ tacheEncours.getIdTache());
        AsyncTask loginTask = new DeleteTacheTask(this, this.td,this.taf,tacheEncours,this.affectTache, this.idUtilisateurCourant).execute();
        try {
            aSuppr = (Boolean) loginTask.get();
        } catch (Exception e) {
            Log.i("test", e.getMessage());
        }
        if (aSuppr.toString().equals("true")) {
            long lg = td.supprimerTache(idTache);
        } else {
            Toast.makeText(getApplicationContext(), "Vous devez être administrateur de la tâche pour la supprimer",
                    Toast.LENGTH_LONG)
                    .show();
        }
        td.close();
        startActivity(intent);
    }

    public void button_upd(View view)
    {
        Intent intent = new Intent(AffichageTacheActivity.this, UpdateTacheActivity.class);
        String idTache;
        Log.i("test","Date des test324");
        Bundle extras = getIntent().getExtras();
        idTache = extras.getString("idpass");
        intent.putExtra("idtu", idTache);
        startActivity(intent);
        Log.i("test","Date des test");
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
