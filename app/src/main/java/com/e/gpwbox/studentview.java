package com.e.gpwbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class studentview extends AppCompatActivity {
TextView t1,t2,t3,t4,uname,udept,uenno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentview);

        t1 = (TextView)findViewById(R.id.tt1);
        Intent i = getIntent();
        String title = i.getStringExtra("title");
        t1.setText(title);

        t2 = (TextView)findViewById(R.id.tt2);
        Intent t = getIntent();
        String content = t.getStringExtra("content");
        t2.setText(content);

        t3 = (TextView)findViewById(R.id.tt3);
        Intent f = getIntent();
        String date = f.getStringExtra("date");
        t3.setText(date);

        t4 = (TextView)findViewById(R.id.tt4);
        Intent d = getIntent();
        String time = d.getStringExtra("time");
        t4.setText(time);

        uname = (TextView)findViewById(R.id.uname);
        Intent n = getIntent();
        String name = n.getStringExtra("uname");
        uname.setText(name);

        uenno = (TextView)findViewById(R.id.uenno);
        Intent e = getIntent();
        String enno = e.getStringExtra("uenno");
       uenno. setText("" + enno.replace("@gpw.com",""));;


        udept = (TextView)findViewById(R.id.udept);
        Intent p = getIntent();
        String dept = p.getStringExtra("udept");
        udept.setText(dept);
    }
}
