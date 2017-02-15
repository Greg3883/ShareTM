package com.elbejaj.sharetm;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    TacheDAO td;
    Tache tache;
    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout);

        ajout_tache = (Button) findViewById(R.id.ajout_button);
        ajout_tache.setOnClickListener(this);
        ajout_nom = (EditText) findViewById(R.id.ajout_input_nom);
        ajout_contenu = (EditText) findViewById(R.id.ajout_input_contenu);
        ajout_priorite = (Spinner) findViewById(R.id.ajout_priorite);
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
        spinnerEtat.add("Validee");

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

        //@LAURIE
        //td = new TacheDAO(this);
        //Si on arrive dans cette activité, c'est qu'on est connecté
        TacheDAO td = new TacheDAO(this,true);
        //-----------------------------------

    }


    @Override
    public void onClick(View v) {

        //Format des dates
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        td.open();
        tache = new Tache();
        String nom = ajout_nom.getText().toString();
        String contenu = ajout_contenu.getText().toString();
        String value_etat = ajout_etat.getSelectedItem().toString();
        int etat;
        if (value_etat == "En cours"){
            etat = 1;
        }else if(value_etat == "En attente"){
            etat = 2;
        } else {
            etat = 3;
        }
        String value_prio = ajout_priorite.getSelectedItem().toString();
        int priorite;
        if (value_prio == "Urgent"){
            priorite = 1;
        }else if(value_prio == "A traiter rapidement"){
            priorite = 2;
        } else {
            priorite = 3;
        }
        String strEcheance  = ajout_echeance.getText().toString();

        tache.setEtat(etat);
        tache.setNom(nom);
        tache.setContenu(contenu);
        tache.setPriorite(priorite);
        Date echeance = null;
        try {
            echeance = format.parse(strEcheance);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        tache.setEcheance(echeance);
        long lg = td.ajouterTache(tache);
        td.close();
        Intent intent = new Intent(AjoutActivity.this, MainActivity.class);
        startActivity(intent);
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
