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

public class PrincipalRecycleview extends AppCompatActivity {
RecyclerView prrecycle;
    PrAdapter prAdapter;
    List<Message> mData;
    DatabaseReference mDatabaseRef;
    EditText search;
    FirebaseAuth firebaseAuth;
    DataSnapshot dataSnapshot;
    String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_recycleview);
        prrecycle=findViewById(R.id.news_pr);
        search=(EditText)findViewById(R.id.searchmsg);
        firebaseAuth = FirebaseAuth.getInstance();
        userid =  firebaseAuth.getCurrentUser().getUid();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Hodgrant");

        mData=new ArrayList<>();

        mDatabaseRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot Hodgrant : snapshot.getChildren()) {
                    Message upload = Hodgrant.getValue(Message.class);
                    assert mData != null;

                    String title = upload.getMsgtype();
                    String content = upload.getMsg();
                    String mode = upload.getMode();
                    String msgid=upload.getMsgid();
                    String uname=upload.getUname();
                    String time= upload.getTime();
                    String uenno = upload.getUenno();
                    String udept = upload.getUdept();
String um =upload.getUm();
                    mData.add(new Message(title,content,mode,msgid,userid,uname,uenno,udept,time,um));

                }

                Collections.reverse(mData);

                prAdapter = new PrAdapter(PrincipalRecycleview.this, mData);

                prrecycle.setAdapter(prAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(PrincipalRecycleview.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



        prAdapter=new PrAdapter(this,mData);
      prrecycle.setAdapter(prAdapter);
        prrecycle.setLayoutManager(new LinearLayoutManager(this));
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                prAdapter.getFilter().filter(s);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }
}
