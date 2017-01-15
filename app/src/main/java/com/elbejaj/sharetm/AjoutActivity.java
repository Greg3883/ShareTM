package com.elbejaj.sharetm;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.DateFormat;
import java.util.Calendar;
import android.app.DatePickerDialog;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Bejaj on 03/12/2016.
 */

public class AjoutActivity extends AppCompatActivity implements View.OnClickListener{

    Button ajout_tache;
    EditText ajout_nom;
    EditText ajout_contenu;
    EditText ajout_priorite;
    TextView ajout_echeance;
    TextView ajout_etat;
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
        ajout_priorite = (EditText) findViewById(R.id.ajout_input_priorite);
        ajout_etat = (EditText) findViewById(R.id.ajout_input_etat);

        ajout_echeance = (TextView) findViewById(R.id.ajout_input_echeance);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);

        td = new TacheDAO(this);
    }


    @Override
    public void onClick(View v) {

        //Format des dates
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        td.open();
        tache = new Tache();
        String nom = ajout_nom.getText().toString();
        String contenu = ajout_contenu.getText().toString();
        int etat = Integer.parseInt(ajout_etat.getText().toString());
        int priorite = Integer.parseInt(ajout_priorite.getText().toString());
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
