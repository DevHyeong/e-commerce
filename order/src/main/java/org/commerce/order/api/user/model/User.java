package org.commerce.order.api.user.model;

import lombok.Getter;

@Getter
public class User {
    private Long userId;
    private String email;
    private String nickname;

    public User(){

    }
    public User(Long userId, String email, String nickname){
        this.userId = userId;
        this.email = email;
        this.nickname = nickname;
    }

}
