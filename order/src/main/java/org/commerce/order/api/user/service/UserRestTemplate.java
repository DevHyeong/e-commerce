package org.commerce.order.api.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.commerce.order.api.user.model.User;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Slf4j
@Profile("default")
@Service
@RequiredArgsConstructor
public class UserRestTemplate implements UserApiService{
    @Override
    public User getUser(Long userId) {
        return null;
    }
}
