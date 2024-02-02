package org.commerce.user.security.service;

import lombok.RequiredArgsConstructor;
import org.commerce.user.security.model.SecuredUser;
import org.commerce.user.entity.User;
import org.commerce.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("%s 는 존재하지 않는 이메일입니다.", username)));
        return new SecuredUser(user.getId(), user.getEmail(), user.getPassword(), new ArrayList<>(),
                true, true, true, true);
    }
}
