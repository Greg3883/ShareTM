package com.elbejaj.sharetm;

//LAURIE

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.app.DatePickerDialog;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    LinearLayout main_layout;
    TacheDAO td;
    GroupeDAO gd;
    TextView aff_name_tache;
    TextView aff_content_tache;

    ArrayList<Tache> tabTache = new ArrayList<Tache>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tache);

        main_layout = (LinearLayout) findViewById(R.id.main_layout);
        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        td = new TacheDAO(this);
        gd = new GroupeDAO(this);
        tabTache = td.listeTache();
        final int N = tabTache.size();
        final LinearLayout[] myLinear = new LinearLayout[N];

        Groupe g = new Groupe();
        g.setNomGroupe("Personnel");
        g.setIdGroupe(1);

        Groupe gb = new Groupe();

        gd.open();
            gb = gd.trouverGroupe(1);
            if (gb == null) {
                Log.i("INSIDE LIST", "AJOUT");
                long lg = gd.ajouterGroupe(g);
            }
        gd.close();



       for (int i = 0; i < N; i++) {
           //Creation du Linear Layout de la tâche
           final LinearLayout rowLinear = new LinearLayout(this);

           //Paramètre du Linear
           LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
           lp.setMargins(30, 50, 30, 0);
           rowLinear.setOrientation(LinearLayout.VERTICAL);
           final String idToPass = Integer.toString(tabTache.get(i).getId());
           Log.i("ID de la tache", String.valueOf(tabTache.get(i).getId()));
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

           if (tabTache.get(i).getPriorite() == 1) {
               rowLinear.setBackgroundColor(getResources().getColor(R.color.prio_red));
           }else if (tabTache.get(i).getPriorite() == 2) {
               rowLinear.setBackgroundColor(getResources().getColor(R.color.prio_orange));
           } else if (tabTache.get(i).getPriorite() == 3) {
               rowLinear.setBackgroundColor(getResources().getColor(R.color.prio_green));
           }

           if (tabTache.get(i).getEtat() == 2) {
               rowLinear.setBackgroundColor(getResources().getColor(R.color.en_attente));
           } else if (tabTache.get(i).getEtat() == 3) {
               rowLinear.setBackgroundColor(getResources().getColor(R.color.bleu_flat));
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
           contenuTache.setText(tabTache.get(i).getContenu());
           LinearLayout.LayoutParams contenuParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
           contenuParam.setMargins(20, 0, 20, 0);
           contenuTache.setLayoutParams(contenuParam);

           //Création du date
           final TextView dateTache = new TextView(this);
           dateTache.setId(R.id.dateTache);
           rowLinear.addView(dateTache);
           dateTache.setTextSize(16);
           Date preDate = tabTache.get(i).getEcheance();
           SimpleDateFormat preDateb = new SimpleDateFormat("dd/MM/yyyy");
           String newDate = preDateb.format( preDate );
           dateTache.setText(newDate);
           dateTache.setTypeface(null, dateTache.getTypeface().ITALIC);
           LinearLayout.LayoutParams dateParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
           dateParam.setMargins(20, 0, 0, 0);
           dateTache.setLayoutParams(dateParam);

           //Création de l'état
           final TextView etaTache = new TextView(this);
           etaTache.setId(R.id.etaTach);
           rowLinear.addView(etaTache);
           int etat_ru = tabTache.get(i).getEtat();
           String etat_rs;
           if (etat_ru == 1){
               etat_rs = "En cours";
           } else if(etat_ru == 2){
                etat_rs = "En attente";
           } else {
                etat_rs = "Validée";
           }
           etaTache.setText(etat_rs);
           etaTache.setTextSize(14);
           etaTache.setTypeface(null, etaTache.getTypeface().ITALIC);
           LinearLayout.LayoutParams etaParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
           etaParam.setMargins(20, 0, 0, 20);
           etaTache.setLayoutParams(etaParam);


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

}
