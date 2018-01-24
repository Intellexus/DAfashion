package com.systems.intellexus.dafashion;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Patterns;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    Button signup,login;
    EditText mailid,password;
    ProgressBar progressBar;
    String stringpassword,stringemaild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();
        mailid = (EditText) findViewById(R.id.editTextmailID);
        password = findViewById(R.id.editTextPassword);
        signup = findViewById(R.id.signup_btnSignIN);
        login = findViewById(R.id.btnLogin);
        progressBar = findViewById(R.id.progressBar2);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             registerfunction();
            }
        });
      login.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              loginFunction();

          }
      });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void loginFunction() {
        stringpassword = password.getText().toString() ;
        stringemaild = mailid.getText().toString();

        if (stringemaild.isEmpty()){
            mailid.setError("Email is Required");
            mailid.requestFocus();
            return;
        }

        else if (!Patterns.EMAIL_ADDRESS.matcher(stringemaild).matches()){
            mailid.setError("Please enter valid Email id:");
            mailid.requestFocus();
            return;
        }


       else if (stringpassword.isEmpty()){
            password.setError("password is Required");
            password.requestFocus();

        }else{
            progressBar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(stringemaild,stringpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(View.GONE);

                    if (task.isSuccessful()){
                        Toast.makeText(MainActivity.this, "U are loged in successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(),HomePage.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);


                    }else {
                        Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }



    }

    private void registerfunction() {
        stringpassword = password.getText().toString() ;
        stringemaild = mailid.getText().toString();

        if (stringemaild.isEmpty()){
            mailid.setError("Email is Required");
            mailid.requestFocus();
            return;
        }

       else  if (!Patterns.EMAIL_ADDRESS.matcher(stringemaild).matches()){
            mailid.setError("Please enter valid Email id:");
            mailid.requestFocus();
            return;
        }


       else if (stringpassword.isEmpty()){
            password.setError("password is Required");
            password.requestFocus();

        }else{



            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(stringemaild,stringpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(View.GONE);

                    if (task.isSuccessful()){
                        Toast.makeText(MainActivity.this, "User Registered Successfully...", Toast.LENGTH_SHORT).show();
                        mailid.setText("");
                        password.setText("");
                    }else {
                        if (task.getException() instanceof FirebaseAuthUserCollisionException){
                            Toast.makeText(MainActivity.this, "You are already Registered...", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Some Error Occurred..."+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }


    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
