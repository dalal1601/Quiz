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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.FirebaseApp;

public class Register extends AppCompatActivity {
    EditText email,password,password1;
    Button bregister;
    FirebaseAuth MyAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        email=findViewById(R.id.email);
        password=findViewById(R.id.pswd);
        password1=findViewById(R.id.pswd1);
        bregister=findViewById(R.id.register);
        MyAuth=FirebaseAuth.getInstance();

        bregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail=email.getText().toString();
                String pass=password.getText().toString();
                String pass1=password1.getText().toString();
                if(TextUtils.isEmpty(mail)||TextUtils.isEmpty(pass)||TextUtils.isEmpty(pass1)){
                    Toast.makeText(Register.this,"fill the required fields",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pass.length()<6){
                    Toast.makeText(getApplicationContext(),"nbre de car inf a 6",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!pass.equals(pass1)){
                    Toast.makeText(getApplicationContext(),"verifier mdp",Toast.LENGTH_SHORT).show();
                    return;
                }
                SignUp(mail,pass);

            }
        });

    }
    public void SignUp(String mail,String password){
        MyAuth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(Register.this,"enregistrement reussie",Toast.LENGTH_SHORT).show();
                    Intent i2 = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i2);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"enregistrement echoué"+
                            task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }

            }
        });
    }

}