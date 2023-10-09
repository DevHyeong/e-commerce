package org.commerce.order.api.user.model;

import lombok.Getter;

@Getter
public class User {
    private Long userId;
    private String name;

    public User(){

    }
    public User(Long userId, String name){
        this.userId = userId;
        this.name = name;
    }

}
