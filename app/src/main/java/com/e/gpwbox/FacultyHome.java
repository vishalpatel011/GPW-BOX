package com.e.gpwbox;

import androidx.annotation.NonNull;
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

public class FacultyHome extends AppCompatActivity {
Button faclo;
    CardView viewmessage,factprofile;
    String userid;
    TextView unicid,name,enrollment,role;


FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_home);
        firebaseAuth = FirebaseAuth.getInstance();
        userid = firebaseAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("fuser").child(userid);
        faclo = (Button) findViewById(R.id.faclogout);

        name = (TextView) findViewById(R.id.name);
        enrollment = (TextView) findViewById(R.id.enrollment);
        role = (TextView) findViewById(R.id.role);
        Intent i = getIntent();
        String ROLE = i.getStringExtra("ROLE");
        role.setText(ROLE);



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String email = dataSnapshot.child("factname").getValue().toString();
                String passwards = dataSnapshot.child("fdept").getValue().toString();
                name.setText("" + email);
                enrollment.setText("" + passwards);
                // unicid.setText("" + userid);
                Toast.makeText(getApplicationContext(), "" + email + "\n" + passwards.replace("@gpw.com", ""), Toast.LENGTH_LONG).show();


                faclo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {

                            firebaseAuth.signOut();
                            Toast.makeText(FacultyHome.this, "Logout Succesful", Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            Toast.makeText(FacultyHome.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        Intent lo = new Intent(FacultyHome.this, MainActivity.class);
                        startActivity(lo);
                    }
                });
                viewmessage = (CardView) findViewById(R.id.viewmsg);
                viewmessage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent vm = new Intent(FacultyHome.this, ViewMessage.class);
                        startActivity(vm);

                    }
                });
                factprofile = (CardView) findViewById(R.id.factprofile);
                factprofile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent vm = new Intent(FacultyHome.this, FactProfile.class);
                        startActivity(vm);

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
                        Intent gh=new Intent(FacultyHome.this,MainActivity.class);
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
