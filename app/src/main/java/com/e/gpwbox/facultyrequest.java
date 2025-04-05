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

public class facultyrequest extends AppCompatActivity {
    RecyclerView fRecycleview;
    facultyAdapter fAdapter;
    List<facultyrg> mData;
    DatabaseReference mDatabaseRef;
    EditText search;


    String msgid,userid,um,time,urole,uname,uenno,udept;
    FirebaseAuth firebaseAuth;
    DataSnapshot dataSnapshot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facultyrequest);

        fRecycleview =findViewById(R.id.freq);
        firebaseAuth = FirebaseAuth.getInstance();
        um =  firebaseAuth.getCurrentUser().getUid();
        userid =  firebaseAuth.getCurrentUser().getUid();

        // recyclerView = (RecyclerView)findViewById(R.id.news_hod);


        mDatabaseRef = FirebaseDatabase.getInstance().getReference("fuser");

        mData=new ArrayList<facultyrg>();

        mDatabaseRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot fuser : snapshot.getChildren()) {
                    facultyrg upload = fuser.getValue(facultyrg.class);
                    assert mData != null;

                    String Name = upload.getFactname();
                    String Enno = upload.getStaffid();
                    String Dept=upload.getFdept();
                    String Email = upload.getFactemail();
                    String Mobile=upload.getFactmobno();
                    String Pass=upload.getFactpass();
                    String Role= upload.getFrole();


                    mData.add(new facultyrg(Name,Enno,Dept,Email,Mobile,Pass,Role));

                }

                Collections.reverse(mData);

                fAdapter = new facultyAdapter(facultyrequest.this, mData);

                fRecycleview.setAdapter(fAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(facultyrequest.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



        fAdapter=new facultyAdapter(this,mData);
        fRecycleview.setAdapter(fAdapter);
        fRecycleview.setLayoutManager(new LinearLayoutManager(this));



    }


}

