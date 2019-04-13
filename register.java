package com.example.sunshine.edocx.app;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import it.unisa.dia.gas.plaf.jpbc.util.io.Base64;


public class register extends AppCompatActivity {
    static UserInformation user;
    String emailr;
    static String passr;
    String name;
    static String phnum;
    TrustedAuthority ta;
    FirebaseDatabase reff= FirebaseDatabase.getInstance();
    DatabaseReference mDatabase =reff.getReference("userDB");
    //Firebase ff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_register3);
        final Button button=findViewById(R.id.button);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View focus=null;
                boolean cancel=true;
                //Toast.makeText(register.this,"entry point",Toast.LENGTH_LONG).show();
                EditText edEmail = (EditText)findViewById(R.id.Email);
                emailr= edEmail.getText().toString();
                Pattern p;
                p = Pattern.compile("^[a-z0-9]+@[a-z]+\\.[a-z]+$");
                Matcher m = p.matcher(emailr);
                if(!m.matches()){
                    edEmail.setError("Invalid email");
                    focus=edEmail;
                    cancel=false;
                }
                EditText  edPh = (EditText)findViewById(R.id.PhNum);
                phnum= edPh.getText().toString();
                p=Pattern.compile("\\+[0-9]{12}");
                // p=Pattern.compile("[0-9]{10}");
                m = p.matcher(phnum);
                if(!m.matches()){
                    edPh.setError("Invalid phone number");
                    focus=edPh;
                    cancel=false;
                }


                EditText edName = (EditText)findViewById(R.id.Name);
                name= edName.getText().toString();
                p=Pattern.compile("^[A-Za-z\\s]+$");
                m = p.matcher(name);
                if(!m.matches()){
                    edName.setError("Invalid name");
                    focus=edName;
                    cancel=false;
                }


                EditText  edPass = (EditText)findViewById(R.id.Password);
                passr= edPass.getText().toString();
                p=Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,20}$");
                m = p.matcher(passr);
                if(passr.length()<6)
                {
                    edPass.setError("Password must contain at least 6 characters");
                    focus=edPass;
                    cancel=false;
                }
                if(!m.matches()){
                    edPass.setError("Password must contain at least one upper case letter, one lower case letter and a digit");
                    focus=edPass;
                    cancel=false;
                }



                EditText edCon = (EditText)findViewById(R.id.ConfirmPass);
                String cPass= edCon.getText().toString();
                if(!cPass.equals(passr))
                {
                    edCon.setError("Password must match");
                    focus=edCon;
                    cancel=false;
                }
                if(cancel==false)
                {
                    focus.requestFocus();
                    return;
                }
                /*SecureRandom secureRandom = new SecureRandom();
                byte[] key = new byte[16];
                secureRandom.nextBytes(key);*/
                //Random key1 = new Random();
               // long key = key1.nextInt(65536);
               // key.nextInt();
                //Pairing pairing = PairingFactory.getPairing("params/a.properties");
                //PairingFactory.getInstance().setUsePBCWhenPossible(true);
                //int degree = pairing.getDegree();

                /* Return Zr */
                //Field Zr = pairing.getZr();
                //Element key = Zr.newRandomElement();
                //TrustedAuthority ta1 = new TrustedAuthority();
                //Toast.makeText(register.this, "after user1", Toast.LENGTH_SHORT).show();

                //String key = ta1.generation();
                //Toast.makeText(register.this, "after user", Toast.LENGTH_SHORT).show();
                //String temp=ta.encryption(key);
                //pass temp as key
                //String key = "hfwjgfjhw";
                Random randomno = new Random();
                byte[] key = new byte[16];
                randomno.nextBytes(key);
                user = new UserInformation(emailr,name,phnum,0, key);
                /*try{
                    ta = new TrustedAuthority();

              //  Toast.makeText(register.this, "after init", Toast.LENGTH_SHORT).show();
                Actual ac = new Actual();
                Toast.makeText(register.this, "before setup", Toast.LENGTH_SHORT).show();

                ta.setup(ac);
                Toast.makeText(register.this, "after setup", Toast.LENGTH_SHORT).show();
               ta.keyGen(ac);
                Toast.makeText(register.this, "after keygen", Toast.LENGTH_SHORT).show();
               // mainClass ma = new mainClass();
                EncryptedKey k1 = mainClass.EncryptionFunction(ac,key);
                Toast.makeText(register.this, "after encryption", Toast.LENGTH_SHORT).show();
                }catch(Exception e){
                    Toast.makeText(register.this, e.toString(), Toast.LENGTH_LONG).show();
                }*/
//decryption time
                //String plaintext=ta.decryption(key);
                //use plaintext in decryption of files

                //        ff= new Firebase("https://edocx-1a864.firebaseio.com/userDB");
                //Toast.makeText(register.this, "before user", Toast.LENGTH_LONG).show();

                //getValues();
                //Toast.makeText(register.this, "after getvalues", Toast.LENGTH_LONG).show();
                //DatabaseReference ref =  mDatabase.getRef("userDB");

                // mDatabase.child(userId).setValue(user);
                // DatabaseReference ref = mDatabase.child("userDB").push();
                // Toast.makeText(register.this, "Registration Successful", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), ForgotPassword.class));



            }
        });
    }
/*private void getValues()
{
    Toast.makeText(register.this, "inside getValues", Toast.LENGTH_LONG).show();
    user.setEmail(email);
    user.setName(name);
    user.setPass(pass);
    user.setPhone_num(phnum);
}*/
}