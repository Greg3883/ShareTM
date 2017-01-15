package com.elbejaj.sharetm;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

public class UpdateTacheActivity extends AppCompatActivity implements View.OnClickListener{

    Button ajout_tache;
    EditText ajout_nom;
    EditText ajout_contenu;
    EditText ajout_priorite;
    EditText ajout_etat;
    TextView ajout_echeance;
    TacheDAO td;
    Tache tache;
    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_tache);

        ajout_tache = (Button) findViewById(R.id.ajout_button);
        ajout_tache.setOnClickListener(this);
        ajout_nom = (EditText) findViewById(R.id.ajout_input_nom);
        ajout_contenu = (EditText) findViewById(R.id.ajout_input_contenu);
        ajout_priorite = (EditText) findViewById(R.id.ajout_input_priorite);
        ajout_etat = (EditText) findViewById(R.id.ajout_input_etat);
        ajout_echeance = (TextView) findViewById(R.id.ajout_input_echeance);



        String idTache;
        Bundle extras = getIntent().getExtras();
        idTache = extras.getString("idtu");
        int idTacheI = Integer.parseInt(idTache);
        td = new TacheDAO(this);
        td.open();
        Tache tachet = td.trouverTache(idTacheI);
        td.close();
        ajout_nom.setText(tachet.getNom());
        String eta = String.valueOf(tachet.getEtat());
        ajout_priorite.setText(eta);
        ajout_contenu.setText(tachet.getContenu());
        String prio = String.valueOf(tachet.getPriorite());
        ajout_priorite.setText(prio);


        Calendar cal = Calendar.getInstance();
        cal.setTime(tachet.getEcheance());
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);

    }


    @Override
    public void onClick(View v) {

        //Format des dates
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        td.open();
        tache = new Tache();
        String nom = ajout_nom.getText().toString();
        String contenu = ajout_contenu.getText().toString();
        int priorite = Integer.parseInt(ajout_priorite.getText().toString());
        int etat = Integer.parseInt(ajout_etat.getText().toString());
        String strEcheance  = ajout_echeance.getText().toString();
        tache.setNom(nom);
        tache.setContenu(contenu);
        tache.setEtat(etat);
        tache.setPriorite(priorite);
        ContentValues cv = new ContentValues();
        cv.put("nom", tache.getNom());
        cv.put("contenu", tache.getContenu());
        cv.put("priorite", tache.getPriorite());
        cv.put("echeance", strEcheance);
        cv.put("etat", tache.getEtat());
        String idTache;
        Bundle extras = getIntent().getExtras();
        idTache = extras.getString("idtu");
        Log.i("INSIDE LIST", idTache);
        int idTacheI = Integer.parseInt(idTache);
        Log.i("INSIDE LIST", "1");
        long lg = td.updateTache(idTacheI,cv);

        td.close();
        Intent intent = new Intent(UpdateTacheActivity.this, MainActivity.class);
        Log.i("INSIDE LIST", "1");
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
