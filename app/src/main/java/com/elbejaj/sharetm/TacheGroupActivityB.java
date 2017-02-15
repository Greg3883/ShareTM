package com.elbejaj.sharetm;

//LAURIE

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TacheGroupActivityB extends AppCompatActivity {


    LinearLayout main_layout;
    TacheDAO td;
    GroupeDAO gd;
    Spinner tri_spinner;
    TextView aff_name_tache;
    TextView aff_content_tache;

    ArrayList<Tache> tabTache = new ArrayList<Tache>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tachegroup_afficahge);

        main_layout = (LinearLayout) findViewById(R.id.main_layout);
        tri_spinner = (Spinner) findViewById(R.id.tri_option);
        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        List spinnerPrio = new ArrayList();
        spinnerPrio.add("Trier par date");
        spinnerPrio.add("Trier par priorité");
        spinnerPrio.add("Trier par état");

        ArrayAdapter adapter = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                spinnerPrio
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tri_spinner.setAdapter(adapter);

        //TODO : A changer
        td = new TacheDAO(this, true);
        gd = new GroupeDAO(this);
        tabTache = td.listeTache();
        Collections.sort(tabTache, Tache.TachePrioComparator);
        final int N = tabTache.size();
        final LinearLayout[] myLinear = new LinearLayout[N];




        for (int i = 0; i < N; i++) {

            //Creation du Linear Layout de la tâche
            final LinearLayout principLinear = new LinearLayout(this);

            //Creation du Linear Layout des infos
            final LinearLayout rowLinear = new LinearLayout(this);

            //Creation du Linear Layout des états
            final LinearLayout rowEtat = new LinearLayout(this);

            //Paramètre du Linear
            LinearLayout.LayoutParams lpb = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 300);
            lpb.setMargins(30, 50, 0, 0);
            final String idToPassb = Integer.toString(tabTache.get(i).getId());
            Log.i("ID de la tache", String.valueOf(tabTache.get(i).getId()));
            rowEtat.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intentAff = new Intent(TacheGroupActivityB.this, AffichageTacheActivity.class);
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
            final String idToPass = Integer.toString(tabTache.get(i).getId());
            Log.i("ID de la tache", String.valueOf(tabTache.get(i).getId()));
            rowLinear.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intentAff = new Intent(TacheGroupActivityB.this, AffichageTacheActivity.class);
                    String strName = null;
                    intentAff.putExtra("idpass", idToPass);
                    startActivity(intentAff);
                }
            });
            rowLinear.setLayoutParams(lp);
            tabTache.get(i).estRetard();
            if (tabTache.get(i).getPriorite() == 1) {
                rowLinear.setBackgroundColor(getResources().getColor(R.color.prio_red));
            }else if (tabTache.get(i).getPriorite() == 2) {
                rowLinear.setBackgroundColor(getResources().getColor(R.color.prio_orange));
            } else if (tabTache.get(i).getPriorite() == 3) {
                rowLinear.setBackgroundColor(getResources().getColor(R.color.prio_green));
            }

            if (tabTache.get(i).getPriorite() == 1) {
                rowEtat.setBackgroundColor(getResources().getColor(R.color.prio_red));
            }else if (tabTache.get(i).getPriorite() == 2) {
                rowEtat.setBackgroundColor(getResources().getColor(R.color.prio_orange));
            } else if (tabTache.get(i).getPriorite() == 3) {
                rowEtat.setBackgroundColor(getResources().getColor(R.color.prio_green));
            }



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


            ImageView etatImg = new ImageView(this);
            int state = tabTache.get(i).getEtat();
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

    }

    public void button_ajout(View view)
    {
        Intent intent = new Intent(TacheGroupActivityB.this, AjoutActivity.class);
        startActivity(intent);
    }

    public void intent_groupe(View view)
    {
        Intent intent = new Intent(TacheGroupActivityB.this, GroupeActivity.class);
        startActivity(intent);
    }

    public void intent_tache(View view)
    {
        Intent intent = new Intent(TacheGroupActivityB.this, MainActivity.class);
        startActivity(intent);
    }


}
