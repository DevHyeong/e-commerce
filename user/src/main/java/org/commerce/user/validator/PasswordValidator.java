package org.commerce.user.validator;

import java.util.regex.Pattern;

public class PasswordValidator {
    private static final String REGEX_PATTERN = "^(?=.*[A-Za-z])(?=.*[!@#$%^&*()_+{}\\[\\]:;<>,.?~\\\\-]).{8,}$";
    public static final String MESSAGE = "비밀번호 형식이 맞지 않습니다.(영대소문자,특수문자포함 8자리이상)";
    public static boolean validate(String password){
        return Pattern.compile(REGEX_PATTERN)
                .matcher(password)
                .matches();
    }
}
