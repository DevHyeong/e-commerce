package org.commerce.user.application;

import lombok.RequiredArgsConstructor;
import org.commerce.user.dto.UserDto;
import org.commerce.user.service.UserService;
import org.commerce.user.validator.EmailValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.commerce.user.validator.EmailValidator.validate;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/join")
    public ResponseEntity<UserDto> join(@RequestBody UserDto userDto){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.join(userDto));
    }

}
