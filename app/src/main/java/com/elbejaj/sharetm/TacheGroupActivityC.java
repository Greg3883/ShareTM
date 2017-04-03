package com.elbejaj.sharetm;

//LAURIE

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

public class TacheGroupActivityC extends AppCompatActivity {


    LinearLayout main_layout;
    TacheDAO td;
    GroupeDAO gd;
    Spinner tri_spinner;
    Intent intent;
    String nomGroupe;
    Intent newI;
    TextView aff_name_tache;
    TextView aff_content_tache;

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

        Intent intent = new Intent(TacheGroupActivityC.this, AproposActivity.class);
        startActivity(intent);
    }

    private void help(){

        Intent intent = new Intent(TacheGroupActivityC.this, HelpActivity.class);
        startActivity(intent);
    }

    private void paramBtn(){

        Intent intent = new Intent(TacheGroupActivityC.this, ParamActivity.class);
        startActivity(intent);
    }

    private void logoutBtn(){
        Intent intent = new Intent(TacheGroupActivityC.this, LoginActivity.class);
        startActivity(intent);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tachegroupe_afficahgec);


        main_layout = (LinearLayout) findViewById(R.id.main_layout);
        tri_spinner = (Spinner) findViewById(R.id.tri_option);
        DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        List spinnerPrio = new ArrayList();
        spinnerPrio.add("Trier par état");
        spinnerPrio.add("Trier par date");
        spinnerPrio.add("Trier par priorité");
        spinnerPrio.add("Trier par groupe");

        ArrayAdapter adapter = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                spinnerPrio
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tri_spinner.setAdapter(adapter);

        //TODO : CHanger
        td = new TacheDAO(this,true);
        //@TODO : A modifier, récupérer le isCOnnected du main
        gd = new GroupeDAO(this,false);
        tabTache = td.listeTache();
        Collections.sort(tabTache, Tache.TacheEtatComparator);
        final int N = tabTache.size();
        final LinearLayout[] myLinear = new LinearLayout[N];


        ApiInterface apiInterface = STMAPI.getClient().create(ApiInterface.class);
        ArrayList<Groupe> tabGroupe = new ArrayList<Groupe>();
        gd = new GroupeDAO(this, false);
        tabGroupe = gd.listeGroupe();
        final int NI = tabGroupe.size();
        ArrayList<String> listGroupesId = new ArrayList<String>();
        ArrayList<String> listGroupesNoms = new ArrayList<String>();
        for(int i =0; i<NI; i++){
            Groupe currentGroupe = tabGroupe.get(i);
            if(currentGroupe !=null){
                Log.i("Id groupe",currentGroupe.getIdGroupe());
                Log.i("Nom groupe",currentGroupe.getNom());
                listGroupesId.add(currentGroupe.getIdGroupe());
                listGroupesNoms.add(currentGroupe.getNom());
            }
        }

        for (int i=0;i<listGroupesNoms.size()-1;i++){
            Log.i("Je cherhce",listGroupesNoms.get(i));
        }




        for (int i = 0; i < N; i++) {

            //Creation du Linear Layout de la tâche
            final LinearLayout principLinear = new LinearLayout(this);

            //Creation du Linear Layout des infos
            final LinearLayout rowLinear = new LinearLayout(this);

            //Creation du Linear Layout des états
            final LinearLayout rowEtat = new LinearLayout(this);

            //Creation du Linear Layout des états
            final LinearLayout rowPrio = new LinearLayout(this);

            //Paramètre du Linear
            LinearLayout.LayoutParams lpb = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 300);
            //lpb.setMargins(30, 50, 0, 0);
            final String idToPassb = tabTache.get(i).getIdTache();
            Log.i("ID de la tache", String.valueOf(tabTache.get(i).getIdTache()));
            rowEtat.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intentAff = new Intent(TacheGroupActivityC.this, AffichageTacheActivity.class);
                    String strName = null;
                    intentAff.putExtra("idpass", idToPassb);
                    startActivity(intentAff);
                }
            });
            rowEtat.setLayoutParams(lpb);

            //Paramètre du Linear
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(400, 300);
            //lp.setMargins(0, 50, 30, 0);
            rowLinear.setOrientation(LinearLayout.VERTICAL);
            final String idToPass = tabTache.get(i).getIdTache();
            Log.i("ID de la tache", String.valueOf(tabTache.get(i).getIdTache()));
            rowLinear.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intentAff = new Intent(TacheGroupActivityC.this, AffichageTacheActivity.class);
                    String strName = null;
                    intentAff.putExtra("idpass", idToPass);
                    startActivity(intentAff);
                }
            });

            rowLinear.setLayoutParams(lp);

            //Paramètre du Linear lpdc
            LinearLayout.LayoutParams lpcd = new LinearLayout.LayoutParams(50, 300);
            //lp.setMargins(0, 50, 30, 0);
            rowPrio.setOrientation(LinearLayout.VERTICAL);
            Log.i("ID de la tache", String.valueOf(tabTache.get(i).getIdTache()));
            if (tabTache.get(i).getPrioriteT() == 1) {
                rowPrio.setBackgroundColor(getResources().getColor(R.color.prio_red));
            }else if (tabTache.get(i).getPrioriteT() == 2) {
                rowPrio.setBackgroundColor(getResources().getColor(R.color.prio_orange));
            } else if (tabTache.get(i).getPrioriteT() == 3) {
                rowPrio.setBackgroundColor(getResources().getColor(R.color.prio_green));
            }
            rowPrio.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intentAff = new Intent(TacheGroupActivityC.this, AffichageTacheActivity.class);
                    String strName = null;
                    intentAff.putExtra("idpass", idToPass);
                    startActivity(intentAff);
                }
            });
            rowPrio.setLayoutParams(lpcd);




            Log.i("test","MainActivity : estRetard - "+tabTache.get(i).afficherTache() );


            tabTache.get(i).estRetard();

            //On stock les taches

            myLinear[i] = rowLinear;

            //Création du nom de la tache
            final TextView nomTache = new TextView(this);
            rowLinear.addView(nomTache);
            nomTache.setText(tabTache.get(i).getIntituleT().toUpperCase());
            nomTache.setTextSize(18);
            nomTache.setId(R.id.nomTache);
            nomTache.setTextSize(20);
            nomTache.setTextColor(getResources().getColor(R.color.blacky));
            nomTache.setTypeface(null, nomTache.getTypeface().BOLD);
            LinearLayout.LayoutParams nomParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            nomParam.setMargins(20, 65, 0, 0);
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
            SimpleDateFormat preDateb = new SimpleDateFormat("dd-MM-yyyy");
            String newDate = preDateb.format(preDate);
            dateTache.setText(newDate);
            dateTache.setTypeface(null, dateTache.getTypeface().ITALIC);
            LinearLayout.LayoutParams dateParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            dateParam.setMargins(20, 0, 0, 0);
            dateTache.setLayoutParams(dateParam);


            Log.i("Size tabTache", String.valueOf(tabTache.size()));
            Log.i("Size listGroupId", String.valueOf(listGroupesId.size()));
            int g = 0;
            Boolean stop = false;
            while(g<= listGroupesId.size()-1 && stop == false){
                Log.i("Taille G", String.valueOf(g));
                Log.i("Taille I", String.valueOf(i));
                Log.i("Tabtache ou est",tabTache.get(i).getRefGroupe());
                Log.i("TabTache intu", tabTache.get(i).getIntituleT());
                Log.i("listGroupID",listGroupesId.get(g));
                if (listGroupesId.get(g).equals(tabTache.get(i).getIdTache())){
                    nomGroupe = listGroupesNoms.get(g);
                    stop = true;
                    Log.i("Missing","You");
                }
                g++;
            }
            Log.i("Nom groupe", "Nom : "+nomGroupe);
            //Création du groupe

            final TextView groupeTache = new TextView(this);
            groupeTache.setId(R.id.groupeTache);
            rowLinear.addView(groupeTache);
            groupeTache.setTextSize(16);
            String preGroupe = nomGroupe;
            groupeTache.setText(preGroupe);
            groupeTache.setTypeface(null, dateTache.getTypeface().ITALIC);
            LinearLayout.LayoutParams GroupeParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            //groupeTache.setMargins(20, 0, 0, 0);
            groupeTache.setLayoutParams(GroupeParam);


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
            principLinear.addView(rowPrio);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            layoutParams.setMargins(30, 20, 30, 0);

            principLinear.setLayoutParams(layoutParams);
            GradientDrawable gdu = new GradientDrawable();
            gdu.setColor(getResources().getColor(R.color.en_attente));
            gdu.setCornerRadius(10);
            if (tabTache.get(i).getPrioriteT() == 1) {
                gdu.setStroke(5, getResources().getColor(R.color.prio_red));
            }else if (tabTache.get(i).getPrioriteT() == 2) {
                gdu.setStroke(5, getResources().getColor(R.color.prio_orange));
            } else if (tabTache.get(i).getPrioriteT() == 3) {
                gdu.setStroke(5, getResources().getColor(R.color.prio_green));
            }

            principLinear.setBackground(gdu);


            //Ajout des layout tâche
            main_layout.addView(principLinear);


        }

        tri_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onItemSelected(AdapterView<?> arg0, View view,
                                       int position, long row_id) {
                Boolean startAct = false;

                switch(position){
                    case 0:
                        Log.i("8===D~~","(Tintin au tibet)");
                        break;
                    case 1:
                        newI = new Intent(TacheGroupActivityC.this, TacheGroupActivity.class);
                        startAct = true;
                        break;
                    case 2:
                        newI = new Intent(TacheGroupActivityC.this, TacheGroupActivityB.class);
                        startAct = true;
                        break;

                    case 3:
                        newI = new Intent(TacheGroupActivityC.this, TacheGroupActivityD.class);
                        startAct = true;
                        break;


                }
                if (startAct == true) {
                    startActivity(newI);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }

        });


    }

    public void button_ajout(View view)
    {
        Intent intent = new Intent(TacheGroupActivityC.this, AjoutActivity.class);
        startActivity(intent);
    }

    public void intent_groupe(View view)
    {
        Intent intent = new Intent(TacheGroupActivityC.this, GroupesActivity.class);
        startActivity(intent);
    }

    public void intent_tache(View view)
    {
        Intent intent = new Intent(TacheGroupActivityC.this, MainActivity.class);
        startActivity(intent);
    }




}
