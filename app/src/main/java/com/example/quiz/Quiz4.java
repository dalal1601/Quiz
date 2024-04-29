package com.example.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

public class Quiz4 extends AppCompatActivity {

    RadioGroup rg;
    Button Next;
    RadioButton rb;
    String CrtAns="Ursa Major";
    int Score;
    // Timer variables
    private static final int TIMER_DELAY = 1000; // Timer delay in milliseconds (1 second)
    private static final int TOTAL_TIME = 30000; // Total time in milliseconds (30 seconds)
    private Handler handler;
    private Runnable timerRunnable;
    private TextView timerTextView;
    private int remainingTime = TOTAL_TIME;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz4);
        rg=findViewById(R.id.radio_group_choices);
        Next=findViewById(R.id.next_button);
        ///////////
        Intent i1=getIntent();
        Score= i1.getIntExtra("Score",0);
        ////////////
        /////timer
        timerTextView = findViewById(R.id.timer_text_view); // Assuming you have a TextView to display the timer

        // Initialize handler
        handler = new Handler();

        // Start the timer
        startTimer();
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rg.getCheckedRadioButtonId()==-1){
                    Toast.makeText(getApplicationContext(),"please ....",Toast.LENGTH_SHORT).show();

                }else{
                    rb=findViewById(rg.getCheckedRadioButtonId());
                    //(rb.getId==R.id.choice1)
                    if(rb.getText().toString().equals(CrtAns)) Score+=1;
                    Intent i1=new Intent(getApplicationContext(),Quiz5.class);
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
        Intent i1 = new Intent(getApplicationContext(), Quiz5.class);
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