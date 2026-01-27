package com.quizc.programmingquizforalllanguages.Repository;

import org.springframework.stereotype.Repository;

@Repository
public class LoginInfos {

    private static final String USERNAME = "Philasande@1402";
    private static final String PASSWORD = "Philasande@1202";

    public boolean isValid(String usernameEntered, String passwordEntered) {
        return USERNAME.equals(usernameEntered) && PASSWORD.equals(passwordEntered);
    }
}
