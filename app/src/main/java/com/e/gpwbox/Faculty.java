package com.e.gpwbox;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
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

import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class Faculty extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    EditText factname,staffid,factemail,factmobno,factpass,factcp;
    Button registerf,btbrowse,btreset;
    Spinner fdept;
    ImageView icardpic;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    AwesomeValidation awesomeValidation;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty);
        Spinner spinner = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.dept,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_4));
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        factname=(EditText)findViewById(R.id.factname);
        staffid=(EditText)findViewById(R.id.staffid);
        fdept = (Spinner)findViewById(R.id.spinner2);
        factemail=(EditText)findViewById(R.id.factimail);
        factmobno=(EditText)findViewById(R.id.factmobno);
        factpass=(EditText)findViewById(R.id.factpass);
        factcp=(EditText)findViewById(R.id.factconpass);
        registerf=(Button) findViewById(R.id.regi);
        btbrowse=(Button)findViewById(R.id.btnbrowse);
        btreset=(Button)findViewById(R.id.btnreset);
        icardpic=(ImageView)findViewById(R.id.fact_icardpic);
        databaseReference = FirebaseDatabase.getInstance().getReference("fuser");


        firebaseAuth=FirebaseAuth.getInstance();
        awesomeValidation=new AwesomeValidation(ValidationStyle.BASIC);
        String regexPassword="(?=.*[a-z]) (?=.*[A-Z]) (?=.*[\\d]) (?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";

        awesomeValidation.addValidation(this,R.id.factname,
                RegexTemplate.NOT_EMPTY,R.string.invalid_name);
        awesomeValidation.addValidation(this,R.id.staffid,
                ".{6}",R.string.invalid_enrollment );
        awesomeValidation.addValidation(this,R.id.factconpass,
                R.id.factpass,R.string.invalid_match);
        awesomeValidation.addValidation(this,R.id.factpass,
                regexPassword,R.string.invalid_passworddd);
        awesomeValidation.addValidation(this,R.id.factmobno,
                ".{10}",R.string.invalid_mobile );
        awesomeValidation.addValidation(this,R.id.factimail,
                Patterns.EMAIL_ADDRESS,R.string.invalid_email);
        btbrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.startPickImageActivity(Faculty.this);
            }
        });

        btreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                icardpic.setImageBitmap(null);
            }
        });


        registerf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String Name = factname.getText().toString();
                final String Staffid = staffid.getText().toString()+"@gpw.com";
                final String Dept=fdept.getSelectedItem().toString();
                final String Email = factemail.getText().toString();
                final String Mobileno = factmobno.getText().toString();
                final String Password = factpass.getText().toString();
                final String Role=("Faculty");
                if(awesomeValidation.validate()){
                    firebaseAuth.createUserWithEmailAndPassword(Staffid,Password)
                            .addOnCompleteListener(Faculty.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful())
                                    {



                                        facultyrg information = new facultyrg(Name,Staffid,Dept,Email,Mobileno,Password,Role);

                                        FirebaseDatabase.getInstance().getReference("fuser")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                Toast.makeText(Faculty.this,"Registration Complete",Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                            }
                                        });

                                    }


                                    else {
                                        Toast.makeText(Faculty.this,"No success",Toast.LENGTH_SHORT).show();
                                    }


                                }
                            });




                }else {
                    Toast.makeText(Faculty.this,"No success",Toast.LENGTH_SHORT).show();
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
