package com.example.youplantjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchTwoPlants extends AppCompatActivity {
    boolean firstA;
    FirebaseFirestore db;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_two_plants);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        db.collection("firstA").get().addOnSuccessListener(queryDocumentSnapshots0 -> {
            List<String> firstAUIDs = new ArrayList<>();
            if (!queryDocumentSnapshots0.isEmpty()) {
                for (DocumentSnapshot document : queryDocumentSnapshots0.getDocuments()) {
                    Map<String, Object> firstAs = document.getData();
                    firstAUIDs.add(firstAs.get("UID").toString());
                }
            }
            if (firstAUIDs.contains(auth.getUid())) { // User ist A
                firstA = true;
            }

            findViewById(R.id.add_tomato_button).setOnClickListener((v) -> {
                Intent intent = new Intent(this, AddTomato.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            });
            findViewById(R.id.add_cactus_button).setOnClickListener((v) -> {
                Intent intent = new Intent(this, AddCactus.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            });

            if (firstA) {   // case: DesignFT
                findViewById(R.id.info_cactus_button).setOnClickListener((v) -> {
                    Intent intent = new Intent(this, GeneralInfoCactusDesignFT.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                });
                findViewById(R.id.info_tomato_button).setOnClickListener((v) -> {
                    Intent intent = new Intent(this, GeneralInfoTomatoDesignFT.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                });
            } else {   // case: DesignCards
                findViewById(R.id.info_cactus_button).setOnClickListener((v) -> {
                    Intent intent = new Intent(this, GeneralInfoCactusDesignCards.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                });
                findViewById(R.id.info_tomato_button).setOnClickListener((v) -> {
                    Intent intent = new Intent(this, GeneralInfoTomatoDesignCards.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                });
            }
            findViewById(R.id.calendarView).setOnClickListener((v) -> {
                Intent intent = new Intent(this, Calendar.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            });

            findViewById(R.id.plantView).setOnClickListener((v) -> {
                Intent intent = new Intent(this, IntroScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            });
        });
    }
}