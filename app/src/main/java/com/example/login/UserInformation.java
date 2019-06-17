package com.example.login;

public class UserInformation  {

    private String email;
    private String pass;

    public UserInformation(){

    }

    public UserInformation(String email, String  pass) {
        this.email = email;
        this.pass=pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void setPass(String pass)
    {
        this.pass=pass;
    }
    public String getPass()
    {
        return pass;
    }
}
