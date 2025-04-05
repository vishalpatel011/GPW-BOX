package com.e.gpwbox;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class Student extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

EditText name,enno,email,mobileno,password,cfpass;
ImageView icardpic;
Button registers,btbrowse,btreset;
Spinner department;
DatabaseReference databaseReference;
FirebaseAuth firebaseAuth;
String userid;
AwesomeValidation awesomeValidation;
Uri uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);


        department= findViewById(R.id.spinner12);
       /* ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.dept,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        department.setAdapter(adapter);
        department.setOnItemSelectedListener(this);*/




        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_4));
        }


        name=(EditText)findViewById(R.id.name);
        enno=(EditText)findViewById(R.id.enno);
        department=(Spinner)findViewById(R.id.spinner12);
        email=(EditText)findViewById(R.id.email);
        mobileno=(EditText)findViewById(R.id.mobno);
        password=(EditText)findViewById(R.id.passw);
        cfpass=(EditText)findViewById(R.id.cfpass);
        registers=(Button) findViewById(R.id.reg);
        btbrowse=(Button)findViewById(R.id.btnbrowse);
        btreset=(Button)findViewById(R.id.btnreset);
        icardpic=(ImageView)findViewById(R.id.stu_icardpic);

        firebaseAuth=FirebaseAuth.getInstance();
        userid =  firebaseAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("sa");


        awesomeValidation=new AwesomeValidation(ValidationStyle.BASIC);
        String regexPassword="(?=.*[a-z]) (?=.*[A-Z]) (?=.*[\\d]) (?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";
        awesomeValidation.addValidation(this,R.id.name,
                RegexTemplate.NOT_EMPTY,R.string.invalid_name);
        awesomeValidation.addValidation(this,R.id.enno,
               ".{12}",R.string.invalid_enrollment );
        awesomeValidation.addValidation(this,R.id.email,
                Patterns.EMAIL_ADDRESS,R.string.invalid_email);
        awesomeValidation.addValidation(this,R.id.cfpass,
                R.id.passw,R.string.invalid_match);
        //awesomeValidation.addValidation(this,R.id.passw,
              //  regexPassword,R.string.invalid_passworddd);
        awesomeValidation.addValidation(this,R.id.mobno,
                ".{10}",R.string.invalid_mobile );

        btbrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.startPickImageActivity(Student.this);
            }
        });

        btreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                icardpic.setImageBitmap(null);
            }
        });


        registers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String Name=name.getText().toString();
                final String Enno=enno.getText().toString()+"@gpw.com";
                final String Dept=department.getSelectedItem().toString();
                final String Email=email.getText().toString();
                final String Password=password.getText().toString();
                final String Mobileno=mobileno.getText().toString();
                final String Role=("Student");
               // final String Userid=databaseReference.getKey();
if(awesomeValidation.validate()){
    firebaseAuth.createUserWithEmailAndPassword(Enno,Password)
         .addOnCompleteListener(Student.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {

                        studentrg information = new studentrg(Name,Enno,Dept,Email,Mobileno,Password,Role,userid);
                        FirebaseDatabase.getInstance().getReference("sa")
                                .child(userid)
                                .setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                Toast.makeText(Student.this,"Registration Complete",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            }
                        });

                    }


                    else {
                        Toast.makeText(Student.this,"No success",Toast.LENGTH_SHORT).show();
                    }


                }
            });


}else{
    Toast.makeText(Student.this,"Validation Failed",Toast.LENGTH_SHORT).show();
}




            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        String text= parent.getItemAtPosition(i).toString();


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
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
                icardpic.setImageURI(result.getUri());
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
