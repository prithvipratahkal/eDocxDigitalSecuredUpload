package com.example.sunshine.edocx.app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.sunshine.edocx.app.LoginActivity.email;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button change = (Button) findViewById(R.id.button4);
        Button logout = (Button) findViewById(R.id.button5);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                   // Toast.makeText(Settings.this,"Email sent",Toast.LENGTH_LONG);
                                    // Log.d(TAG, "Email sent.");
                                }
                            }
                        });*/
                startActivity(new Intent(getApplicationContext(), ChangePassword.class));

            }

        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

    }
}