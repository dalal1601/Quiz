package com.example.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {



    EditText email,password;
    Button sign;
    TextView Register;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the sign in button
        email = findViewById(R.id.mail);
        password = findViewById(R.id.pswd);
        sign = findViewById(R.id.sign);
        mAuth=FirebaseAuth.getInstance();
        Register = findViewById(R.id.register_button);
        // Set click listener for the sign in button
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail=email.getText().toString();
                String pass=password.getText().toString();

                if(TextUtils.isEmpty(mail)||TextUtils.isEmpty(pass)){
                    Toast.makeText(MainActivity.this,"fill the required fields",Toast.LENGTH_SHORT).show();
                    return;
                }

                signInUser(mail,pass);

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
        private void signInUser(String email, String password) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(MainActivity.this, "Authentication successful.", Toast.LENGTH_SHORT).show();
                                Intent i1 = new Intent(getApplicationContext(), Quiz1.class);
                                startActivity(i1);
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
    }
    
}
