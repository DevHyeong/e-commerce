package org.commerce.product.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.commerce.product.dto.ProductRequest;
import org.commerce.product.entity.Category;
import org.commerce.product.repository.CategoryRepository;
import org.commerce.product.service.ProductReadService;
import org.commerce.product.service.ProductWriteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
    @Autowired
    private ProductWriteService productWriteService;

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

    @Test
    void testGetProduct() throws Exception{
        //given
        ProductRequest productRequest = new ProductRequest(3L, "이천상품쌀", 12000, 50);
        categoryRepository.save(new Category(0L, "식품"));
        categoryRepository.save(new Category(1L, "쌀/잡곡"));
        categoryRepository.save(new Category(2L, "백미"));
        productWriteService.createProduct(productRequest);

        //when
        ResultActions result = mockMvc.perform(get("/product/1")
                .contentType(MediaType.APPLICATION_JSON));

        //then
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName", is("이천상품쌀")))
                .andExpect(jsonPath("$.price", is(12000)))
                .andExpect(jsonPath("$.totalAmount", is(50)))
                .andExpect(jsonPath("$.categories[0].name", is("식품")))
                .andExpect(jsonPath("$.categories[1].name", is("쌀/잡곡")))
                .andExpect(jsonPath("$.categories[2].name", is("백미")));
    }

    @Test
    void testGetProducts() throws Exception{
        //given
        categoryRepository.save(new Category(0L, "식품"));
        categoryRepository.save(new Category(1L, "쌀/잡곡"));
        categoryRepository.save(new Category(2L, "백미")); // 3L

        categoryRepository.save(new Category(0L, "주방용품"));
        categoryRepository.save(new Category(4L, "수저/커트러리"));
        categoryRepository.save(new Category(5L, "나이프")); // 6L

        categoryRepository.save(new Category(0L, "스포츠/레저"));
        categoryRepository.save(new Category(7L, "낚시"));
        categoryRepository.save(new Category(8L, "낚싯대")); // 9L

        createProduct(3L, "이천쌀", 12000, 10);
        createProduct(3L, "대구쌀", 28000, 5);
        createProduct(3L, "진상미", 12000, 10);
        createProduct(3L, "금쌀", 12000, 10);
        createProduct(6L, "대추나무 버터 나이프", 2600, 25);
        createProduct(6L, "버터나이프 크림", 1200, 50);
        createProduct(6L, "치즈커터", 14000, 15);
        createProduct(6L, "치즈나이프", 15000, 5);
        createProduct(9L, "어린이 낚시", 22000, 10);
        createProduct(9L, "낚시대 문어", 16000, 5);
        createProduct(9L, "거치대", 32000, 15);
        createProduct(9L, "바다투어낚씨대", 8000, 20);

        List<Long> ids = Arrays.asList(1L, 2L, 3L, 4L);
        String content = objectMapper.writeValueAsString(ids);

        //when
        ResultActions result = mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        //then
        result.andDo(print())
                .andExpect(status().isOk());
    }

    private void createProduct(Long categoryId, String name, int price, int totalAmount){
        ProductRequest request = new ProductRequest(categoryId, name, price, totalAmount);
        productWriteService.createProduct(request);
    }
}
