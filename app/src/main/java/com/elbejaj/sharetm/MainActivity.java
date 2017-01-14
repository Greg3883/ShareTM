package com.elbejaj.sharetm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import java.util.Date;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import android.widget.GridLayout.LayoutParams;

public class MainActivity extends AppCompatActivity {


    LinearLayout main_layout;
    TacheDAO td;
    TextView aff_name_tache;
    TextView aff_content_tache;




    ArrayList<Tache> tabTache = new ArrayList<Tache>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tache);


        main_layout = (LinearLayout) findViewById(R.id.main_layout);

        td = new TacheDAO(this);
        tabTache = td.listeTache();
        final int N = tabTache.size();
        final RelativeLayout[] myLinear = new RelativeLayout[N];




       for (int i = 0; i < N; i++) {
           //Creation du Linear Layout de la tâche
           final RelativeLayout rowLinear = new RelativeLayout(this);

           //Paramètre du Linear
           RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
           lp.setMargins(30, 50, 30, 0);
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
           RelativeLayout.LayoutParams nomParam = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,70);
           nomParam.setMargins(20, 20, 20, 10);
           nomTache.setLayoutParams(nomParam);

           //Création du contenu
           final TextView contenuTache = new TextView(this);
           contenuTache.setId(R.id.contenuTache);
           rowLinear.addView(contenuTache);
           contenuTache.setText(tabTache.get(i).getContenu());
           RelativeLayout.LayoutParams contenuParam = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
           contenuParam.addRule(RelativeLayout.BELOW, R.id.dateTache);
           contenuParam.setMargins(20, 0, 20, 30);
           contenuTache.setLayoutParams(contenuParam);

           //Création du date
           final TextView dateTache = new TextView(this);
           dateTache.setId(R.id.dateTache);
           rowLinear.addView(dateTache);
           dateTache.setText(String.valueOf(tabTache.get(i).getId()));
           dateTache.setTypeface(null, dateTache.getTypeface().ITALIC);
           RelativeLayout.LayoutParams dateParam = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
           dateParam.addRule(RelativeLayout.BELOW, R.id.nomTache);
           dateParam.setMargins(20, 0, 20, 10);
           dateTache.setLayoutParams(dateParam);


        }

    }

    public void button_ajout(View view)
    {
        Intent intent = new Intent(MainActivity.this, AjoutActivity.class);
        startActivity(intent);
    }

    public void button_ajoutGroupe(View view)
    {
        Intent intent = new Intent(MainActivity.this, FormAddGroup.class);
        startActivity(intent);
    }
}
