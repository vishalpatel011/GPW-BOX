package com.e.gpwbox;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class hodview extends AppCompatActivity {
    TextView t1,t2,t3,t4,uname,udept,uenno;
    Button g,r;
    String userid,msgid,um,uidmsgid;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference1;
    DatabaseReference databaseReference2;
    DatabaseReference databaseReference3;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hodview);

        firebaseAuth=FirebaseAuth.getInstance();
        userid =  firebaseAuth.getCurrentUser().getUid();
        msgid= firebaseAuth.getCurrentUser().getUid();

        um=firebaseAuth.getCurrentUser().getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference("Complaint");
        databaseReference1 = FirebaseDatabase.getInstance().getReference("Suser").child(userid);
        databaseReference2=FirebaseDatabase.getInstance().getReference("Hodgrant");
        databaseReference3=FirebaseDatabase.getInstance().getReference("Complaint").child(userid).child(um);
Toast.makeText(hodview.this,""+databaseReference3,Toast.LENGTH_SHORT).show();
        t1 = (TextView) findViewById(R.id.tt1);
        Intent i = getIntent();
        String title = i.getStringExtra("title");
        t1.setText(title);

        t2 = (TextView) findViewById(R.id.tt2);
        Intent t = getIntent();
        String content = t.getStringExtra("content");
        t2.setText(content);

        t3 = (TextView) findViewById(R.id.tt3);
        Intent f = getIntent();
        String date = f.getStringExtra("date");
        t3.setText("Mode:"+date);

        t4 = (TextView) findViewById(R.id.tt4);
        Intent d = getIntent();
        String time = d.getStringExtra("time");
        t4.setText(time);

        uname = (TextView) findViewById(R.id.uname);
        Intent n = getIntent();
        String name = n.getStringExtra("uname");


        uenno = (TextView) findViewById(R.id.uenno);
        Intent e = getIntent();
        String enno = e.getStringExtra("uenno");
        uenno.setText("Enrollment no:"+"" + enno.replace("@gpw.com", ""));

        Intent m=getIntent();
        final String msgidd=m.getStringExtra("msgiddd");


        udept = (TextView) findViewById(R.id.udept);
        Intent p = getIntent();
        String dept = p.getStringExtra("udept");
        udept.setText("Department:"+dept);

        Intent q=getIntent();
        final String complaintid=q.getStringExtra("compid");
        uname.setText("Received by:"+complaintid);





        g=(Button)findViewById(R.id.hodgrant);
        r=(Button)findViewById(R.id.hodref);

        g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String Msgtype = t1.getText().toString();
                final String Msg = t2.getText().toString();
                final String Mode = t3.getText().toString();
                final String Date = t4.getText().toString();
final String Uname=uname.getText().toString();
                final String Uenno=uenno.getText().toString();
                final String Udept=udept.getText().toString();

                String msgid = databaseReference2.push().getKey();
                Message ms = new Message(Msgtype, Msg, Mode, msgid, userid, Uname, Uenno, Udept, Date,um);
                um = userid + msgidd;

                g.isClickable();
                g.setBackgroundColor(g.getContext().getResources().getColor(R.color.colorPrimaryDark));
                    FirebaseDatabase.getInstance().getReference("Hodgrant")
                            .child(um)
                            .setValue(ms).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            Toast.makeText(hodview.this, "Message grant", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), HODviewmsg.class));
                        }
                    });


                }





        });

        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



            }




    }


