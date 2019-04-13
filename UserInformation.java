package com.example.sunshine.edocx.app;

import android.widget.Toast;

import java.nio.charset.StandardCharsets;


public class UserInformation  {

    private String name;
    private String email;
    private String phone_num;
    private int count;
    private String key;
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public UserInformation(){

        // System.out.print("IN USER");
        // Toast.makeText(this,"in user",Toast.LENGTH_LONG).show();

    }
    UserInformation(String email, String name, String phone_num, int count, byte[] key){
        this.email = email;
        this.name = name;
        this.phone_num = phone_num;
        this.count=count;
        String kk=new String(key, StandardCharsets.UTF_16LE);
        this.key=kk;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}