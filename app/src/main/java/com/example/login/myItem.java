package com.example.login;

import android.location.Location;
import android.media.Image;

import java.io.Serializable;

public class myItem implements Serializable {
    //    Image myImage;
    String nameFood;
    String price;
    String Checkin;
    String Location;
    String phoneNumber;
    //friend attribute
    public myItem()//defaultConstructor
    {
        nameFood="Change Food Name Here";
        phoneNumber="Enter Phone Number";
        Location="Change Location Here";
    }
    public myItem(String nameFood,String location,String phoneNumber,String Checkin,String price)
    {
        this.nameFood=nameFood;
        this.phoneNumber=phoneNumber;
        this.Location=location;
        this.Checkin=Checkin;
        this.price=price;
    }
    public myItem(myItem myItemIn)
    {
        nameFood=myItemIn.nameFood;
        Location=myItemIn.Location;
        phoneNumber=myItemIn.phoneNumber;
        price=myItemIn.price;
        Checkin=myItemIn.Checkin;
    }
    public void set(String checkin,String name,String phone,String price,String location)
    {
        this.nameFood=name;
        this.Checkin=checkin;
        this.phoneNumber=phone;
        this.price=price;
        this.Location=location;
    }

    public void setNameFood(String nameFood)
    {
        this.nameFood=nameFood;
    }

    public void setPhonenNumber(String phoneNumber)
    {
        this.phoneNumber=phoneNumber;
    }
}
