package com.elbejaj.sharetm;

/**
 * Created by Bejaj on 04/01/2017.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FormAddGroup extends AppCompatActivity implements View.OnClickListener {

    private TextView idG;
    private EditText nomG;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_account);

        nomG = (EditText) findViewById(R.id.nomG);
        String n = nomG.getText().toString();


        Button b = (Button) findViewById(R.id.addGrp);
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormAddGroup.this, MainActivity.class);
                startActivity(intent);
            }


        });

    }

    @Override
    public void onClick(View v) {

    }

}
