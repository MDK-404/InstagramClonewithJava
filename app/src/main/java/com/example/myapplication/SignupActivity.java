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

public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        EditText usernameEditText = findViewById(R.id.signupUsernameEditText);
        EditText emailEditText = findViewById(R.id.signupEmailEditText);
        EditText passwordEditText = findViewById(R.id.signupPasswordEditText);
        Button signupButton = findViewById(R.id.signupButton);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(SignupActivity.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Register user with email and password
                mAuth.createUserWithEmailAndPassword(email, password)
                     .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                         @Override
                         public void onComplete(@NonNull Task<AuthResult> task) {
                             if (task.isSuccessful()) {
                                 // Registration success
                                 FirebaseUser user = mAuth.getCurrentUser();
                                 updateUI(user); // Handle the signed-in user
                             } else {
                                 // If registration fails, display a message to the user.
                                 Toast.makeText(SignupActivity.this, "Authentication Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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
            Intent intent = new Intent(SignupActivity.this, LoginScreen.class);
            startActivity(intent);
            finish();
        }
    }
}
