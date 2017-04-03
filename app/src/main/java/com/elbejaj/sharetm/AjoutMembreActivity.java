package com.elbejaj.sharetm;

/**
 * Created by Valentin on 03/04/2017.
 */

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AjoutMembreActivity extends AppCompatActivity {

    MembreGroupeDAO mgd;
    UtilisateurDAO ud;
    ApiInterface apiInterface = STMAPI.getClient().create(ApiInterface.class);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_membre);

        Button valider = (Button) findViewById(R.id.ajout_button);
        Bundle extras = getIntent().getExtras();
        final String idGrp = extras.getString("idtu");
        Log.i("test","VALEUR DU IDGRP DANS AJOUTMBR : "+idGrp);

        //Instanciation du membre groupe DAO
        this.mgd = new MembreGroupeDAO(this,true);
        this.ud = new UtilisateurDAO(this,true);

       valider.setOnClickListener(new View.OnClickListener() {

                                       @Override
                                       public void onClick(View v) {
                                           ajoutMembre(idGrp);
                                       }
                                   }
        );


    }

    //test la validité du champ "Adresse e-mail" (vide + syntaxe)
    public Boolean isEmailValide() {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        EditText mail = (EditText) findViewById(R.id.input_mail);
        String mailstr = mail.getText().toString();
        if (mailstr.length() == 0) {
            Toast.makeText(AjoutMembreActivity.this, "Adresse e-mail non remplie", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!mailstr.matches(emailPattern)) {
            Toast.makeText(AjoutMembreActivity.this, "Adresse e-mail non valide", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    public void ajoutMembre(String idGrp) {
        if (isEmailValide()) {
            EditText mail = (EditText) findViewById(R.id.input_mail);
            String mailstr = mail.getText().toString();
            String idU = this.ud.userByEmail(mailstr);
            AsyncTask ajout = new MembreGroupeTask(this,this.mgd).execute(idU,idGrp,false);
        }
    }

}
