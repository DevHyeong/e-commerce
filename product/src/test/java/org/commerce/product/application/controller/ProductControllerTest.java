package org.commerce.product.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.commerce.product.dto.ProductRequest;
import org.commerce.product.entity.Category;
import org.commerce.product.repository.CategoryRepository;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void testCreateProduct() throws Exception{
        //given
        categoryRepository.save(new Category(0L, "식품"));
        categoryRepository.save(new Category(1L, "쌀/잡곡"));
        categoryRepository.save(new Category(2L, "백미"));
        ProductRequest request = new ProductRequest(3L, "쌀", 12000, 100);
        String content = objectMapper.writeValueAsString(request);

        //when
        ResultActions result = mockMvc.perform(post("/product")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        //then
        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.productName", is("쌀")))
                .andExpect(jsonPath("$.price", is(12000)))
                .andExpect(jsonPath("$.totalAmount", is(100)))
                .andExpect(jsonPath("$.categories[0].name", is("식품")))
                .andExpect(jsonPath("$.categories[1].name", is("쌀/잡곡")))
                .andExpect(jsonPath("$.categories[2].name", is("백미")));
    }

    @Test
    void testCreateProductThrowsIllegalArgumentException() throws Exception{
        //given
        categoryRepository.save(new Category(0L, "식품"));
        categoryRepository.save(new Category(1L, "쌀/잡곡"));
        categoryRepository.save(new Category(2L, "백미"));
        ProductRequest request = new ProductRequest(1L, "쌀", 12000, 100);
        String content = objectMapper.writeValueAsString(request);

        //when
        ResultActions result = mockMvc.perform(post("/product")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        //then
        result.andExpect(status().isForbidden());
    }
}
