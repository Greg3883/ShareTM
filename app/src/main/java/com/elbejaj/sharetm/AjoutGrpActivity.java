package com.elbejaj.sharetm;

/**
 * Created by Baalamor on 08/02/2017.
 */


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

import static android.R.attr.format;



public class AjoutGrpActivity extends AppCompatActivity implements View.OnClickListener {

    Button ajout_grp;
    EditText ajout_nom;
    TextView dateC;
    GroupeDAO gd;
    Groupe grp;
    Calendar date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajout_grp_activity);

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        ajout_grp = (Button) findViewById(R.id.ajout_button);
        ajout_grp.setOnClickListener(this);
        ajout_nom = (EditText) findViewById(R.id.ajout_input_nom);
        dateC = (TextView) findViewById(R.id.dateC);

        String datestr;
        Calendar date = Calendar.getInstance();
        datestr = df.format(date.getTime());

        dateC.setText(datestr);

        gd = new GroupeDAO(this);

        grp = new Groupe();
        grp.setDateC(date);

    }


    @Override
    public void onClick(View v) {

        gd.open();

        String nom = ajout_nom.getText().toString();
        grp.setNom(nom);
        gd.ajouterGroupe(grp);
        gd.close();
        Intent intent = new Intent(AjoutGrpActivity.this, GroupesActivity.class);
        startActivity(intent);
    }


}
