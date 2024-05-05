package com.example.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
///////////////not sure
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Score extends AppCompatActivity {

    TextView tvscore;
    ProgressBar pd;
    Button btry,blogout;
    int score;
    // Declare Firebase variables
    private FirebaseAuth mAuth;
    private DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_score);

        // Initialize Firebase components
        mAuth = FirebaseAuth.getInstance();
        usersRef = FirebaseDatabase.getInstance().getReference().child("users");
        //Initialize the view
        tvscore = findViewById(R.id.score);
        pd = findViewById(R.id.progressBar);
        btry=findViewById(R.id.tryagain_button);
        blogout = findViewById(R.id.logout_button);
        TextView tvLeaderboard = findViewById(R.id.tvLeaderboard); // Find the TextView for leaderboard

        Intent i1=getIntent();
        score = i1.getIntExtra("score", 0);

        tvscore.setText(score * 20 +"%");
        pd.setProgress(score * 20 );





        btry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(getApplicationContext(),Quiz1.class);
                startActivity(i2);
                finish();

            }
        });

        blogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i3 = new Intent(Score.this, MainActivity.class);
                startActivity(i3);
                finish();
            }
        });


        // Call this method when the user finishes the quiz
        updateScoreInFirebase(score*20);
        // Call displayLeaderboard() method to display the leaderboard
        displayLeaderboard(tvLeaderboard);



    }
    // Update user's score in Firebase
    // Update user's score in Firebase
    private void updateScoreInFirebase(int score) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            usersRef.child(userId).child("score").setValue(score);
        }
    }


    // Display a basic leaderboard (dummy data or data from local storage)
    // Display leaderboard from Firebase
    private void displayLeaderboard(final TextView tvLeaderboard) {
        usersRef.orderByChild("score").limitToLast(10).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                StringBuilder leaderboardText = new StringBuilder("Leaderboard:\n");
                int rank = 1;
                List<DataSnapshot> userSnapshots = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    userSnapshots.add(snapshot);
                }
                Collections.reverse(userSnapshots);
                for (DataSnapshot snapshot : userSnapshots) {
                    String userId = snapshot.getKey(); // Get the user's UID /// whyyy!
                    int userScore = snapshot.child("score").getValue(Integer.class);

                    // Assuming you have a child node 'email' under each user in your database
                    String email = snapshot.child("email").getValue(String.class);

                    leaderboardText.append(rank).append(". ").append(email).append(" - ").append(userScore).append("%\n");
                    rank++;
                }
                tvLeaderboard.setText(leaderboardText.toString());

                setProgressBar(score * 20);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    private void setProgressBar(int progress) {
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(progress);
    }




}

