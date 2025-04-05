package com.e.gpwbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;

public class MainActivity extends AppCompatActivity {

    Button btnlogin;
    String email, passwords;
    EditText emaile, password;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference1;
    ProgressDialog progressDialog;
    String role, userid;
    FirebaseAuth firebaseAuth;
    AwesomeValidation awesomeValidation1;
    String ROLE;
Spinner lgrole;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button rg = (Button) findViewById(R.id.register);
        Button lm = (Button) findViewById(R.id.learnmore);
        Button btlogin = (Button) findViewById(R.id.btnlogin);

        TextView cp = (TextView) findViewById(R.id.changepassword);
lgrole=(Spinner)findViewById(R.id.spin);
        firebaseAuth = FirebaseAuth.getInstance();


        lgrole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (lgrole.getSelectedItem().toString().equals("Student")) {

                    emaile.setHint("Enter Enrollment No");
                    emaile.setInputType(InputType.TYPE_CLASS_NUMBER);

                } else if (lgrole.getSelectedItem().toString().equals("Faculty")) {

                    emaile.setHint("Enter Staff id");
                    emaile.setInputType(InputType.TYPE_CLASS_NUMBER);
                }
                else if (lgrole.getSelectedItem().toString().equals("HOD")) {

                    emaile.setHint("Enter Hod id");
                    emaile.setInputType(InputType.TYPE_CLASS_NUMBER);
                }
                else if (lgrole.getSelectedItem().toString().equals("Principal")) {

                    emaile.setHint("Enter Principal id");
                    emaile.setInputType(InputType.TYPE_CLASS_NUMBER);
                }
                else if (lgrole.getSelectedItem().toString().equals("Admin")) {

                    emaile.setHint("Enter Admin id");
                    emaile.setInputType(InputType.TYPE_CLASS_TEXT);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        rg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ir = new Intent(MainActivity.this, Registration.class);
                startActivity(ir);
            }
        });
        lm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent il = new Intent(MainActivity.this, learnmorescreen.class);
                startActivity(il);
            }
        });
        cp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        emaile = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        btnlogin = (Button) findViewById(R.id.btnlogin);

        awesomeValidation1=new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation1.addValidation(this,R.id.email,
                RegexTemplate.NOT_EMPTY,R.string.invalid_name);
        awesomeValidation1.addValidation(this,R.id.password,
                RegexTemplate.NOT_EMPTY,R.string.invalid_passwordddd);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(awesomeValidation1.validate()){
                    progressDialog = new ProgressDialog(MainActivity.this);
                    progressDialog.show();
                    progressDialog.setContentView(R.layout.progress_dialog);
                    progressDialog.getWindow().setBackgroundDrawableResource(
                            android.R.color.transparent
                    );
                userLogin();}else {
                    Toast.makeText(MainActivity.this,"Please Enter Data",Toast.LENGTH_SHORT).show();
                }
            }
        });





    }

    public void openDialog() {
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");

    }

    private void userLogin() {
        final String email = emaile.getText().toString().trim() + "@gpw.com";//"simi123@gmail.com";
        final String password1 = password.getText().toString().trim();
        final String l= lgrole.getSelectedItem().toString().trim();
        //if the email and password are not empty
        //logging in the user
        firebaseAuth.signInWithEmailAndPassword(email, password1)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //if the task is successfull

                        if (TextUtils.isEmpty(email)) {
                            Toast.makeText(MainActivity.this, "Please Enter  valid user id", Toast.LENGTH_SHORT).show();
                        }
                        if (TextUtils.isEmpty(password1)) {
                            Toast.makeText(MainActivity.this, "Please Enter valid password", Toast.LENGTH_SHORT).show();
                        }
                        if(TextUtils.isEmpty(email)&&TextUtils.isEmpty(password1)){
                            Toast.makeText(MainActivity.this,"Please Enter Data",Toast.LENGTH_SHORT).show();
                        }

                        if (task.isSuccessful()) {
                            //start the profile activity
                            Toast.makeText(getApplicationContext(), l, Toast.LENGTH_LONG).show();
                            if(l.equals("Student"))
                            {
                                userid = firebaseAuth.getCurrentUser().getUid();
                                databaseReference = FirebaseDatabase.getInstance().getReference("Suser").child(userid);

                                databaseReference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        ROLE = dataSnapshot.child("role").getValue().toString();
                                        Toast.makeText(getApplicationContext(), "successfully login" + ROLE, Toast.LENGTH_LONG).show();
                                        if (ROLE.equals("Student")) {


                                            Toast.makeText(getApplicationContext(), "successfully login", Toast.LENGTH_LONG).show();
                                            Intent ihome = new Intent(getApplicationContext(), Home.class);
                                            ihome.putExtra("ROLE", ROLE);
                                            startActivity(ihome);
                                        }
                                        else
                                            {  Toast.makeText(MainActivity.this,"No Data Found",Toast.LENGTH_SHORT).show();}


                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });}
                            if(l.equals("Faculty")){

                      userid = firebaseAuth.getCurrentUser().getUid();
                            databaseReference1 = FirebaseDatabase.getInstance().getReference("fuser").child(userid);

                            databaseReference1.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    ROLE = dataSnapshot.child("frole").getValue().toString();
                                    if (ROLE.equals("Faculty")) {


                                        Toast.makeText(getApplicationContext(), "successfully login", Toast.LENGTH_LONG).show();
                                        Intent fhome = new Intent(getApplicationContext(), FacultyHome.class);
                                        fhome.putExtra("ROLE", ROLE);
                                        startActivity(fhome);
                                    }else{  Toast.makeText(MainActivity.this,"No Data Found",Toast.LENGTH_SHORT).show();}


                                }


                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            }); }
                            if(l.equals("Admin"))
                            {
                                userid = firebaseAuth.getCurrentUser().getUid();
                                databaseReference = FirebaseDatabase.getInstance().getReference("fuser").child(userid);

                                databaseReference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        ROLE = dataSnapshot.child("arole").getValue().toString();
                                        Toast.makeText(getApplicationContext(), "successfully login" + ROLE, Toast.LENGTH_LONG).show();
                                        if (ROLE.equals("Admin")) {


                                            Toast.makeText(getApplicationContext(), "successfully login", Toast.LENGTH_LONG).show();
                                            Intent phome = new Intent(getApplicationContext(), AdminHome.class);
                                            startActivity(phome);
                                        }else{  Toast.makeText(MainActivity.this,"No Data Found",Toast.LENGTH_SHORT).show();}


                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                            if(l.equals("HOD"))
                            {
                                userid = firebaseAuth.getCurrentUser().getUid();
                                databaseReference = FirebaseDatabase.getInstance().getReference("huser").child(userid);

                                databaseReference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        ROLE = dataSnapshot.child("hrole").getValue().toString();
                                        Toast.makeText(getApplicationContext(), "successfully login" + ROLE, Toast.LENGTH_LONG).show();
                                        if (ROLE.equals("HOD")) {


                                            Toast.makeText(getApplicationContext(), "successfully login", Toast.LENGTH_LONG).show();
                                            Intent hhome = new Intent(getApplicationContext(), HodHome.class);
                                            hhome.putExtra("ROLE", ROLE);
                                            startActivity(hhome);
                                        }else{  Toast.makeText(MainActivity.this,"No Data Found",Toast.LENGTH_SHORT).show();}

                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });}
                            if(l.equals("Principal"))
                            {
                                userid = firebaseAuth.getCurrentUser().getUid();
                                databaseReference = FirebaseDatabase.getInstance().getReference("puser").child(userid);

                                databaseReference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        ROLE = dataSnapshot.child("prole").getValue().toString();
                                        Toast.makeText(getApplicationContext(), "successfully login" + ROLE, Toast.LENGTH_LONG).show();
                                        if (ROLE.equals("Principal")) {


                                            Toast.makeText(getApplicationContext(), "successfully login", Toast.LENGTH_LONG).show();
                                            Intent prhome = new Intent(getApplicationContext(),PrincipalHome.class);
                                            prhome.putExtra("ROLE", ROLE);
                                            startActivity(prhome);
                                        }else{  Toast.makeText(MainActivity.this,"No Data Found",Toast.LENGTH_SHORT).show();}


                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });}
                        }
                    }
                });
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Do you want to Exit?")
                .setCancelable(false)
                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                     finishAffinity();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();

    }

}
