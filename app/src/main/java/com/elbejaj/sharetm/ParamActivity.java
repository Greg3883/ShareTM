package com.elbejaj.sharetm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * Created by Boubaker-Sédike on 28/03/2017.
 */

public class ParamActivity extends AppCompatActivity {

    Button logout_button;
    private SharedPreferences mesPreferences;

    //Menu de l'application (haut-droite)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.param:
                paramBtn();
                return true;
            case R.id.aprop:
                aprop();
                return true;
            case R.id.helpe:
                help();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void aprop(){

        Intent intent = new Intent(ParamActivity.this, AproposActivity.class);
        startActivity(intent);
    }

    private void help(){

        Intent intent = new Intent(ParamActivity.this, HelpActivity.class);
        startActivity(intent);
    }

    private void paramBtn(){

        Intent intent = new Intent(ParamActivity.this, ParamActivity.class);
        startActivity(intent);
    }

    public void logoutBtn(View view)
    {
        Intent intent = new Intent(ParamActivity.this, Splash.class);
        //mesPreferences.edit().remove("ShareTaskManagerPreferences").commit();
        SharedPreferences settings = this.getSharedPreferences("ShareTaskManagerPreferences", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("userConnected", false);
        editor.putString("idRegisteredUser","");
        editor.commit();
        startActivity(intent);
    }

    protected void onCreate(Bundle savedInstanceState) {
        mesPreferences = getSharedPreferences("ShareTaskManagerPreferences",0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_param);
        logout_button = (Button) findViewById(R.id.button_logout);
    }
    public void intent_tache(View view)
    {
        Intent intent = new Intent(ParamActivity.this, MainActivity.class);
        startActivity(intent);
    }
}