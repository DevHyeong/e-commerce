package org.commerce.user.application;

import lombok.RequiredArgsConstructor;
import org.commerce.user.dto.LoginRequest;
import org.commerce.user.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/join")
    public void join(){

    }

}
