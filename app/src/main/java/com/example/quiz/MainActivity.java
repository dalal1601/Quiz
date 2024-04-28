package com.example.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {



    EditText email,password;
    Button sign;
    TextView Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the sign in button
        email = findViewById(R.id.mail);
        password = findViewById(R.id.pswd);
        sign = findViewById(R.id.sign);
        // Set click listener for the sign in button
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Quiz1Activity when the sign in button is clicked
                if(email.getText().toString().equals("dalal")&&password.getText().toString().equals("123")){
                    Intent i1= new Intent(getApplicationContext(),Quiz1.class);
                    startActivity(i1);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Login ...",Toast.LENGTH_SHORT).show();
                }

                //startActivity(new Intent(MainActivity.this, Quiz1.class)); cuz this -> new View.OnClickListener lol
            }
        });

        // Initialize the register button
        Register= findViewById(R.id.register_button);
        // Set click listener for the register button
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the RegisterActivity when the register button is clicked
                Intent i2=new Intent(getApplicationContext(),Register.class);
                startActivity(i2);

            }
        });
    }
}
