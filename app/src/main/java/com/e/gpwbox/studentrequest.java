package com.e.gpwbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class studentrequest extends AppCompatActivity {
    RecyclerView reqRecycleview;
    requestadapter rAdapter;
    List<studentrg> mData;
    DatabaseReference mDatabaseRef;
    EditText search;


    String msgid,userid,um,time,urole,uname,uenno,udept;
    FirebaseAuth firebaseAuth;
    DataSnapshot dataSnapshot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentrequest);

        reqRecycleview =findViewById(R.id.streq);
        firebaseAuth = FirebaseAuth.getInstance();
        um =  firebaseAuth.getCurrentUser().getUid();
        userid =  firebaseAuth.getCurrentUser().getUid();

        // recyclerView = (RecyclerView)findViewById(R.id.news_hod);


        mDatabaseRef = FirebaseDatabase.getInstance().getReference("sa");

        mData=new ArrayList<studentrg>();

        mDatabaseRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot sa : snapshot.getChildren()) {
                    studentrg upload = sa.getValue(studentrg.class);
                    assert mData != null;

                    String Name = upload.getName();
                    String Enno = upload.getEnno();
                    String Dept=upload.getDept();
                    String Email = upload.getEmail();
                    String Mobile=upload.getMobileno();
                    String Pass=upload.getPassword();
                    String Role= upload.getRole();
String  Userid=upload.getUserid();

                    mData.add(new studentrg(Name,Enno,Dept,Email,Mobile,Pass,Role,Userid));

                }

                Collections.reverse(mData);

                rAdapter = new requestadapter(studentrequest.this, mData);

                reqRecycleview.setAdapter(rAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(studentrequest.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



        rAdapter=new requestadapter(this,mData);
        reqRecycleview.setAdapter(rAdapter);
        reqRecycleview.setLayoutManager(new LinearLayoutManager(this));



    }




}
