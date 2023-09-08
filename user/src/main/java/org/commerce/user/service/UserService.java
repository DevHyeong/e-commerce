package org.commerce.user.service;

import lombok.RequiredArgsConstructor;
import org.commerce.user.dto.LoginRequest;
import org.commerce.user.entity.User;
import org.commerce.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

}
