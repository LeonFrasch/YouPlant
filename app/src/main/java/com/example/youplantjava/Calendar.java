package com.example.youplantjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Calendar extends AppCompatActivity {
    long startTime = System.currentTimeMillis();
    String sessionID;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        db = FirebaseFirestore.getInstance();

        SharedPreferences sP = getSharedPreferences("session_data", MODE_PRIVATE);
        sessionID = sP.getString("session_id", null);

        findViewById(R.id.plantTab).setOnClickListener((v) -> {
            Intent intent = new Intent(this, IntroScreen.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        findViewById(R.id.searchTab).setOnClickListener((v) -> {
            Intent intent = new Intent(this, SearchTwoPlants.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
    @Override
    protected void onPause() {
        super.onPause();

        long timeSpent = System.currentTimeMillis() - startTime;

        Map<String, Object> pageData = new HashMap<>();
        pageData.put("page_name", "Calendar");
        pageData.put("time_spent", timeSpent);

        db.collection("sessions").document(sessionID)
                .update("pages", FieldValue.arrayUnion(pageData))
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "Page data saved"))
                .addOnFailureListener(e -> Log.w("Firestore", "Error saving page data", e));
    }
}