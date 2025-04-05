package com.e.gpwbox;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;

public class Registration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(actionBar!=null){
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_4));
        }
        Button sr= (Button)findViewById(R.id.student_reg);
        Button fr=(Button)findViewById(R.id.faculty_reg);
        Button bk=(Button)findViewById(R.id.btn_back);
        sr.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent sri = new Intent(Registration.this,Student.class);
                startActivity(sri);
            }
        });
        fr.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent fri = new Intent(Registration.this,Faculty.class);
                startActivity(fri);
            }
        });
        bk.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent bki = new Intent(Registration.this,MainActivity.class);
                startActivity(bki);
            }
        });

    }

}
