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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class HOD extends AppCompatActivity {
    EditText hodname,hodid,hodemail,hodmobno,hodpass;
    Button registerh;
    Spinner hdept;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hod);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(actionBar!=null){
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_4));
        }

        hodname=(EditText)findViewById(R.id.hodname);
        hodid=(EditText)findViewById(R.id.hodid);
        hdept = (Spinner)findViewById(R.id.spinhod);
        hodemail=(EditText)findViewById(R.id.hodemail);
        hodmobno=(EditText)findViewById(R.id.hodmobno);
        hodpass=(EditText)findViewById(R.id.hodpass);
        registerh=(Button) findViewById(R.id.reg);
        databaseReference = FirebaseDatabase.getInstance().getReference("huser");


        firebaseAuth=FirebaseAuth.getInstance();
        registerh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String Name = hodname.getText().toString();
                final String Hodid = hodid.getText().toString() + "@gpw.com";
                final String Dept = hdept.getSelectedItem().toString();
                final String Email = hodemail.getText().toString();
                final String Mobileno = hodmobno.getText().toString();
                final String Password = hodpass.getText().toString();
                final String Role = ("HOD");

                if (TextUtils.isEmpty(Email)) {
                    Toast.makeText(HOD.this, "Please Enter  Email", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(Password)) {
                    Toast.makeText(HOD.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(Name)) {
                    Toast.makeText(HOD.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(Mobileno)) {
                    Toast.makeText(HOD.this, "Please Enter Valid Mobilenumber", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(Hodid)) {
                    Toast.makeText(HOD.this, "Please Enter  staffid", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(Dept)) {
                    Toast.makeText(HOD.this, "Please Enter Department", Toast.LENGTH_SHORT).show();
                }


                firebaseAuth.createUserWithEmailAndPassword(Hodid, Password)
                        .addOnCompleteListener(HOD.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {


                                    hodrg information = new hodrg(Name, Hodid, Dept, Email, Mobileno, Password, Role);

                                    FirebaseDatabase.getInstance().getReference("huser")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            Toast.makeText(HOD.this, "Registration Complete", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                        }
                                    });

                                } else {
                                    Toast.makeText(HOD.this, "No success", Toast.LENGTH_SHORT).show();
                                }

                            }

                        });


            }

        });




    }

}
