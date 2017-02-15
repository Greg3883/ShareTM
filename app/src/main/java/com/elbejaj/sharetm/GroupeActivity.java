package com.elbejaj.sharetm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Bejaj on 15/01/2017.
 */

public class GroupeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupe);
    }

    public void intent_tache(View view)
    {
        Intent intent = new Intent(GroupeActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void intent_tachegroup(View view)
    {
        Intent intent = new Intent(GroupeActivity.this, TacheGroupActivity.class);
        startActivity(intent);
    }
}
