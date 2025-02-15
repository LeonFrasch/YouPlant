package com.example.youplantjava;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Login extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private ImageButton loginButton;
    private FirebaseAuth auth;
    String sessionID;
    FirebaseFirestore db;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = FirebaseFirestore.getInstance();

        sessionID = UUID.randomUUID().toString();
        SharedPreferences sharedPreferences = getSharedPreferences("session_data", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("session_id", sessionID);
        editor.apply();

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
                Toast.makeText(Login.this, "Experiment started!", Toast.LENGTH_SHORT).show();
            });
            Map<String, Object> sessionData = new HashMap<>();
            sessionData.put("session_user", auth.getUid());
            sessionData.put("session_id", sessionID);

            long start_time = System.currentTimeMillis();
            Date date = new Date(start_time);

            // Format für das Datum und die Uhrzeit
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

            String formatted_start_Date = dateFormat.format(date);

            sessionData.put("start_time", formatted_start_Date);
            sessionData.put("end_time", 0);
            sessionData.put("pages", new ArrayList<>());

            db.collection("sessions").document(sessionID).set(sessionData).addOnSuccessListener(aVoid -> Log.d("Firestore", "Session started")).addOnFailureListener(e -> Log.w("Firestore", "Error starting session", e));
        startActivity(new Intent(Login.this, IntroScreen.class));
            finish();
        }).addOnFailureListener(e -> {
            runOnUiThread(() -> {
                Toast.makeText(Login.this, "Login failed!", Toast.LENGTH_SHORT).show();
            });
        });
    }
}