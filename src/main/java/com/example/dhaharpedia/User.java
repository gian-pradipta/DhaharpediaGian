package com.example.dhaharpedia;

public class User {
    protected static boolean isLogin = false;

    protected static void setIsLogin(boolean t){
        isLogin = t;
        if (t){
            MainMenu.login.setText("Logout");
        }
        else
            MainMenu.login.setText("Login");
    }
}
