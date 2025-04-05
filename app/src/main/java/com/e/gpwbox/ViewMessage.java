package com.e.gpwbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ViewMessage extends AppCompatActivity {

    RecyclerView NewsRecycleview;
    NewsAdapter newsAdapter;
    List<Message> mData;
    DatabaseReference mDatabaseRef;
    EditText search;

    DatabaseReference databaseReference;
    String msgid,userid,um,time,urole;
    FirebaseAuth firebaseAuth;
RecyclerView recyclerView;
DataSnapshot dataSnapshot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_message);
        search=(EditText)findViewById(R.id.searchmsg);

        NewsRecycleview =findViewById(R.id.news_rv);

        firebaseAuth = FirebaseAuth.getInstance();
        um =  firebaseAuth.getCurrentUser().getUid();
     userid =  firebaseAuth.getCurrentUser().getUid();

        recyclerView = (RecyclerView)findViewById(R.id.news_rv);


        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Suggection");

        mData=new ArrayList<>();

        mDatabaseRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot Suggection : snapshot.getChildren()) {
                    Message upload = Suggection.getValue(Message.class);
                    assert mData != null;

                    String title = upload.getMsgtype();
                    String content = upload.getMsg();
                    String mode = upload.getMode();
                    String msgid=upload.getMsgid();
                    String uname=upload.getUname();
                    String time= upload.getTime();
                    String uenno = upload.getUenno();
                    String udept = upload.getUdept();

                    mData.add(new Message(title,content,mode,msgid,userid,uname,uenno,udept,time,um));

                }

                Collections.reverse(mData);

                newsAdapter = new NewsAdapter(ViewMessage.this, mData);

                NewsRecycleview.setAdapter(newsAdapter);

            }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(ViewMessage.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });



     newsAdapter=new NewsAdapter(this,mData);
        NewsRecycleview.setAdapter(newsAdapter);
        NewsRecycleview.setLayoutManager(new LinearLayoutManager(this));

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                newsAdapter.getFilter().filter(s);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


}

