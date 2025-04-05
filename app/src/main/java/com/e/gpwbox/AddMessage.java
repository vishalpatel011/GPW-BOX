package com.e.gpwbox;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.e.gpwbox.ui.Messagge;
import com.e.gpwbox.ui.complaint;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AddMessage extends AppCompatActivity {
    EditText msg;
    Button send,btbrowse,btreset;
    ImageView imge;
    Spinner msgtype,mode;
    String userid,msgid,uenno,uname,udept,um,urole,complaintidd;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    TextView adate;
    DatabaseReference databaseReference1;
    DataSnapshot dataSnapshot;
    DatabaseReference databaseReference2;
    DatabaseReference databaseReference3;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_message);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Calendar calendar=Calendar.getInstance();
        String currentDate= DateFormat.getDateInstance().format(calendar.getTime());
        adate=(TextView)findViewById(R.id.date);
        adate.setText(currentDate);

        msg=(EditText)findViewById(R.id.msg);
        mode=(Spinner)findViewById(R.id.mode);
        msgtype = (Spinner)findViewById(R.id.msgtype);
        adate=(TextView)findViewById(R.id.date);
        imge=(ImageView)findViewById(R.id.img);
        send=(Button)findViewById(R.id.send);
        imge=(ImageView)findViewById(R.id.img);
        btbrowse=(Button)findViewById(R.id.btnbrowse);
        btreset=(Button)findViewById(R.id.btnreset);


        ActionBar actionBar = getSupportActionBar();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(actionBar!=null){
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_4));
        }
        btbrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.startPickImageActivity(AddMessage.this);
            }
        });

        btreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imge.setImageBitmap(null);
            }
        });
        firebaseAuth=FirebaseAuth.getInstance();
       userid =  firebaseAuth.getCurrentUser().getUid();
msgid= firebaseAuth.getCurrentUser().getUid();

um=firebaseAuth.getCurrentUser().getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference("Complaint");
        databaseReference1 = FirebaseDatabase.getInstance().getReference("Suser").child(userid);
databaseReference2=FirebaseDatabase.getInstance().getReference("Suggection");

        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                uname = dataSnapshot.child("name").getValue().toString();
uenno = dataSnapshot.child("enno").getValue().toString();
udept = dataSnapshot.child("dept").getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String Msgtype = msgtype.getSelectedItem().toString();
                final String Msg = msg.getText().toString();
                final String Mode = mode.getSelectedItem().toString();
                final String Date = adate.getText().toString();


                if (TextUtils.isEmpty(Msg)) {
                    Toast.makeText(AddMessage.this, "Please Enter Your Message", Toast.LENGTH_SHORT).show();
                }

                String msgid = databaseReference.push().getKey();
          um = userid + msgid;
                Message ms = new Message(Msgtype, Msg, Mode, msgid, userid, uname, uenno, udept, Date,um);



    if (Msgtype.equals("Complaint") || Msgtype.equals("Permission")) {
        FirebaseDatabase.getInstance().getReference("Complaint")
                .child(um)
                .setValue(ms).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Toast.makeText(AddMessage.this, "Message Send", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Home.class));
            }
        });


}
              else {
                    FirebaseDatabase.getInstance().getReference("Suggection")
                            .child(um)
                            .setValue(ms).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            Toast.makeText(AddMessage.this, "Message Send", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Home.class));
                        }
                    });

                }

            }



        });



    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE
                && resultCode == Activity.RESULT_OK) {
            Uri imageuri = CropImage.getPickImageResultUri(this, data);
            if (CropImage.isReadExternalStoragePermissionsRequired(this,imageuri)){
                uri=imageuri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}
                        ,0);}else {
                startCrop(imageuri);}
        }
        if(requestCode== CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            if (resultCode==RESULT_OK){
                imge.setImageURI(result.getUri());
                Toast.makeText(this,"Image Uploded Successfully",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startCrop(Uri imageuri) {
        CropImage.activity(imageuri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }
}
