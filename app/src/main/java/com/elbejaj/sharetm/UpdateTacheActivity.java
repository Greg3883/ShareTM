package com.elbejaj.sharetm;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Bejaj on 03/12/2016.
 */

public class UpdateTacheActivity extends AppCompatActivity implements View.OnClickListener {

    Button ajout_tache;
    EditText ajout_nom;
    EditText ajout_contenu;
    Spinner ajout_priorite;
    Spinner ajout_etat;
    TextView ajout_echeance;
    TacheDAO td;
    Tache tache;
    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;


    //Pour les appels aux services de l'API
    ApiInterface apiInterface = STMAPI.getClient().create(ApiInterface.class);

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

    private void aprop() {

        Intent intent = new Intent(UpdateTacheActivity.this, AproposActivity.class);
        startActivity(intent);
    }

    private void help() {

        Intent intent = new Intent(UpdateTacheActivity.this, HelpActivity.class);
        startActivity(intent);
    }

    private void paramBtn() {

        Intent intent = new Intent(UpdateTacheActivity.this, ParamActivity.class);
        startActivity(intent);
    }

    private void logoutBtn(){
        Intent intent = new Intent(UpdateTacheActivity.this, LoginActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_tache);

        //Initialisation des champs
        ajout_tache = (Button) findViewById(R.id.ajout_button);
        ajout_tache.setOnClickListener(this);
        ajout_nom = (EditText) findViewById(R.id.ajout_input_nom);
        ajout_contenu = (EditText) findViewById(R.id.ajout_input_contenu);
        ajout_priorite = (Spinner) findViewById(R.id.ajout_priorite);
        ajout_etat = (Spinner) findViewById(R.id.ajout_etat);
        ajout_echeance = (TextView) findViewById(R.id.ajout_input_echeance);

        //Instanciation du TacheDAO
        td = new TacheDAO(this, true);
        td.open();

        //Récupération de l'ID de la tâche en modification
        Bundle extras = getIntent().getExtras();
        String idTache = extras.getString("idtu");

        //Recherche de la tâche en ligne
        this.tache = td.trouverTache(idTache);
        td.close();


        //Instanciation des valeurs des champs en fonction de celles de la tâche
        ajout_nom.setText(this.tache.getIntituleT());
        int priob = this.tache.getPrioriteT();
        List spinnerPrio = new ArrayList();
        if (priob == 1) {
            spinnerPrio.add("Urgent");
            spinnerPrio.add("A traiter rapidement");
            spinnerPrio.add("Pas d'urgence");
        } else if (priob == 2) {
            spinnerPrio.add("A traiter rapidement");
            spinnerPrio.add("Urgent");
            spinnerPrio.add("Pas d'urgence");
        } else {
            spinnerPrio.add("Pas d'urgence");
            spinnerPrio.add("A traiter rapidement");
            spinnerPrio.add("Urgent");
        }

        ArrayAdapter adapter = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                spinnerPrio
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ajout_priorite.setAdapter(adapter);


        int etab = this.tache.getEtatT();
        List spinnerEtat = new ArrayList();
        if (etab == 1) {
            spinnerEtat.add("En cours");
            spinnerEtat.add("En attente");
            spinnerEtat.add("Validee");
        } else if (etab == 2) {
            spinnerEtat.add("En attente");
            spinnerEtat.add("En cours");
            spinnerEtat.add("Validee");
        } else {
            spinnerEtat.add("Validee");
            spinnerEtat.add("En attente");
            spinnerEtat.add("En cours");

        }

        ArrayAdapter adapterb = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                spinnerEtat
        );

        adapterb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ajout_etat.setAdapter(adapterb);
        ajout_contenu.setText(this.tache.getDescriptionT());
        Log.i("test", "updateTacheActivity - DescriptionT: " + this.tache.getDescriptionT());
        String prio = String.valueOf(this.tache.getPrioriteT());


        Calendar cal = Calendar.getInstance();
        try {
            Log.i("test", "getEcheance : " + this.tache.getEcheanceT());
            cal.setTime(this.tache.getEcheanceT());
        } catch (Exception e) {
            e.printStackTrace();
        }

        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        Boolean ajoutZeroMonth = false;
        Boolean ajoutZeroDay = false;
        if (month < 10) {
            ajoutZeroMonth = true;
        }
        if (day < 10) {
            ajoutZeroDay = true;
        }
        day = cal.get(Calendar.DAY_OF_MONTH);
        showDate(year, month + 1, day);


    }


    @Override
    public void onClick(View v) {
        Log.i("test","Je suis dans le onClick du valider");
        //Format des dates
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        td.open();

        //Récupération des nouvelles valeurs
        String nom = ajout_nom.getText().toString();
        String contenu = ajout_contenu.getText().toString();
        String value_prio = ajout_priorite.getSelectedItem().toString();


        //Récupération de la priorité
        int priorite;
        if (value_prio == "Urgent") {
            priorite = 1;
        } else if (value_prio == "A traiter rapidement") {
            priorite = 2;
        } else {
            priorite = 3;
        }

        //Récupération de l'état
        String value_etat = ajout_etat.getSelectedItem().toString();
        int etat;
        if (value_etat == "En cours") {
            etat = 1;
        } else if (value_etat == "En attente") {
            etat = 2;
        } else {
            etat = 3;
        }

        //Récupération de l'échéance
        String strEcheance = ajout_echeance.getText().toString();
        Log.i("test", "echence : " + strEcheance);

        //Modification de la tâche en local
        tache.setIntituleT(nom);
        tache.setDescriptionT(contenu);
        tache.setEtatT(etat);
        tache.setPrioriteT(priorite);

        //Envoi des nouveaux attributs à la requête de modification en local
        ContentValues cv = new ContentValues();
        cv.put("intituleT", tache.getIntituleT());
        cv.put("descriptionT", tache.getDescriptionT());
        cv.put("prioriteT", tache.getPrioriteT());
        cv.put("echeanceT", strEcheance);
        setContentValue(cv);
        Bundle extras = getIntent().getExtras();
        String idTache = extras.getString("idtu");
        //@TODO : Modifier la date de dernière modification
        //int idTacheI = Integer.parseInt(idTache);
        long lg = td.updateTache(idTache, cv);

        //Modification de la tâche en question sur le serveur
        Log.i("test", "updateTacheActivity : Je vais exécuter le updateTacheTask");
        AsyncTask updateTacheTask = new UpdateTacheTask(this, this.td).execute(idTache, nom, contenu, priorite, etat, strEcheance);


        Object aFonctionne = false;
        try {
            aFonctionne = updateTacheTask.get();
            Log.i("test", "updateTacheActivity : Résultat du task : " + aFonctionne.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        td.close();
        Intent intent = new Intent(UpdateTacheActivity.this, MainActivity.class);
        Log.i("INSIDE LIST", "1");
        startActivity(intent);
    }

    private void setContentValue(ContentValues cv) {
        cv.put("etatT", tache.getEtatT());
    }

    private void showDate(int year, int month, int day) {

        String strDay = String.valueOf(day);
        String strMonth = String.valueOf(month);

        if (day < 10) {
            strDay = "0" + strDay;
        }

        if (month < 10) {
            strMonth = "0" + strMonth;
        }


        ajout_echeance.setText(new StringBuilder().append(strDay).append("-")
                .append(strMonth).append("-").append(year));
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2 + 1, arg3);
                }
            };



}
