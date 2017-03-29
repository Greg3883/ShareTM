package com.elbejaj.sharetm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by Boubaker-Sédike on 28/03/2017.
 */

public class HelpActivity extends AppCompatActivity {

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
            case R.id.new_game:
                aprop();
                return true;
            case R.id.help:
                paramBtn();
                return true;
            case R.id.helpe:
                help();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void aprop(){

        Intent intent = new Intent(HelpActivity.this, AproposActivity.class);
        startActivity(intent);
    }

    private void help(){

        Intent intent = new Intent(HelpActivity.this, HelpActivity.class);
        startActivity(intent);
    }

    private void paramBtn(){

        Intent intent = new Intent(HelpActivity.this, ParamActivity.class);
        startActivity(intent);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }
    public void intent_tache(View view)
    {
        Intent intent = new Intent(HelpActivity.this, MainActivity.class);
        startActivity(intent);
    }



}