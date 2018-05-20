package com.example.mathias.findyourfriends.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mathias.findyourfriends.Database.DatabaseConnector;
import com.example.mathias.findyourfriends.Helpers.ToastMaker;
import com.example.mathias.findyourfriends.R;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private Button createUserButton;
    private Button loginButton;
    private FirebaseAuth firebaseAuth;
    private ToastMaker toastMaker;
    private DatabaseConnector database;
    private final static int LOGIN_PERMISSION = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        createUserButton = (Button)findViewById(R.id.createUserButton);
        loginButton = (Button)findViewById(R.id.loginButton);
        firebaseAuth = FirebaseAuth.getInstance();
        toastMaker = new ToastMaker();
        requestGpsPermission();
    }

    private void requestGpsPermission() {

        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION}, 1001);

            return;
        }
    }

    public void createUserButton_click(View view) {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setAllowNewEmailAccounts(true).build(), LOGIN_PERMISSION
        );
    }

    public void loginButton_click(View view) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            Intent intent = new Intent(this, NavigationDrawerActivity.class);
            startActivity(intent);
        }

        else {
            toastMaker.createToast(this, "You need to create a user.");

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == LOGIN_PERMISSION) {
            startNewActivity(resultCode, data);
        }
    }

    private void startNewActivity(int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            checkIfUserExists();

        }

        else {
            Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void createUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        database = new DatabaseConnector("Users");
        database.createUser(user.getEmail(), user.getDisplayName());
        Toast.makeText(this, "User Created", Toast.LENGTH_SHORT).show();
    }

    public void checkIfUserExists() {
        database = new DatabaseConnector("Users");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();

        Query query = database.getRef().orderByChild("email").equalTo(email);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Intent intent = new Intent(LoginActivity.this, NavigationDrawerActivity.class);
                    startActivity(intent);
                    finish();
                }

                else {
                    createUser();
                    Intent intent = new Intent(LoginActivity.this, NavigationDrawerActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}