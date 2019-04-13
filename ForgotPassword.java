package com.example.sunshine.edocx.app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

import static com.example.sunshine.edocx.app.register.passr;
import static com.example.sunshine.edocx.app.register.phnum;
import static com.example.sunshine.edocx.app.register.user;


public class ForgotPassword extends AppCompatActivity {
    Button btnGenerateOTP, btnSignIn;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    EditText etPhoneNumber, etOTP;
    String phoneNumber, otp;
    FirebaseAuth auth;
    private String verificationCode;
    private FirebaseAuth mAuth;

    FirebaseDatabase reff= FirebaseDatabase.getInstance();
    DatabaseReference mDatabase =reff.getReference("userDB");
    Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        btnGenerateOTP = findViewById(R.id.otpb);
        btnSignIn = findViewById(R.id.verify);
        // etEmail = findViewById(R.id.email);
        etPhoneNumber = findViewById(R.id.PhNum);
        etOTP = findViewById(R.id.otp);
        mAuth = FirebaseAuth.getInstance();
        StartFirebaseLogin();



        btnGenerateOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //phoneNumber=etPhoneNumber.getText().toString();

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        phnum,                     // Phone number to verify
                        60,                           // Timeout duration
                        TimeUnit.SECONDS,                // Unit of timeout
                        ForgotPassword.this,        // Activity (for callback binding)
                        mCallback);                      // OnVerificationStateChangedCallbacks
            }
        });


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp=etOTP.getText().toString();
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, otp);
                SigninWithPhone(credential);
            }
        });
    }


    private void SigninWithPhone(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            //String userId = mDatabase.push().getKey();
                            //Toast.makeText(register.this, "userID"+userId, Toast.LENGTH_LONG).show();
                            //mDatabase.setValue(userId);
                            // mDatabase.child(userId).setValue(user);

                            mAuth.createUserWithEmailAndPassword(user.getEmail(), passr)
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {

                                            if (task.isSuccessful()) {

                                                mDatabase.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        //progressBar.setVisibility(View.GONE);
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(ForgotPassword.this,"Registration successful",Toast.LENGTH_LONG).show();
                                                            startActivity(new Intent(ForgotPassword.this,LoginActivity.class));
                                                            finish();
                                                            // Toast.makeText(ForgotPassword.this, "Email sent", Toast.LENGTH_LONG).show();
                                                        } else {
                                                            //display a failure message
                                                            // Toast.makeText(ForgotPassword.this, "else part pa", Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                });

                                            } else {
                                                Toast.makeText(ForgotPassword.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });




                        } else {
                            Toast.makeText(ForgotPassword.this,"Incorrect OTP",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    private void StartFirebaseLogin() {

        auth = FirebaseAuth.getInstance();
        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                Toast.makeText(ForgotPassword.this,"verification completed",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(ForgotPassword.this,"verification failed",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationCode = s;
                Toast.makeText(ForgotPassword.this,"OTP sent",Toast.LENGTH_SHORT).show();
            }
        };
    }




}