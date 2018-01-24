package com.systems.intellexus.dafashion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class HomePage extends AppCompatActivity {

    String connect;
    String abc;
    String xy;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        functionprint1();

    }

    private void functionprint1() {
        Toast.makeText(this, "works at both side", Toast.LENGTH_SHORT).show();
    }
}
