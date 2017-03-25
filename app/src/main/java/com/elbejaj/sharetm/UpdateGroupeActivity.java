package com.elbejaj.sharetm;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;


/**
 * Created by Baalamor on 08/02/2017.
 */

public class UpdateGroupeActivity extends AppCompatActivity implements View.OnClickListener {

    Button update;
    EditText ajout_nom;
    Groupe grp;
    GroupeDAO gd;
    String idGrp;
    int pos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_groupe);

        ajout_nom = (EditText) findViewById(R.id.ajout_input_nom);

        update = (Button) findViewById(R.id.update_button);
        update.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        idGrp = extras.getString("idtu");
        pos = extras.getInt("position");
        /*String anciennom = trouverNom();
        ajout_nom = (EditText) findViewById(R.idGroupe.ajout_input_nom);
        ajout_nom.setText(anciennom);*/

        //@TODO : A modifier, récupérer le isCOnnected du main
        gd = new GroupeDAO(this, false);
    }


    @Override
    public void onClick(View v) {

        //Format des dates
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        gd.open();
        grp = new Groupe();
        String nom = ajout_nom.getText().toString();
        grp.setNom(nom);

        ContentValues cv = new ContentValues();
        cv.put("nom", grp.getNom());

        Log.i("INSIDE LIST", idGrp);
        int idGrpI = Integer.parseInt(idGrp);
        Log.i("INSIDE LIST", "1");
        long lg = gd.updateGroupe(idGrpI,cv);

        gd.close();
        Intent intent = new Intent(UpdateGroupeActivity.this, GroupesActivity.class);
        Log.i("INSIDE LIST", "1");
        startActivity(intent);
    }

  /*  public String trouverNom() {
        return gd.listeGroupe().get(pos).getIntituleT();
    }*/

}
