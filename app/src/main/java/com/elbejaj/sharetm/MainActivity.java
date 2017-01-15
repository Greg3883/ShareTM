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
           LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 200);
           lp.setMargins(30, 50, 30, 0);
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
           }else if (tabTache.get(i).getPriorite() == 3) {
               rowLinear.setBackgroundColor(getResources().getColor(R.color.prio_yellow));
           }
           else if (tabTache.get(i).getPriorite() == 4) {
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
           LinearLayout.LayoutParams nomParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
           nomParam.setMargins(20, 20, 20, 10);
           nomTache.setLayoutParams(nomParam);

           //Création du contenu
           final TextView contenuTache = new TextView(this);
           contenuTache.setId(R.id.contenuTache);
           rowLinear.addView(contenuTache);
           contenuTache.setText(tabTache.get(i).getContenu());
           LinearLayout.LayoutParams contenuParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 70);
           contenuTache.setLayoutParams(contenuParam);

           //Création du date
           final TextView dateTache = new TextView(this);
           dateTache.setId(R.id.dateTache);
           rowLinear.addView(dateTache);
           dateTache.setText(String.valueOf(tabTache.get(i).getId()));
           dateTache.setTypeface(null, dateTache.getTypeface().ITALIC);
           LinearLayout.LayoutParams dateParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 70);
           dateParam.setMargins(20, 0, 20, 10);
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

}
