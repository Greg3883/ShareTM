package com.elbejaj.sharetm;

/**
 * Created by Baalamor on 08/02/2017.
 */


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


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

        //@TODO : A modifier, récupérer le isCOnnected du main
        gd = new GroupeDAO(this, false);

        grp = new Groupe();
        grp.setDateCreationGroupe(date);

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
