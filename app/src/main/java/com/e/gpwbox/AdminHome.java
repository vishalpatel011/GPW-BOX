package com.e.gpwbox;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class AdminHome extends AppCompatActivity {
Button alo;
    FirebaseAuth firebaseAuth;
    CardView viewmessage,studentreq,facultyreq;
    CardView cv1,cv2,cv4,cv5,cv6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        alo=(Button)findViewById(R.id.adminlogout);
        alo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    firebaseAuth.signOut();
                    Toast.makeText(AdminHome.this, "Logout Succesful", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                Toast.makeText(AdminHome.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                Intent lo = new Intent(AdminHome.this,MainActivity.class);
                startActivity(lo);
            }
        });

        studentreq=(CardView)findViewById(R.id.studentreq);
        studentreq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pm=new Intent(AdminHome.this,studentrequest.class);
                startActivity(pm);

            }
        });

       facultyreq=(CardView)findViewById(R.id.facultyreq);
        facultyreq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent am=new Intent(AdminHome.this,facultyrequest.class);
                startActivity(am);

            }
        });

        viewmessage=(CardView)findViewById(R.id.viewmsg);
        viewmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent vm=new Intent(AdminHome.this,ViewMessage.class);
                startActivity(vm);

            }
        });


        cv5=(CardView)findViewById(R.id.hodreg);
        cv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hr = new Intent(AdminHome.this,HOD.class);
                startActivity(hr);
            }
        });
        cv6=(CardView)findViewById(R.id.prinreg);
        cv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pr=new Intent(AdminHome.this,Principal.class);
                startActivity(pr);
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
                        Intent gh=new Intent(AdminHome.this,MainActivity.class);
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
