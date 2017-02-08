package com.elbejaj.sharetm;

/**
 * Created by Baalamor on 15/01/2017.
 */


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class GroupesActivity extends AppCompatActivity {

    GroupeDAO gd;
    LinearLayout main_layout;

    ArrayList<Groupe> tabGroupe = new ArrayList<Groupe>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groupes);


        gd = new GroupeDAO(this);
        tabGroupe = gd.listeGroupe();
        final int N = tabGroupe.size();
        final LinearLayout[] myLinear = new LinearLayout[N];
        main_layout = (LinearLayout) findViewById(R.id.main_layout);

        for (int i = 0; i < N; i++) {
            //Creation du Linear Layout de la tâche
            final LinearLayout rowLinear = new LinearLayout(this);

            //Paramètre du Linear
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(30, 50, 30, 0);
            final String idToPass = Integer.toString(tabGroupe.get(i).getId());
            final int pos = i;
            Log.i("ID du groupe", String.valueOf(tabGroupe.get(i).getId()));
            rowLinear.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intentAff = new Intent(GroupesActivity.this, AffichageGroupeActivity.class);
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
            final TextView nomGroupe = new TextView(this);
            rowLinear.addView(nomGroupe);
            nomGroupe.setText(tabGroupe.get(i).getNom());
            nomGroupe.setTextSize(18);
            nomGroupe.setId(i);
            nomGroupe.setTypeface(null, nomGroupe.getTypeface().BOLD);
            LinearLayout.LayoutParams nomParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 70);
            nomParam.setMargins(20, 20, 20, 10);
            nomGroupe.setLayoutParams(nomParam);



        }


    }


    public void taches(View view)
    {
        Intent intent = new Intent(GroupesActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void mes_taches(View view)
    {
        Intent intent = new Intent(GroupesActivity.this, MesTachesActivity.class);
        startActivity(intent);
    }

    public void button_ajout(View view)
    {
        Intent intent = new Intent(GroupesActivity.this, AjoutGrpActivity.class);
        startActivity(intent);
    }

}
