package com.elbejaj.sharetm;

/**
 * Created by Baalamor on 08/02/2017.
 */


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

import static baalamorapp.sharetm.R.id.nomTache;
import static baalamorapp.sharetm.R.layout.groupes;


public class AffichageGroupeActivity extends AppCompatActivity {


    LinearLayout main_layout;
    GroupeDAO gd;
    Groupe groupe;
    ArrayList<Groupe> tabGroupe = new ArrayList<Groupe>();
    String nom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupe_affichage);

        main_layout = (LinearLayout) findViewById(R.id.affichage_groupe_layout);
        gd = new GroupeDAO(this);
        tabGroupe = gd.listeGroupe();
        final int N = tabGroupe.size();
        final RelativeLayout[] myLinear = new RelativeLayout[N];

        for (int i = 0; i < N; i++) {
            String idGroupe;
            Bundle extras = getIntent().getExtras();
            idGroupe = extras.getString("idpass");
            int idGrpI = Integer.parseInt(idGroupe);
            Log.i("INSIDE LIST", String.valueOf(idGrpI));
            Log.i("ID du groupe", String.valueOf(tabGroupe.get(i).getId()));
            if (idGrpI == tabGroupe.get(i).getId()) {
                //Creation du Linear Layout du groupe
                final RelativeLayout rowLinear = new RelativeLayout(this);

                //Paramètre du Linear
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(30, 50, 30, 0);
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
                RelativeLayout.LayoutParams nomParam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 70);
                nomParam.setMargins(20, 20, 20, 10);
                nomGroupe.setLayoutParams(nomParam);


            }
        }
    }



    public void button_suppr(View view)
    {
        Intent intent = new Intent(AffichageGroupeActivity.this, GroupesActivity.class);
        gd.open();
        String idGrp;
        Bundle extras = getIntent().getExtras();
        idGrp = extras.getString("idpass");
        int idGrpI = Integer.parseInt(idGrp);
        long lg = gd.supprimerGroupe(idGrpI);
        gd.close();
        startActivity(intent);
    }

    public void button_upd(View view)
    {
        Intent intent = new Intent(AffichageGroupeActivity.this, UpdateGroupeActivity.class);
        String idGrp;
        Bundle extras = getIntent().getExtras();
        int pos = extras.getInt("pos");
        idGrp = extras.getString("idpass");
        intent.putExtra("idtu", idGrp);
        intent.putExtra("position", pos);
        startActivity(intent);
    }

}
