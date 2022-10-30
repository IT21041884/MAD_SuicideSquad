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
import android.util.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.barista.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class SignUpActivity extends AppCompatActivity {

    TextView txtSignIn;
    EditText edtFullName, edtEmail, edtMobile, edtPassword, edtConfirmPassword;
    ProgressBar progressBar;
    Button btnSignUp;
    String txtFullName, txtEmail, txtMobile, txtPassword, txtConfirmPassword;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    DBHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        txtSignIn = findViewById(R.id.txtSignIn);
        edtFullName = findViewById(R.id.edtSignUpFullName);
        edtEmail = findViewById(R.id.edtSignUpEmail);
        edtMobile = findViewById(R.id.edtSignUpMobile);
        edtPassword = findViewById(R.id.edtSignUpPassword);
        edtConfirmPassword = findViewById(R.id.edtSignUpConfirmPassword);
        progressBar = findViewById(R.id.signUpProgressBar);
        btnSignUp = findViewById(R.id.btnSignUp);

        myDB = new DBHelper(this);

        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtFullName = edtFullName.getText().toString();
                txtEmail = edtEmail.getText().toString();
                txtMobile = edtMobile.getText().toString();
                txtPassword = edtPassword.getText().toString();
                txtConfirmPassword = edtConfirmPassword.getText().toString();

                if (!TextUtils.isEmpty(txtFullName)) {
                    if (!TextUtils.isEmpty(txtEmail)) {
                        if (txtEmail.matches(emailPattern)) {
                            if (!TextUtils.isEmpty(txtMobile)) {
                                if (txtMobile.length() == 9) {
                                    if (!TextUtils.isEmpty(txtPassword)) {
                                        if (!TextUtils.isEmpty(txtConfirmPassword)) {
                                            if (txtConfirmPassword.equals(txtPassword)) {
                                                SignUpUser();
                                            } else {
                                                edtConfirmPassword.setError("Confirm Password and Password should be same.");
                                            }
                                        } else {
                                            edtConfirmPassword.setError("Confirm Password Field can't be empty");
                                        }
                                    } else {
                                        edtPassword.setError("Password Field can't be empty");
                                    }
                                } else {
                                    edtMobile.setError("Enter a valid Mobile");
                                }
                            } else {
                                edtMobile.setError("Mobile Number Field can't be empty");
                            }
                        } else {
                            edtEmail.setError("Enter a valid Email Address");
                        }
                    } else {
                        edtEmail.setError("Email Field can't be empty");
                    }
                } else {
                    edtFullName.setError("Full Name Field can't be empty");
                }


            }
        });
    }

    private void SignUpUser() {
        progressBar.setVisibility(View.VISIBLE);
        btnSignUp.setVisibility(View.INVISIBLE);

        Boolean result = myDB.checkusernamePassword(txtEmail, txtPassword);
        if(result = true){
            Toast.makeText(SignUpActivity.this, "Sign Up Successful !", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else{
            Toast.makeText(SignUpActivity.this, "Invalid Credentials !", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.INVISIBLE);
            btnSignUp.setVisibility(View.VISIBLE);
        }
    }
}