package org.commerce.user.validator;

import java.util.regex.Pattern;

public class EmailValidator {
    private static final String REGEX_PATTERN = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    public static boolean validate(String email){
        return Pattern.compile(REGEX_PATTERN)
                .matcher(email)
                .matches();
    }

}
