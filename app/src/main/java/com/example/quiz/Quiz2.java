package com.example.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.FirebaseFirestore;

public class Quiz2 extends AppCompatActivity {

    RadioGroup rg;
    Button Next;
    RadioButton rb;
    String CrtAns="Mars";
    int Score;
    // Timer variables
    private static final int TIMER_DELAY = 1000; // Timer delay in milliseconds (1 second)
    private static final int TOTAL_TIME = 30000; // Total time in milliseconds (30 seconds)
    private Handler handler;
    private Runnable timerRunnable;
    private TextView timerTextView;
    private int remainingTime = TOTAL_TIME;

    private FirebaseFirestore db;
    private String correctAnswer;
    private TextView questionText;
    private RadioButton radioA, radioB, radioC, radioD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz2);///////////
        rg=findViewById(R.id.radio_group_choices);
        Next=findViewById(R.id.next_button);
        ///////////
        Intent i1=getIntent();
        Score= i1.getIntExtra("score",0);
        ////////////
        /////timer
        timerTextView = findViewById(R.id.timer_text_view); // Assuming you have a TextView to display the timer

        // Initialize handler
        handler = new Handler();

        // Initialize the views for question and options
        questionText = findViewById(R.id.question_text);
        radioA = findViewById(R.id.choice1);
        radioB = findViewById(R.id.choice2);
        radioC = findViewById(R.id.choice3);
        radioD = findViewById(R.id.choice4);

        // Start the timer
        startTimer();

        db = FirebaseFirestore.getInstance();

        db.collection("question").document("quiz2")
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String question = documentSnapshot.getString("question");
                        String optionA = documentSnapshot.getString("option1");
                        String optionB = documentSnapshot.getString("option2");
                        String optionC = documentSnapshot.getString("option3");
                        String optionD = documentSnapshot.getString("option4");
                        correctAnswer = documentSnapshot.getString("correct_answer");

                        questionText.setText(question);
                        radioA.setText(optionA);
                        radioB.setText(optionB);
                        radioC.setText(optionC);
                        radioD.setText(optionD);

                        // Log the data
                        Log.d("Question1", "Question: " + question);
                        Log.d("Question1", "Option A: " + optionA);
                        Log.d("Question1", "Option B: " + optionB);
                        Log.d("Question1", "Option C: " + optionC);
                        Log.d("Question1", "Option D: " + optionD);
                        Log.d("Question1", "Correct Answer: " + correctAnswer);
                    } else {
                        Log.d("Question2", "No such document");
                        Toast.makeText(getApplicationContext(), "No such document", Toast.LENGTH_SHORT).show();
                    }
                })
        ;

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rg.getCheckedRadioButtonId()==-1){
                    Toast.makeText(getApplicationContext(),"please choose your answer",Toast.LENGTH_SHORT).show();

                }else{
                    rb=findViewById(rg.getCheckedRadioButtonId());
                    //(rb.getId==R.id.choice1)
                    if(rb.getText().toString().equals(CrtAns)) Score+=1;
                    // Stop the timer when moving to the next question
                    stopTimer();

                    Intent i1=new Intent(getApplicationContext(),Quiz3.class);
                    i1.putExtra("score",Score);
                    startActivity(i1);
                    //overridePendingTransition(R.anim.exit,R.anim.entry);
                    finish();
                }

            }
        });


    }
    private void startTimer() {
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                if (remainingTime > 0) {
                    remainingTime -= 1000; // Decrement remaining time by 1 second
                    updateTimerText();
                    handler.postDelayed(this, TIMER_DELAY); // Schedule the next iteration
                } else {
                    // Time's up
                    stopTimer();
                    // Handle what to do when time's up
                    moveToNextQuiz();
                }
            }
        };
        // Start the timer runnable after TIMER_DELAY milliseconds
        handler.postDelayed(timerRunnable, TIMER_DELAY);
    }
    private void moveToNextQuiz() {
        Toast.makeText(getApplicationContext(), "Time's up!", Toast.LENGTH_SHORT).show();

        // Add your logic to move to the next quiz here
        Intent i1 = new Intent(getApplicationContext(), Quiz3.class);
        i1.putExtra("score", Score);
        startActivity(i1);
        finish();
    }

    private void stopTimer() {
        handler.removeCallbacks(timerRunnable); // Remove the callback to stop the timer
    }

    private void updateTimerText() {
        // Convert remaining time to seconds and display
        int seconds = remainingTime / 1000;
        timerTextView.setText("Time remaining: " + seconds + "s");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Make sure to stop the timer when the activity is destroyed
        stopTimer();
    }


}