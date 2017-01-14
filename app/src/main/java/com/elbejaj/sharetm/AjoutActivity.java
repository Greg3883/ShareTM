package com.elbejaj.sharetm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DateFormat;
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
    EditText ajout_echeance;
    TacheDAO td;
    Tache tache;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout);

        ajout_tache = (Button) findViewById(R.id.ajout_button);
        ajout_tache.setOnClickListener(this);
        ajout_nom = (EditText) findViewById(R.id.ajout_input_nom);
        ajout_contenu = (EditText) findViewById(R.id.ajout_input_contenu);
        ajout_priorite = (EditText) findViewById(R.id.ajout_input_priorite);
        ajout_echeance = (EditText) findViewById(R.id.ajout_input_echeance);
        td = new TacheDAO(this);
    }


    @Override
    public void onClick(View v) {

        //Format des dates
        DateFormat format = new SimpleDateFormat("Y-m-d");

        td.open();
        tache = new Tache();
        String nom = ajout_nom.getText().toString();
        String contenu = ajout_contenu.getText().toString();
        int priorite = Integer.parseInt(ajout_priorite.getText().toString());
        String strEcheance = ajout_echeance.getText().toString();

        tache.setId();
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
}
