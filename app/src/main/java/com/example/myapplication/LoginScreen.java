package com.example.myapplication;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginScreen extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        mAuth = FirebaseAuth.getInstance();

        EditText loginEditText = findViewById(R.id.loginEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        Button CreateAccount = findViewById(R.id.createAccountButton);

        CreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginScreen.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        Button loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = loginEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginScreen.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Sign in user with email and password
                mAuth.signInWithEmailAndPassword(email, password)
                     .addOnCompleteListener(LoginScreen.this, new OnCompleteListener<AuthResult>() {
                         @Override
                         public void onComplete(@NonNull Task<AuthResult> task) {
                             if (task.isSuccessful()) {
                                 // Sign in success
                                 FirebaseUser user = mAuth.getCurrentUser();
                                 updateUI(user); // Handle the signed-in user
                             } else {
                                 // If sign in fails, display a message to the user.
                                 Toast.makeText(LoginScreen.this, "Authentication Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                             }
                         }
                     });
            }
        });
    }

    // TODO: Implement updateUI method to handle the signed-in user (e.g., update UI, navigate to the next screen)
    private void updateUI(FirebaseUser user) {
        if (user != null) {
            // Example: Navigate to the main activity
            Intent intent = new Intent(LoginScreen.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
