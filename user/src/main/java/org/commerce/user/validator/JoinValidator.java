package org.commerce.user.validator;

import org.commerce.user.dto.UserDto;
import org.commerce.user.repository.UserRepository;
import org.springframework.util.StringUtils;

import static org.commerce.user.validator.EmailValidator.MESSAGE;

public class JoinValidator {
    private static final String DUPLICATE_EMAIL_MESSAGE = "이미 등록된 이메일입니다.";
    private static final String DUPLICATE_NICKNAME_MESSAGE = "이미 등록된 닉네임입니다.";
    private static final String INVALID_NICKNAME_MESSAGE = "닉네임을 10자리이하로 등록해주세요";

    public static void validate(UserDto userDto, UserRepository userRepository) throws IllegalArgumentException{
        if(!EmailValidator.validate(userDto.getEmail()))
            throw new IllegalArgumentException(MESSAGE);

        if(!PasswordValidator.validate(userDto.getPassword()))
            throw new IllegalArgumentException(PasswordValidator.MESSAGE);

        if(!hasLengthAndUnderLength(userDto.getNickname(), 10))
            throw new IllegalArgumentException(INVALID_NICKNAME_MESSAGE);

        userRepository.findByEmail(userDto.getEmail())
                .ifPresent(e -> {
                    throw new IllegalArgumentException(DUPLICATE_EMAIL_MESSAGE);
                });
        userRepository.findByNickname(userDto.getNickname())
                .ifPresent(e -> {
                    throw new IllegalArgumentException(DUPLICATE_NICKNAME_MESSAGE);
                });
    }
    private static boolean hasLengthAndUnderLength(String str, int length){
        return StringUtils.hasLength(str) && str.length() <= length;
    }
}
