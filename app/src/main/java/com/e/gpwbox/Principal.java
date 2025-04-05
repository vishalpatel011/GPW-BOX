package com.e.gpwbox;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Principal extends AppCompatActivity {
    EditText prname,prid,premail,prmobno,prpass;
    Button registerp;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(actionBar!=null){
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_4));
        }

        prname=(EditText)findViewById(R.id.prname);
        prid=(EditText)findViewById(R.id.prid);
        premail=(EditText)findViewById(R.id.premail);
        prmobno=(EditText)findViewById(R.id.prmobno);
        prpass=(EditText)findViewById(R.id.prpass);
        registerp=(Button) findViewById(R.id.prreg);
        databaseReference = FirebaseDatabase.getInstance().getReference("puser");


        firebaseAuth=FirebaseAuth.getInstance();
        registerp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String Name = prname.getText().toString();
                final String Prid = prid.getText().toString() + "@gpw.com";
                final String Email = premail.getText().toString();
                final String Mobileno = prmobno.getText().toString();
                final String Password = prpass.getText().toString();
                final String Role = ("Principal");

                if (TextUtils.isEmpty(Email)) {
                    Toast.makeText(Principal.this, "Please Enter  Email", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(Password)) {
                    Toast.makeText(Principal.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(Name)) {
                    Toast.makeText(Principal.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(Mobileno)) {
                    Toast.makeText(Principal.this, "Please Enter Valid Mobilenumber", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(Prid)) {
                    Toast.makeText(Principal.this, "Please Enter  principalid", Toast.LENGTH_SHORT).show();
                }



                firebaseAuth.createUserWithEmailAndPassword(Prid, Password)
                        .addOnCompleteListener(Principal.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {


                                    principalrg information = new principalrg(Name, Prid, Email, Mobileno, Password, Role);

                                    FirebaseDatabase.getInstance().getReference("puser")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            Toast.makeText(Principal.this, "Registration Complete", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                        }
                                    });

                                } else {
                                    Toast.makeText(Principal.this, "No success", Toast.LENGTH_SHORT).show();
                                }

                            }

                        });


            }

        });




    }



}
