package org.commerce.order.api.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.commerce.order.api.user.model.User;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Profile("test")
@Service
@RequiredArgsConstructor
public class MockUserRestTemplate implements UserApiService{

    private List<User> users;
    @Override
    public User getUser(Long userId) {
        return users.stream()
                .filter(e-> e.getUserId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(""));
    }

    public void setUsers(List<User> users){
        this.users = users;
    }

}
