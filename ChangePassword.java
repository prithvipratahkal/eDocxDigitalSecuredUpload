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
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.sunshine.edocx.app.LoginActivity.email;
import static com.example.sunshine.edocx.app.LoginActivity.password;

public class ChangePassword extends AppCompatActivity {

    UserInformation user;

    FirebaseDatabase reff= FirebaseDatabase.getInstance();
    DatabaseReference mDatabase =reff.getReference("userDB");
    Query query;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Button button = (Button) findViewById(R.id.button);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View focus = null;
                boolean cancel = true;
                //Toast.makeText(register.this,"entry point",Toast.LENGTH_LONG).show();
                EditText edOld = (EditText) findViewById(R.id.oldPass);
                String old = edOld.getText().toString();

                if (!old.equals(password)) {
                    edOld.setError("Enter valid existing password");
                    focus = edOld;
                    cancel = false;
                }
                Pattern p;
                Matcher m;
                EditText edPass = (EditText) findViewById(R.id.newPass);
                final String newpass = edPass.getText().toString();
                p = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,20}$");
                m = p.matcher(newpass);
                if (!m.matches()) {
                    edPass.setError("Password must contain at least one upper case letter, one lower case letter and a digit");
                    focus = edPass;
                    cancel = false;
                }

                EditText edconfirm = (EditText) findViewById(R.id.ConfirmPass);
                String confirm = edconfirm.getText().toString();
                if (!confirm.equals(newpass)) {
                    edconfirm.setError("Doesn't match with new password");
                    focus = edconfirm;
                    cancel = false;
                }

                if (cancel == false) {
                    focus.requestFocus();
                    return;
                }


                final FirebaseUser userAuth = FirebaseAuth.getInstance().getCurrentUser();

// Get auth credentials from the user for re-authentication. The example below shows
// email and password credentials but there are multiple possible providers,
// such as GoogleAuthProvider or FacebookAuthProvider.
                AuthCredential credential = EmailAuthProvider
                        .getCredential(email, old);

// Prompt the user to re-provide their sign-in credentials
                userAuth.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    userAuth.updatePassword(newpass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(ChangePassword.this,"password updated",Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(ChangePassword.this," Error: password not updated",Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                } else {
                                    Toast.makeText(ChangePassword.this, "Error authentication failed",Toast.LENGTH_LONG).show();
                                }
                            }
                        });




             /*   query=mDatabase.orderByChild("email").equalTo(email);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        if (dataSnapshot.exists())
                        {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren())
                            {
                                String temp=snapshot.getKey();
                                mDatabase.child(temp).child("pass").setValue(newpass);
                                Toast.makeText(ChangePassword.this,"Password Change Successful",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(), Leftmenu.class));
                            }

                        }
                        else
                        {
                            Toast.makeText(ChangePassword.this,"Something went wrong",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });*/


    }
});
    }
}