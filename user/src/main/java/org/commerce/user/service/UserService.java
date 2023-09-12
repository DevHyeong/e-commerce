package org.commerce.user.service;

import lombok.RequiredArgsConstructor;
import org.commerce.user.dto.LoginRequest;
import org.commerce.user.dto.UserDto;
import org.commerce.user.entity.User;
import org.commerce.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static org.commerce.user.validator.JoinValidator.validate;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDto join(UserDto userDto){
        validate(userDto);
        User user = new User(userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()), userDto.getNickname());
        User result = userRepository.save(user);
        return toDto(result);
    }

    private UserDto toDto(User user){
        return new UserDto(user.getEmail(),
                user.getPassword(),
                user.getNickname());
    }

}
