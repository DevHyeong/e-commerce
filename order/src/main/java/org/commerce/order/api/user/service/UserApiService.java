package org.commerce.order.api.user.service;

import org.commerce.order.api.user.model.User;

public interface UserApiService {
    User getUser(Long userId);
}
