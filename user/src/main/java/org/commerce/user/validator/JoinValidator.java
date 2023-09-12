package org.commerce.user.validator;

import org.commerce.user.dto.UserDto;

import static org.commerce.user.validator.EmailValidator.MESSAGE;
import static org.commerce.user.validator.EmailValidator.validate;

public class JoinValidator {

    public static void validate(UserDto userDto) throws IllegalArgumentException{
        if(!EmailValidator.validate(userDto.getEmail())){
            throw new IllegalArgumentException(MESSAGE);
        }

        if(!PasswordValidator.validate(userDto.getPassword()))
            throw new IllegalArgumentException(PasswordValidator.MESSAGE);
    }
}
