package com.example.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    EditText email, password, password1, name; // Add EditText for capturing the user's name
    Button bregister;
    FirebaseAuth MyAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        email = findViewById(R.id.email);
        password = findViewById(R.id.pswd);
        password1 = findViewById(R.id.pswd1);
        name = findViewById(R.id.name); // Initialize EditText for capturing the user's name
        bregister = findViewById(R.id.register);
        MyAuth = FirebaseAuth.getInstance();

        bregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = email.getText().toString();
                String pass = password.getText().toString();
                String pass1 = password1.getText().toString();
                String userName = name.getText().toString(); // Get user's name
                if (TextUtils.isEmpty(mail) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(pass1) || TextUtils.isEmpty(userName)) {
                    Toast.makeText(Register.this, "Fill all the required fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pass.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password length must be at least 6 characters", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!pass.equals(pass1)) {
                    Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }
                SignUp(mail, pass, userName); // Pass the user's name to the SignUp method
            }
        });
    }

    public void SignUp(String mail, String password, final String userName) {
        MyAuth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Registration successful, save user information to Realtime Database
                    FirebaseUser user = MyAuth.getCurrentUser();
                    if (user != null) {
                        String userId = user.getUid();
                        String userEmail = user.getEmail(); // Get user's email
                        // Save the user's information to the Realtime Database
                        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
                        userRef.child("email").setValue(userEmail);
                        userRef.child("name").setValue(userName); // Save user's name
                        Toast.makeText(Register.this, "Registration successful", Toast.LENGTH_SHORT).show();
                        Intent i2 = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i2);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "User not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
