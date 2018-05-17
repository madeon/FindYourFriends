package com.example.mathias.findyourfriends.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mathias.findyourfriends.Database.DatabaseConnector;
import com.example.mathias.findyourfriends.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreateUserActivity extends AppCompatActivity {

    private TextView text;
    private DatabaseConnector database;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        text = (TextView) findViewById(R.id.editText_username);
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    public void create_user_button_click(View view) {

        database = new DatabaseConnector("Users");
        String username = text.getText().toString();

        database.createUser(user.getEmail(), user.getDisplayName());

        Intent intent = new Intent(CreateUserActivity.this, NavigationDrawerActivity.class);
        startActivity(intent);
    }



}
