package org.commerce.user.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.commerce.user.dto.LoginRequest;
import org.commerce.user.entity.User;
import org.commerce.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testLoginWhenUserIsNotExistThenIsUnauthorized() throws Exception{
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("asdf123@naver.com");
        loginRequest.setPassword("1234");
        String body = objectMapper.writeValueAsString(loginRequest);

        ResultActions result = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body));
        result.andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testLoginWhenUserIsExistThenSuccess() throws Exception{
        User user = new User("asdf123@naver.com", passwordEncoder.encode("1234"), "생각하는개발자");
        userRepository.save(user);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("asdf123@naver.com");
        loginRequest.setPassword("1234");
        String body = objectMapper.writeValueAsString(loginRequest);

        ResultActions result = mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body));
        result.andDo(print())
                .andExpect(status().isOk());
    }


}