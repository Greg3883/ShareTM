package com.elbejaj.sharetm;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Bejaj on 03/12/2016.
 */

public class AjoutActivity extends AppCompatActivity implements View.OnClickListener{

    Button ajout_tache;
    EditText ajout_nom;
    EditText ajout_contenu;
    Spinner ajout_priorite;
    TextView ajout_echeance;
    Spinner ajout_etat;
    private SharedPreferences mesPreferences;
    TacheDAO td;
    AffectationTacheDAO taf;
    AffectationTache affectTache;
    private String idRegisteredUser;
    Tache tache;
    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    List listeGroupesNoms = new ArrayList();
    List listeGroupesID = new ArrayList();

    Spinner ajout_spinnerGroupes;

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

        Intent intent = new Intent(AjoutActivity.this, AproposActivity.class);
        startActivity(intent);
    }

    private void help(){

        Intent intent = new Intent(AjoutActivity.this, HelpActivity.class);
        startActivity(intent);
    }

    private void paramBtn(){

        Intent intent = new Intent(AjoutActivity.this, ParamActivity.class);
        startActivity(intent);
    }

    private void logoutBtn(){
        Intent intent = new Intent(AjoutActivity.this, LoginActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout);

        ajout_tache = (Button) findViewById(R.id.ajout_button);
        ajout_tache.setOnClickListener(this);
        ajout_nom = (EditText) findViewById(R.id.ajout_input_nom);
        ajout_contenu = (EditText) findViewById(R.id.ajout_input_contenu);
        ajout_priorite = (Spinner) findViewById(R.id.ajout_priorite);
        ajout_spinnerGroupes = (Spinner) findViewById(R.id.ajout_spinnerGroupes);

        //Remplissage des données pour les groupes
        Log.i("test","Ajout activity : Je vais remplir la liste des groupes");
        GroupeDAO gd = new GroupeDAO(this,true);
        ArrayList<Groupe> listeGroupes = gd.listeGroupe();

        for(int i=0;i<listeGroupes.size();i++) {
            Groupe currentGroupe = listeGroupes.get(i);
            if(currentGroupe!=null) {
                Log.i("test","ID Du groupe : "+currentGroupe.getIdGroupe());
                Log.i("test","nom du groupe : " + currentGroupe.getNom());
                this.listeGroupesID.add(currentGroupe.getIdGroupe());
                this.listeGroupesNoms.add(currentGroupe.getNom());
            } else {
                Log.i("test","le groupe est null");
            }
        }
        //Remplissage du spinner
        Log.i("test","ArrayAdapter");
        if(this.listeGroupesNoms!=null) {
            Log.i("test","AjoutActivity : La liste des groupes n'est PAS null");
            ArrayAdapter dataAdapterG = new ArrayAdapter(this,android.R.layout.simple_spinner_item,this.listeGroupesNoms);
            dataAdapterG.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            ajout_spinnerGroupes.setAdapter(dataAdapterG);

        } else {
            Log.i("test","AjoutActivity : La liste des groupes est null");
        }

        List spinnerPrio = new ArrayList();
        spinnerPrio.add("Urgent");
        spinnerPrio.add("A traiter rapidement");
        spinnerPrio.add("Pas d'urgence");

        ArrayAdapter adapter = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                spinnerPrio
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ajout_priorite.setAdapter(adapter);

        ajout_etat = (Spinner) findViewById(R.id.ajout_etat);
        List spinnerEtat = new ArrayList();
        spinnerEtat.add("En cours");
        spinnerEtat.add("En attente");
        spinnerEtat.add("Validée");

        ArrayAdapter adapterb = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                spinnerEtat
        );

        adapterb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ajout_etat.setAdapter(adapterb);
        ajout_echeance = (TextView) findViewById(R.id.ajout_input_echeance);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);

        //td = new TacheDAO(this);
        TacheDAO td = new TacheDAO(this, true);
    }


    @Override
    public void onClick(View v) {
        TacheDAO td = new TacheDAO(this, true);
        AffectationTacheDAO taf = new AffectationTacheDAO(this,true);
        ApiInterface apiInterface = STMAPI.getClient().create(ApiInterface.class);

        //Format des dates
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        DateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
        Date currentDate = new Date();

        //Récupération des valeurs
        td.open();
        tache = new Tache();

        String nom = ajout_nom.getText().toString();
        String contenu = ajout_contenu.getText().toString();
        String value_etat = ajout_etat.getSelectedItem().toString();

        if(contenu.isEmpty() | nom.isEmpty()) {
            Toast.makeText(this,"Veuillez remplir tous les champs",Toast.LENGTH_LONG).show();
        } else {
            int etat;
            if (value_etat == "En cours") {
                etat = 1;
            } else if (value_etat == "En attente") {
                etat = 2;
            } else {
                etat = 3;
            }
            String value_prio = ajout_priorite.getSelectedItem().toString();
            int priorite;
            if (value_prio == "Urgent") {
                priorite = 1;
            } else if (value_prio == "A traiter rapidement") {
                priorite = 2;
            } else {
                priorite = 3;
            }
            String strEcheance = ajout_echeance.getText().toString();
            try {
                Date strEcheance2 = format2.parse(strEcheance);
                strEcheance = format.format(strEcheance2);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //Récupération du groupe sélectionné et de son ID
            String idGroupeSelected = listeGroupesID.get(ajout_spinnerGroupes.getSelectedItemPosition()).toString();

            tache.setEtatT(etat);
            tache.setIntituleT(nom);
            tache.setDescriptionT(contenu);
            tache.setRefGroupe("1");
            tache.setPrioriteT(priorite);
            tache.setDateCreationT(currentDate);
            tache.setRefGroupe(idGroupeSelected);

            Date echeance = null;
            try {
                echeance = format.parse(strEcheance);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            tache.setEcheanceT(echeance);


            taf.open();
            affectTache = new AffectationTache();

            affectTache.setIdUtilisateur("1");
            affectTache.setEstAdminTache(1);
            affectTache.setIdTache(tache.getIdTache());

            //Récupération des préférences pour avoir l'ID utilisateur courant
            mesPreferences = getSharedPreferences("ShareTaskManagerPreferences", 0);
            this.idRegisteredUser = mesPreferences.getString("idRegisteredUser", "");

            //Création d'une tâche sur le serveur
            Log.i("test", "Je vais lancer l'ajoutTask");
            AsyncTask ajoutTask = new AjoutTask(this, this.td, this.taf, this.tache, this.affectTache, idRegisteredUser).execute();
            Object aFonctionne = false;
            try {
                aFonctionne = ajoutTask.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            taf.close();
            td.close();

            //Retour vers le MainActivity
            Intent intent = new Intent(AjoutActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void showDate(int year, int month, int day) {
        ajout_echeance.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
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
                    showDate(arg1, arg2+1, arg3);
                }
            };
}
