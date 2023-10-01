package com.reserve.restaurantservice.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequiredValidation {

    public boolean validatePhno(String phno){
        String regex = "^(0/91)?[6-9][0-9]{9}$";
        Pattern p = Pattern.compile(regex);
        if(phno == null || phno.length()!=10){
            return false;
        }
        else{
            Matcher m  = p.matcher(phno);
            return m.matches();
        }
    }

    public boolean validateUser(String email)
    {
        String regex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
        Pattern p = Pattern.compile(regex);
        if (email == null) {
            return false;
        }
        Matcher m = p.matcher(email);
        return m.matches();


    }

    public boolean validatePassword(String password,String email)
    {
        String regex = "^(?=.*[0-9])"
                + "(?=.[a-z])(?=.[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";
        Pattern p = Pattern.compile(regex);
        if (password == null ||  password.substring(0,6).equals(email.substring(0,6))) {
            return false;
        }
        Matcher m = p.matcher(password);
        return m.matches();
    }

}
