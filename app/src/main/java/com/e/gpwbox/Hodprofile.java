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

public class Hodprofile extends AppCompatActivity {
    ImageView st_icardpic;
    ImageView back;
    TextView name_title,department_title,st_email,st_phone,st_enrollment,st_department,msgcount;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hodprofile);
        firebaseAuth = FirebaseAuth.getInstance();
        userid =  firebaseAuth.getCurrentUser().getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference("huser").child(userid);


        name_title=findViewById(R.id.st_name);
        department_title=findViewById(R.id.st_department);
        msgcount=findViewById(R.id.msg_count);
        st_email=findViewById(R.id.st_email);
        st_phone=findViewById(R.id.st_mobile);
        st_enrollment=findViewById(R.id.st_enroll);
        back=(ImageView)findViewById(R.id.back_btn);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("hodname").getValue().toString();
                String enroll=dataSnapshot.child("hodid").getValue().toString();
                String department = dataSnapshot.child("hoddept").getValue().toString();
                String Email=dataSnapshot.child("hodemail").getValue().toString();
                String phone=dataSnapshot.child("hodmobno").getValue().toString();
                name_title.setText("" + name);
                st_enrollment.setText("" + enroll.replace("@gpw.com", ""));
                department_title.setText(""+department+" Department");
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
                Intent ba=new Intent(Hodprofile.this,HodHome.class);
                startActivity(ba);
            }
        });
    }
}
