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

public class Score extends AppCompatActivity {

    TextView tvscore;
    ProgressBar pd;
    Button btry,blogout;
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_score);
        tvscore = findViewById(R.id.score);
        pd = findViewById(R.id.progressBar);
        btry=findViewById(R.id.tryagain_button);
        blogout = findViewById(R.id.logout_button);
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












    }
}