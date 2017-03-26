package com.elbejaj.sharetm;

/**
 * Created by Valentin on 25/03/2017.
 */

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import butterknife.Bind;


public class RegisterActivity extends AppCompatActivity {


    /*useless*/
    UtilisateurDAO ud;
    ApiInterface apiInterface = STMAPI.getClient().create(ApiInterface.class);


    @Bind(R.id.input_name) EditText _nameText;
    @Bind(R.id.input_mail) EditText _mailText;
    @Bind(R.id.input_pwd) EditText _pwdText;
    @Bind(R.id.input_confirmpwd) EditText _confirmPwdText;
    @Bind(R.id.btn_valider) Button _validerButton;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button valider = (Button) findViewById(R.id.btn_valider);

        valider.setOnClickListener(new View.OnClickListener() {

                                            @Override
                                            public void onClick(View v) {
                                                register();
                                            }
                                        }
        );


    }

    //test la validité du champ "Nom" (vide ou non)
    public Boolean isEmptyName() {
        EditText name = (EditText) findViewById(R.id.input_name);
        String namestr = name.getText().toString();
        if (namestr.length()== 0) {
            Toast.makeText(RegisterActivity.this, "Le nom n'est pas indiqué", Toast.LENGTH_SHORT).show();
            return true;
        }
        else return false;
    }

    //test la validité du champ "Adresse e-mail" (vide + syntaxe)
    public Boolean isEmailValide() {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        EditText mail = (EditText) findViewById(R.id.input_mail);
        String mailstr = mail.getText().toString();
        if (mailstr.length() == 0) {
            Toast.makeText(RegisterActivity.this, "Adresse e-mail non remplie", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!mailstr.matches(emailPattern)) {
            Toast.makeText(RegisterActivity.this, "Adresse e-mail non valide", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    //test la validité des champs "mot de passe" (vide + différents)
    public Boolean isPasswordValide() {
        EditText pwd = (EditText) findViewById(R.id.input_pwd);
        EditText confpwd = (EditText) findViewById(R.id.input_confirmpwd);
        String pwdstr = pwd.getText().toString();
        String confpwdstr = confpwd.getText().toString();
        if (!(pwdstr.length() == 0)){
            if (!(confpwdstr.length() == 0 )) {
                if (!confpwdstr.equals(pwdstr)) {
                    Toast.makeText(RegisterActivity.this, "Mots de passe différents", Toast.LENGTH_SHORT).show();
                    return false;
                }
                else return true;
            }
            else {
                Toast.makeText(RegisterActivity.this, "Confirmation vide", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(RegisterActivity.this, "Mot de passe vide", Toast.LENGTH_SHORT).show();
        }
        return false;
    }


    //appelle tous les test sur les champs, puis la fonction d'inscription avec l'API
    public void register() {
        if (!isEmptyName()) {
            if (isEmailValide()) {
                if (isPasswordValide()) {
                    Toast.makeText(RegisterActivity.this, "Validé", Toast.LENGTH_SHORT).show();
                    registerAPI();
                }
            }
        }

    }

    //communique avec l'API pour l'inscription (ajout dans la BdD)
    public void registerAPI() {

        //Récupération du nom, de l'email et du mot de passe
        EditText nom = (EditText) findViewById(R.id.input_name);
        EditText mail = (EditText) findViewById(R.id.input_mail);
        EditText pwd = (EditText) findViewById(R.id.input_pwd);
        String nomstr = nom.getText().toString();
        String mailstr = mail.getText().toString();
        String pwdstr = pwd.getText().toString();

        //Exécution de l'inscription
        Log.i("test","registerActivity : Je vais exécuter le registerTask");
        AsyncTask registerTask = new RegisterTask(this,this.ud).execute(nomstr,mailstr,pwdstr);
        Object aFonctionne = false;
        try {
            aFonctionne = registerTask.get();
            Log.i("test","registerActivity : Résultat du task : "+aFonctionne.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Boolean registerAFonctionne = (Boolean) aFonctionne;

        //Avant de démarrer le LoginActivity, on vérifie si l'inscription a fonctionné
        if(registerAFonctionne==true) {
            Intent i = new Intent (".LOGINACTIVITY");
            startActivity(i);
        } else {
            Toast.makeText(this,"Problème lors de l'inscription. Veuillez réessayer.",Toast.LENGTH_LONG).show();
        }

    }


}
