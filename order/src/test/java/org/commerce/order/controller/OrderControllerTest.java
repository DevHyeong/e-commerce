package org.commerce.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.commerce.order.dto.OrderProductDto;
import org.commerce.order.dto.OrderRequest;
import org.commerce.order.dto.OrdererDto;
import org.commerce.order.entity.Address;
import org.commerce.order.entity.OrderStatus;
import org.commerce.order.entity.PhoneNumber;
import org.commerce.order.entity.Receiver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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
}
