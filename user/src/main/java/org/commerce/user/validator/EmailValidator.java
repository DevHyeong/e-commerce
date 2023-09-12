package org.commerce.user.validator;

import java.util.regex.Pattern;

public class EmailValidator {
    private static final String REGEX_PATTERN = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    public static final String MESSAGE = "이메일 형식이 맞지 않습니다.";


    public static boolean validate(String email){
        return Pattern.compile(REGEX_PATTERN)
                .matcher(email)
                .matches();
    }

}
