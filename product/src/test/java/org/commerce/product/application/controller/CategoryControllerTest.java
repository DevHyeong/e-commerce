package org.commerce.product.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.commerce.product.dto.CategoryRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateCategory() throws Exception{
        //given
        CategoryRequest request = new CategoryRequest(0L, "식품");
        String content = objectMapper.writeValueAsString(request);

        //when
        ResultActions result = mockMvc.perform(post("/category")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        //then
        result.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.upperId", is(0)))
                .andExpect(jsonPath("$.name", is("식품")));
    }

    @Test
    void testCreateCategoryThrowsCategoryNotFoundException() throws Exception{
        //given
        CategoryRequest request = new CategoryRequest(1L, "쌀/잡곡");
        String content = objectMapper.writeValueAsString(request);

        //when
        ResultActions result = mockMvc.perform(post("/category")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));
        //then
        result.andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$", is("category is not found")));
    }
}