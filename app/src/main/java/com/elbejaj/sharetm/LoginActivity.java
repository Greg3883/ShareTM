package com.elbejaj.sharetm;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
import butterknife.ButterKnife;

/*
 * Activité d'inscription/connexion de l'application
 */

public class LoginActivity extends AppCompatActivity {

    TextView login_incorrect;
    ApiInterface apiInterface = STMAPI.getClient().create(ApiInterface.class);

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @Bind(R.id.input_email) EditText _emailText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.btn_login) Button _loginButton;
    @Bind(R.id.link_signup) TextView _signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        DBManager db = new DBManager(this,"base", null, 13);
        SQLiteDatabase sqlite = db.getWritableDatabase();
        db.onUpgrade(sqlite,13,13);

        //Récupération de la variable isConnected
        Bundle extras = getIntent().getExtras();
        final Boolean isConnected;
        if(extras==null) {
            isConnected = false;
        } else {
            isConnected=extras.getBoolean("isConnected");
        }

        final UtilisateurDAO ud = new UtilisateurDAO(this,isConnected);


        //Si on clique sur le bouton Login, on peut se logger et on lance la méthode login()
        _loginButton.setOnClickListener(new View.OnClickListener() {

             @Override
             public void onClick(View v) {
                    login(ud, isConnected);
                }
             }
        );

        //Si on clique sur le bouton SignUp, on va sur la page d'inscription
        /*
        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            // Start the Signup activity
            Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
            startActivityForResult(intent, REQUEST_SIGNUP);
            finish();
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
        */
    }

    /**
     * Fonction pour se connecter à l'application
     */
    public void login(UtilisateurDAO ud, Boolean isConnected) {

        //@TODO : Remettre la vérification des champs du formulaire
        /*
        if (!validate()) {
            onLoginFailed();
            return;
        }*/


        //@TODO : Désactivation du bouton de Login
        //_loginButton.setEnabled(false);

        //Dialogue de chargement (cercle qui tourne)
        /*
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();
        */

        //Récupération de l'email et du mot de passe
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        //Exécution de la connexion
        Log.i("test","loginActivity : Je vais exécuter le loginTask");
        AsyncTask loginTask = new LoginTask(this,ud).execute(email,password);
        Object aFonctionne = false;
        try {
            aFonctionne = loginTask.get();
            Log.i("test","loginActivity : Résultat du task : "+aFonctionne.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Boolean loginAFonctionne = (Boolean) aFonctionne;

        //Avant de démarrer le MainActivity, on vérifie si le login a fonctionné
        if(loginAFonctionne==true) {
            //On lance l'activité MainActivity pour avoir accès à l'application
            Intent i = new Intent (".MAINACTIVITY");
            i.putExtra("isConnected",isConnected);
            startActivity(i);
        } else {
            Toast.makeText(this,"Problème lors de la connexion. Veuillez réessayer.",Toast.LENGTH_LONG).show();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    /*
     * Fonction appelée lorsque la connexion a fonctionné
     */
    public void onLoginSuccess() {
        Intent i = new Intent(".MAINACTIVITY");
        startActivity(i);

    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }


    public void signUpLink(View view)
    {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    /**
     * @TODO : Vérifier la vérification d'un email ("a" ne fonctionne pas mais "greg" fonctionne)
     * @return
     */
    public boolean validate() {
        return true;
        /*UtilisateurDAO ud;
        boolean valid = true;
        boolean valid_uutilisateur;
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        ud = new UtilisateurDAO(this);
        ud.open();
        valid_uutilisateur = ud.trouverUtilisateur(email);
        ud.close();

        if (email.isEmpty() || !valid_uutilisateur) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 1 || password.length() > 50) {
            _passwordText.setError("between 1 and 50 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
*/
    }
}
