package com.e.gpwbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home extends AppCompatActivity {

    Button logout;
    TextView unicid,name,enrollment,role;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    String userid,username,userenno;
    CardView addmessage;
    CardView viewmessage;
    CardView fb;
    CardView Editpro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        firebaseAuth = FirebaseAuth.getInstance();
        userid =  firebaseAuth.getCurrentUser().getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference("Suser").child(userid);

        //unicid = (TextView)findViewById(R.id.unicid);
        name = (TextView)findViewById(R.id.name);
        enrollment = (TextView)findViewById(R.id.enrollment);
        logout = (Button) findViewById(R.id.logout);
        addmessage=(CardView)findViewById(R.id.addmsg);
        viewmessage=(CardView)findViewById(R.id.viewmsg);
        fb=(CardView)findViewById(R.id.feedback);
        Editpro=(CardView)findViewById(R.id.editprofile);


        role = (TextView)findViewById(R.id.role);
        Intent i = getIntent();
        String ROLE = i.getStringExtra("ROLE");
        role.setText(ROLE);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String email = dataSnapshot.child("name").getValue().toString();
                String passwards = dataSnapshot.child("enno").getValue().toString();
                name.setText("" + email);
                enrollment.setText("" + passwards.replace("@gpw.com",""));
               // unicid.setText("" + userid);
                Toast.makeText(getApplicationContext(), "" + email +"\n" + passwards.replace("@gpw.com",""), Toast.LENGTH_LONG).show();
                logout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            firebaseAuth.signOut();
                            Toast.makeText(Home.this, "Logout Succesful", Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            Toast.makeText(Home.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        Intent lo = new Intent(Home.this,MainActivity.class);
                        startActivity(lo);
                    }
                });
                addmessage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent am=new Intent(Home.this,AddMessage.class);
                        startActivity(am);

                    }
                });
                viewmessage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String srole=role.getText().toString();
                        Intent vm=new Intent(Home.this,ViewMessage.class);
                        vm.putExtra("srole",srole);
                        startActivity(vm);

                    }
                });
                fb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent feed=new Intent(Home.this,Feedback.class);
                        startActivity(feed);

                    }
                });
                Editpro.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent ep=new Intent(Home.this,StudentProfile.class);
                        startActivity(ep);

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Do you want to Logout?")
                .setCancelable(false)
                .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent gh=new Intent(Home.this,MainActivity.class);
                        startActivity(gh);
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

