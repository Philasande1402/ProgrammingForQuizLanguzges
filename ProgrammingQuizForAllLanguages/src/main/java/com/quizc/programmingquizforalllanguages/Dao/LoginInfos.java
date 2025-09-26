package com.quizc.programmingquizforalllanguages.Dao;

import lombok.Data;

@Data
public class LoginInfos {
    private final static String USERNAME = "Philasande@1402";
    private final static String PASSWORD = "Philasande@1202";

    public boolean isValid(String usernameEntered,String passwordEntered){
        boolean valid = false;
        if(usernameEntered.equals(USERNAME) && passwordEntered.equals(PASSWORD)){
            valid = true;
        }

        return valid;
    }
}
