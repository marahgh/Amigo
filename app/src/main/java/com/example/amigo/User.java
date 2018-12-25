package com.example.amigo;

import java.util.HashMap;

public class User {

    public String userName;
    public  String email;
    public String password;
    public HashMap<String,String> favContacts;
    public HashMap<String,Medicine> myMedicine;



    public User() {
    }

    public User(String userName, String email, String password, HashMap<String, String> favContacts) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.favContacts = favContacts;
        this.myMedicine = new HashMap<>();
        this.myMedicine.put("none",new Medicine());

    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public HashMap<String, String> getFavContacts() {
        return favContacts;
    }

    public String getPassword() {
        return password;
    }

    public HashMap<String, Medicine> getMyMedicine() {
        return myMedicine;
    }
};
