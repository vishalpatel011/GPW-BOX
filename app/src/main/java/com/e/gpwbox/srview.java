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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class srview extends AppCompatActivity {
    TextView name,enno,dept,email,mobile,pass,role,user;
    Button approval;
    String userid;
    DatabaseReference databaseReference1,databaseReference2;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_srview);
        firebaseAuth=FirebaseAuth.getInstance();
        userid =  firebaseAuth.getCurrentUser().getUid();
        databaseReference2=FirebaseDatabase.getInstance().getReference("sa").child(userid);
        databaseReference1 = FirebaseDatabase.getInstance().getReference("Suser");

        name= (TextView)findViewById(R.id.name);
        Intent i = getIntent();
        String title = i.getStringExtra("title");
        name.setText(title);

        enno = (TextView)findViewById(R.id.enno);
        Intent t = getIntent();
        String content = t.getStringExtra("content");
        enno. setText("" + content.replace("@gpw.com",""));


        dept = (TextView)findViewById(R.id.dept);
        Intent f = getIntent();
        String date = f.getStringExtra("date");
        dept.setText(date);

       email = (TextView)findViewById(R.id.email);
        Intent d = getIntent();
        String emailid = d.getStringExtra("email");
        email.setText(emailid);

        mobile = (TextView)findViewById(R.id.mobno);
        Intent n = getIntent();
        String mobileno = n.getStringExtra("mobile");
        mobile.setText(mobileno);

        pass = (TextView)findViewById(R.id.pass);
        Intent e = getIntent();
        String password = e.getStringExtra("pass");
        pass. setText(password);;


        role = (TextView)findViewById(R.id.role);
        Intent p = getIntent();
        String r = p.getStringExtra("role");
        role.setText(r);

       user = (TextView)findViewById(R.id.userid);
        Intent u = getIntent();
        String use = u.getStringExtra("userid");
        user.setText(use);


        approval=(Button)findViewById(R.id.approval);


        approval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String Name = name.getText().toString();
                final String Enno = enno.getText().toString();
                final String Dept = dept.getText().toString();
                final String Email = email.getText().toString();
                final String Mobileno = mobile.getText().toString();
                final String Password = pass.getText().toString();
                final String Role = role.getText().toString();
                final String User = user.getText().toString();
                approval.isClickable();
                approval.setBackgroundColor(approval.getContext().getResources().getColor(R.color.colorPrimaryDark));


                String Userid = databaseReference1.push().getKey();
                                    studentrg information = new studentrg(Name, Enno, Dept, Email, Mobileno, Password, Role, User);
                                    FirebaseDatabase.getInstance().getReference("Suser")
                                            .child(Userid)
                                            .setValue(information).addOnCompleteListener(new OnCompleteListener<Void>()
                                    {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task)
                                        {

                                            Toast.makeText(srview.this,"Message Approval",Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(),AdminHome.class));
                                        }
                                    });

                                }





                        });


            }

    }
