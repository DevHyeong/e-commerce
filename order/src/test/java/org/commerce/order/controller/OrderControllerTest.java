package org.commerce.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.commerce.order.api.user.model.User;
import org.commerce.order.api.user.service.MockUserRestTemplate;
import org.commerce.order.dto.OrderProductDto;
import org.commerce.order.dto.OrderRequest;
import org.commerce.order.dto.OrdererDto;
import org.commerce.order.entity.*;
import org.commerce.order.repository.OrderRepository;
import org.commerce.order.api.product.model.Category;
import org.commerce.order.api.product.service.MockProductRestTemplate;
import org.commerce.order.api.product.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderControllerTest {
    private MockMvc mockMvc;
    @Autowired
    public void setMockMvc(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MockProductRestTemplate mockProductRestTemplate;
    @Autowired
    private MockUserRestTemplate mockUserRestTemplate;


    @DisplayName("주문하기")
    @Test
    void testOrderCreate() throws Exception {
        Address address = new Address("부산광역시 남구 대연동", "삼동아파트 105호", "47801");
        Receiver receiver = new Receiver("홍길동", address, new PhoneNumber("010-1234-1234"));
        OrdererDto ordererDto = new OrdererDto(1L, receiver);

        List<OrderProductDto> orderProductDtos = new ArrayList<>();
        orderProductDtos.add(new OrderProductDto(1L, 1500, 3));
        orderProductDtos.add(new OrderProductDto(2L, 500, 5));
        orderProductDtos.add(new OrderProductDto(3L, 2500, 2));
        orderProductDtos.add(new OrderProductDto(4L, 3000, 10));

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrderer(ordererDto);
        orderRequest.setProducts(orderProductDtos);

        String content = objectMapper.writeValueAsString(orderRequest);
        ResultActions result = mockMvc.perform(post("/order/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));
        result.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status", is(OrderStatus.PREPARING_FOR_PRODUCT.name())));
    }

    @Test
    void testOrder() throws Exception{
        Address receiverAddress = new Address("부산광역시 수영구 광안동 123-1", "빌라이름 303호", "45892");
        PhoneNumber receiverPhoneNumber = new PhoneNumber("010-1234-1234");
        Receiver receiver = new Receiver("받는사람", receiverAddress, receiverPhoneNumber);
        Orderer orderer = new Orderer(1L, receiver);

        List<OrderProduct> orderProducts = new ArrayList<>();
        orderProducts.add(new OrderProduct(1L, 18520, 2));
        orderProducts.add(new OrderProduct(2L, 21760, 3));

        Order order = new Order(orderer, OrderStatus.PREPARING_FOR_PRODUCT, orderProducts);
        orderRepository.save(order);

        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1L, 0L, "여성패션"));
        categories.add(new Category(2L, 1L, "의류"));
        categories.add(new Category(3L, 2L, "스커트/치마"));

        List<Category> categories1 = new ArrayList<>();
        categories1.add(new Category(4L, 0L, "주방용품"));
        categories1.add(new Category(5L, 4L, "테이블웨어"));
        categories1.add(new Category(6L, 5L, "그릇/식기"));

        List<Product> products = new ArrayList<>();
        products.add(new Product(1L, "바블링브록 여성용 소프트 모직 치마 바지", categories));
        products.add(new Product(2L, "보울보울 어반데일리 멀티볼", categories1));

        mockProductRestTemplate.setProducts(products);

        List<User> users = new ArrayList<>();
        users.add(new User(1L, "주문자이름"));

        mockUserRestTemplate.setUsers(users);

        ResultActions result = mockMvc.perform(get("/order/1")
                        .contentType(MediaType.APPLICATION_JSON));

        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId", is(1)))
                .andExpect(jsonPath("$.status", is(OrderStatus.PREPARING_FOR_PRODUCT.name())))
                .andExpect(jsonPath("$.orderer.name", is("주문자이름")))
                //.andExpect(jsonPath("$.orderer.", is("")))
                .andExpect(jsonPath("$.receiver.name", is("받는사람")))
                .andExpect(jsonPath("$.receiver.address.addr1", is("부산광역시 수영구 광안동 123-1")))
                .andExpect(jsonPath("$.receiver.address.addr2", is("빌라이름 303호")))
                .andExpect(jsonPath("$.receiver.address.zipCode", is("45892")))
                .andExpect(jsonPath("$.receiver.phoneNumber", is("010-1234-1234")))
                .andExpect(jsonPath("$.products[0].name", is("바블링브록 여성용 소프트 모직 치마 바지")))
                .andExpect(jsonPath("$.products[0].categories[0].name", is("여성패션")))
                .andExpect(jsonPath("$.products[0].categories[1].name", is("의류")))
                .andExpect(jsonPath("$.products[0].categories[2].name", is("스커트/치마")))
                .andExpect(jsonPath("$.products[0].price", is(18520)))
                .andExpect(jsonPath("$.products[0].amount", is(2)))
                .andExpect(jsonPath("$.products[1].name", is("보울보울 어반데일리 멀티볼")))
                .andExpect(jsonPath("$.products[1].categories[0].name", is("주방용품")))
                .andExpect(jsonPath("$.products[1].categories[1].name", is("테이블웨어")))
                .andExpect(jsonPath("$.products[1].categories[2].name", is("그릇/식기")))
                .andExpect(jsonPath("$.products[1].price", is(21760)))
                .andExpect(jsonPath("$.products[1].amount", is(3)));




    }
}
