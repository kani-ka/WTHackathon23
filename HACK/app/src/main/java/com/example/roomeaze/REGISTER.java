package com.example.roomeaze;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class REGISTER extends AppCompatActivity {

    private TextInputLayout tilName, tilEmail, tilPassword, tilConfirmPassword;
    private TextInputEditText etName, etEmail, etPassword, etConfirmPassword;
    private ImageView imageView;
    private Button btnSignUp;
    private FirebaseAuth mAuth;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tilName = findViewById(R.id.til_name);
        tilEmail = findViewById(R.id.til_email);
        tilPassword = findViewById(R.id.til_password);
        tilConfirmPassword = findViewById(R.id.til_confirm_password);

        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);

        imageView = findViewById(R.id.imageView);
        mAuth = FirebaseAuth.getInstance();
        btnSignUp = findViewById(R.id.btn_sign_up);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String confirmPassword = etConfirmPassword.getText().toString().trim();

                if (name.isEmpty()) {
                    tilName.setError("Name is required");
                    tilName.requestFocus();
                    return;
                } else {
                    tilName.setErrorEnabled(false);
                }

                if (email.isEmpty()) {
                    tilEmail.setError("Email is required");
                    tilEmail.requestFocus();
                    return;
                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    tilEmail.setError("Please enter a valid email address");
                    tilEmail.requestFocus();
                    return;
                } else {
                    tilEmail.setErrorEnabled(false);
                }

                if (password.isEmpty()) {
                    tilPassword.setError("Password is required");
                    tilPassword.requestFocus();
                    return;
                } else if (password.length() < 6) {
                    tilPassword.setError("Password should be at least 6 characters long");
                    tilPassword.requestFocus();
                    return;
                } else {
                    tilPassword.setErrorEnabled(false);
                }

                if (confirmPassword.isEmpty()) {
                    tilConfirmPassword.setError("Please confirm your password");
                    tilConfirmPassword.requestFocus();
                    return;
                } else if (!confirmPassword.equals(password)) {
                    tilConfirmPassword.setError("Passwords do not match");
                    tilConfirmPassword.requestFocus();
                    return;
                } else {
                    tilConfirmPassword.setErrorEnabled(false);
                }

                Intent intent = new Intent(REGISTER.this, services.class);
                startActivity(intent);

                // Show a toast message to indicate successful account creation
                Toast.makeText(getApplicationContext(), "Account created successfully", Toast.LENGTH_SHORT).show();
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(REGISTER.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(getApplicationContext(), "Account created successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(REGISTER.this, services.class);
                                    startActivity(intent);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(getApplicationContext(), "Authentication failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
    }
}