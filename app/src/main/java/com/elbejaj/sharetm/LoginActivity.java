package com.elbejaj.sharetm;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.R.attr.tag;

public class LoginActivity extends AppCompatActivity {
    UtilisateurDAO ud;
    TextView login_incorrect;

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

        Utilisateur default_utilisateur = new Utilisateur();
        default_utilisateur.setEmail("greg");
        default_utilisateur.setNomU("greg");
        default_utilisateur.setMdpHash("azze");
        default_utilisateur.setIdUtilisateur(98);
        default_utilisateur.setApiKey("frere");
        java.text.DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date currentDate = new Date();
        default_utilisateur.setDateCreationU(currentDate);
        ud = new UtilisateurDAO(this);
        ud.open();
        Log.d("fzfez", "Message validé");
        ud.ajouterUtilisateur(default_utilisateur);
        ud.close();
        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        /**  _signupLink.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {
        // Start the Signup activity
        Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
        startActivityForResult(intent, REQUEST_SIGNUP);
        finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        }
        });**/
    }

    public void login() {

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        Boolean valid_uutilisateur = false;
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
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

    public void onLoginSuccess() {
        Intent i = new Intent(".MAINACTIVITY");
        startActivity(i);

    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        UtilisateurDAO ud;
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
    }
}
