package com.example.login;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {
    private  String email;
    private String uid;
    private String pass;
    private ArrayList<myItem> listUserItem;
    public User()
    {

    }
    public User(String email,String uid,String pass,ArrayList<myItem> listUserItem)
    {
        this.email=email;
        this.uid=uid;
        this.pass=pass;
        this.listUserItem=listUserItem;
    }
    public String getEmail(){return email;}
    public String getUid(){return uid;}
    public String getPass(){return pass;}
    public ArrayList<myItem> getListUserItem(){return listUserItem;}
    public void setList(ArrayList<myItem> listUserItem){this.listUserItem=listUserItem;}
}
