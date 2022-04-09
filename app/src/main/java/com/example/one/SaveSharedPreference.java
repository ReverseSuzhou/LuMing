package com.example.one;

public class SaveSharedPreference {
    private static String username=null;
    private static String password=null;
    private static String phone=null;

    public void setUsername (String username) {
        this.username = username;
    }

    public void setPassword (String password) {
        this.password = password;
    }

    public void setPhone (String phone) {
        this.phone = phone;
    }

    public String getUsername () { return username; }
    public String getPassword () { return password; }
    public String getPhone () { return phone; }
}
