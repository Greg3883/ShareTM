package com.elbejaj.sharetm;

/**
 * Created by Baalamor on 08/02/2017.
 */


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.AsyncTask;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;


public class AjoutGrpActivity extends AppCompatActivity implements View.OnClickListener {

    Button ajout_grp;
    EditText ajout_nom;
    TextView dateC;
    GroupeDAO gd;
    Groupe grp;
    Date date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajout_grp_activity);

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        ajout_grp = (Button) findViewById(R.id.ajout_button);
        ajout_grp.setOnClickListener(this);
        ajout_nom = (EditText) findViewById(R.id.ajout_input_nom);
        dateC = (TextView) findViewById(R.id.dateC);

        String datestr;
        Date date = new Date();
        datestr = df.format(date.getTime());

        dateC.setText(datestr);

        //@TODO : A modifier, récupérer le isCOnnected du main
        gd = new GroupeDAO(this, false);

        grp = new Groupe();
        grp.setDateCreationGroupe(date);

    }


    @Override
    public void onClick(View v) {

        String nom = ajout_nom.getText().toString();

        if (nom.length() == 0) {
            Toast.makeText(this, "Veuillez renseigner le nom", Toast.LENGTH_LONG).show();
        } else {

            Log.i("test","je vais ouvrir le groupeDAO");
            gd.open();
            Log.i("test","j'ai ouvert le groupeDAO");
            grp.setNom(nom);
            gd.ajouterGroupe(grp);
            Log.i("test","gpID : "+grp.getIdGroupe());
            Log.i("test","je vais fermer le groupeDAO");
            gd.close();
            Log.i("test","j'ai fermé le groupeDAO");


            createGroupe(nom);

            Intent intent = new Intent(AjoutGrpActivity.this, GroupesActivity.class);
            startActivity(intent);
        }
    }

    public void createGroupe(String nom) {

        //Exécution de l'inscription
        Log.i("test","groupeActivity : Je vais exécuter le groupeTask");
        AsyncTask groupeTask = new GroupeTask(this,this.gd).execute(nom);
        Object aFonctionne = false;
        try {
            aFonctionne = groupeTask.get();
            Log.i("test","groupeActivity : Résultat du task : "+aFonctionne.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

}
