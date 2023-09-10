package org.commerce.user.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.commerce.user.dto.LoginRequest;
import org.commerce.user.dto.UserDto;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
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
                .andExpect(status().isOk())
                .andExpect(header().exists("access_token"));
    }
    @Test
    void testJoinWhenEmailFormatIsIncorrectThenIsBadRequest() throws Exception{
        UserDto userDto = new UserDto();
        userDto.setEmail("wogud19flkfkj");
        userDto.setPassword("asdf1234!@");
        userDto.setNickname("생각하는개발자");
        String body = objectMapper.writeValueAsString(userDto);

        ResultActions result = mockMvc.perform(post("/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body));
        result.andDo(print())
                .andExpect(status().isBadRequest());
    }
    @Test
    void testJoinWhenEmailAlreadyExistsThenIsBadRequest() throws Exception{
        User user = new User("wogud19@naver.com", "asdf1234!@", "행복하자");
        userRepository.save(user);

        UserDto userDto = new UserDto();
        userDto.setEmail("wogud19@naver.com");
        userDto.setPassword("asdf1234!@");
        userDto.setNickname("생각하는개발자");
        String body = objectMapper.writeValueAsString(userDto);

        ResultActions result = mockMvc.perform(post("/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body));
        result.andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void testJoinWhenPasswordFormatIsIncorrectThenIsBadRequest() throws Exception{
        UserDto userDto = new UserDto();
        userDto.setEmail("wogud19@naver.com");
        userDto.setPassword("1234");
        userDto.setNickname("생각하는개발자");
        String body = objectMapper.writeValueAsString(userDto);

        ResultActions result = mockMvc.perform(post("/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body));
        result.andDo(print())
                .andExpect(status().isBadRequest());

    }
    @Test
    void testJoinWhenNicknameAlreadyExistsThenIsBadRequest() throws Exception{
        User user = new User("wog12@gmail.com", "asdf1234!@", "생각하는 개발자");
        userRepository.save(user);

        UserDto userDto = new UserDto();
        userDto.setEmail("wogud19@naver.com");
        userDto.setPassword("asdf1234!@");
        userDto.setNickname("생각하는개발자");
        String body = objectMapper.writeValueAsString(userDto);

        ResultActions result = mockMvc.perform(post("/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body));
        result.andDo(print())
                .andExpect(status().isBadRequest());
    }
}