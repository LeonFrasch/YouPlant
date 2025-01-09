package com.example.youplantjava;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private ImageButton loginButton;
    private FirebaseAuth auth;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.emailText);
        password = findViewById(R.id.passwordText);
        loginButton = findViewById(R.id.loginButton);

        auth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener((v) -> { // start of Experiment
            String txt_email = email.getText().toString();
            String txt_password = password.getText().toString();
            loginUser(txt_email, txt_password);
        });
    }

    private void loginUser(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener((result) -> {
            runOnUiThread(() -> {
                Toast.makeText(Login.this, "Login successfully!", Toast.LENGTH_SHORT).show();
            });
            startActivity(new Intent(Login.this, IntroScreen.class));
            finish();
        }).addOnFailureListener(e -> {
            runOnUiThread(() -> {
                Toast.makeText(Login.this, "Login failed!", Toast.LENGTH_SHORT).show();
            });
        });
    }
}