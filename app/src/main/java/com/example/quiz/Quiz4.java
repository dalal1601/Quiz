package com.example.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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


}