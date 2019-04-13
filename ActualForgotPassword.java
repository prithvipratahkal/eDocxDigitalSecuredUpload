package com.example.sunshine.edocx.app;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ActualForgotPassword extends AppCompatActivity {


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actual_forgot_password);
        Button confirm=(Button)findViewById(R.id.button2);
        mAuth = FirebaseAuth.getInstance();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText edPass = (EditText)findViewById(R.id.mailconfirm);
                String temp= edPass.getText().toString();
                //Toast.makeText(ActualForgotPassword.this,"Email inside",Toast.LENGTH_LONG).show();
                mAuth.sendPasswordResetEmail(temp)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ActualForgotPassword.this,"Email sent",Toast.LENGTH_LONG).show();
                                }
                                else
                                {
                                    Toast.makeText(ActualForgotPassword.this,"else part",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }
}
