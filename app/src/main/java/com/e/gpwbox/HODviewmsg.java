package com.e.gpwbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

public class HODviewmsg extends AppCompatActivity {

    RecyclerView hodRecycleview;
    HODAdapter hodAdapter;
    List<Message> mData;
    DatabaseReference mDatabaseRef;
    EditText search;


    String msgid,userid,um2,time,urole,uname,uenno,udept;
    FirebaseAuth firebaseAuth;
    DataSnapshot dataSnapshot;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_hodviewmsg);
        hodRecycleview =findViewById(R.id.news_hod);
        search=(EditText)findViewById(R.id.searchmsg);
        firebaseAuth = FirebaseAuth.getInstance();
       um2 =  firebaseAuth.getCurrentUser().getUid();
       userid =  firebaseAuth.getCurrentUser().getUid();

       // recyclerView = (RecyclerView)findViewById(R.id.news_hod);


        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Complaint");

        mData=new ArrayList<>();

        mDatabaseRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot Complaint : snapshot.getChildren()) {
                    Message upload = Complaint.getValue(Message.class);
                    assert mData != null;

                    String title = upload.getMsgtype();
                    String content = upload.getMsg();
                    String mode = upload.getMode();
                    String msgid=upload.getMsgid();
                    String uname=upload.getUname();
                    String time= upload.getTime();
                    String uenno = upload.getUenno();
                    String udept = upload.getUdept();
                    String complaintid=upload.getUm();

                    mData.add(new Message(title,content,mode,msgid,userid,uname,uenno,udept,time,um2));

                }

                Collections.reverse(mData);

                hodAdapter = new HODAdapter(HODviewmsg.this, mData);

                hodRecycleview.setAdapter(hodAdapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(HODviewmsg.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



        hodAdapter=new HODAdapter(this,mData);
        hodRecycleview.setAdapter(hodAdapter);
        hodRecycleview.setLayoutManager(new LinearLayoutManager(this));
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                hodAdapter.getFilter().filter(s);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }




}





