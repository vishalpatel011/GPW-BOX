package com.e.gpwbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PrincipalProfile extends AppCompatActivity {
    ImageView back;
    TextView name_title,st_email,st_phone,st_enrollment,msgcount;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_profile);
        firebaseAuth = FirebaseAuth.getInstance();
        userid =  firebaseAuth.getCurrentUser().getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference("puser").child(userid);


        name_title=findViewById(R.id.st_name);
        msgcount=findViewById(R.id.msg_count);
        st_email=findViewById(R.id.st_email);
        st_phone=findViewById(R.id.st_mobile);
        st_enrollment=findViewById(R.id.st_enroll);
        back=(ImageView)findViewById(R.id.back_btn);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("prname").getValue().toString();
                String enroll=dataSnapshot.child("prid").getValue().toString();
                String Email=dataSnapshot.child("premail").getValue().toString();
                String phone=dataSnapshot.child("prmobno").getValue().toString();
                name_title.setText("" + name);
                st_enrollment.setText("" + enroll.replace("@gpw.com", ""));
                st_email.setText(""+Email);
                st_phone.setText(""+phone);

            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ba=new Intent(PrincipalProfile.this,PrincipalHome.class);
                startActivity(ba);
            }
        });
    }
}
