package com.example.mathias.findyourfriends.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mathias.findyourfriends.Database.DatabaseConnector;
import com.example.mathias.findyourfriends.R;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button createUserButton;
    private Button loginButton;
    private FirebaseAuth firebaseAuth;
    DatabaseConnector database;
    private final static int LOGIN_PERMISSION = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        createUserButton = (Button)findViewById(R.id.createUserButton);
        loginButton = (Button)findViewById(R.id.loginButton);
        firebaseAuth = FirebaseAuth.getInstance();

    }

    public void createUserButton_click(View view) {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                .setAllowNewEmailAccounts(true).build(), LOGIN_PERMISSION
        );
    }

    public void loginButton_click(View view) {
        Intent intent = new Intent(this, NavigationDrawerActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == LOGIN_PERMISSION) {
            startNewActivity(resultCode, data);
        }
    }

    private void startNewActivity(int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            //Intent intent = new Intent(LoginActivity.this, CreateUserActivity.class);
            //startActivity(intent);
            //finish();
            checkIfUserExists();
        }

        else {
            Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
        }
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
                    Intent intent = new Intent(LoginActivity.this, CreateUserActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void databaseButton_click(View view) {

        database = new DatabaseConnector("Groups");
        database.createGroup("Tinderbox 2018");

        //database = new DatabaseConnector("Users");
        //database.createUser("Mathias", "mathias-1992@live.dk");

        Toast.makeText(this, "User added", Toast.LENGTH_SHORT).show();
    }


}
