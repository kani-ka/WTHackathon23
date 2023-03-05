package com.example.roomeaze;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;

public class front extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView createAccountTextView;
    private TextView forgotPasswordTextView;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front);
        FirebaseApp.initializeApp(this);
        // Initialize Firebase Storage
        FirebaseStorage storage = FirebaseStorage.getInstance();

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        createAccountTextView = findViewById(R.id.createAccountTextView);
        forgotPasswordTextView = findViewById(R.id.forgotPasswordTextView);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // check if username and password are not empty
                if (!username.isEmpty() && !password.isEmpty()) {
                    // authenticate user using Firebase authentication
                    mAuth.signInWithEmailAndPassword(username, password)
                            .addOnCompleteListener(front.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(front.this, "Login successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(front.this, services.class);
                                        startActivity(intent);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(front.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(front.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        createAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(front.this, REGISTER.class);
                startActivity(intent);
            }
        });

        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim(); // remove any leading/trailing whitespace

                if (!username.isEmpty()) {
                    mAuth.sendPasswordResetEmail(username)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // Password reset email sent successfully
                                        Toast.makeText(front.this, "Password reset link sent to your email", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // Password reset email failed to send
                                        Toast.makeText(front.this, "Failed to send password reset link", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    // No email entered
                    Toast.makeText(front.this, "Please enter your email to reset your password", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }}




