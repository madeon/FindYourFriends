package com.example.mathias.findyourfriends;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button createUserButton;
    private Button loginButton;
    private FirebaseAuth firebaseAuth;
    private final static int LOGIN_PERMISSION = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        String email = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                         if(task.isSuccessful()) {
                             Intent intent = new Intent(MainActivity.this, NavigationDrawer.class);
                             startActivity(intent);
                             finish();
                         }
                         else {
                             //Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
                         }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == LOGIN_PERMISSION) {
            startNewActivity(resultCode, data);
        }
    }

    private void startNewActivity(int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            Intent intent = new Intent(MainActivity.this, NavigationDrawer.class);
            startActivity(intent);
            finish();
        }

        else {
            Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
        }
    }


}
