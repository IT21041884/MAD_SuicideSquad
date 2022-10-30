package com.example.barista;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;



public class LoginActivity extends AppCompatActivity {

    TextView txtSignUp;
    EditText edtEmail, edtPassword;
    ProgressBar progressBar;
    Button btnSignIn;
    String txtEmail, txtPassword;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    DBHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtSignUp = findViewById(R.id.txtSignUp);
        edtEmail = findViewById(R.id.edtSignInEmail);
        edtPassword = findViewById(R.id.edtSignInPassword);
        btnSignIn = findViewById(R.id.btnSignIn);

        myDB = new DBHelper(this);

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtEmail = edtEmail.getText().toString().trim();
                txtPassword = edtPassword.getText().toString().trim();

                if (!TextUtils.isEmpty(txtEmail)) {
                    if (txtEmail.matches(emailPattern)) {
                        if (!TextUtils.isEmpty(txtPassword)) {
                            SignInUser();
                        } else {
                            edtPassword.setError("Password Field can't be empty");
                        }
                    } else {
                        edtEmail.setError("Enter a valid Email Address");
                    }
                } else {
                    edtEmail.setError("Email Field can't be empty");
                }
            }
        });
    }

    private void SignInUser() {
        progressBar.setVisibility(View.VISIBLE);
        btnSignIn.setVisibility(View.INVISIBLE);

        Boolean result = myDB.checkusername(txtEmail);
        if (result = true){
                Toast.makeText(LoginActivity.this, "Login Successful !", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
            Toast.makeText(LoginActivity.this, "Invalid Credentials !", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.INVISIBLE);
            btnSignIn.setVisibility(View.VISIBLE);
        }
    }
}
