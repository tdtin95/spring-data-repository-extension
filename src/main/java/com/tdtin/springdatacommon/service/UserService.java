package com.tdtin.springdatacommon.service;

import java.util.List;
import java.util.Map;
import com.tdtin.springdatacommon.dto.UserDto;
import com.tdtin.springdatacommon.entity.User;
import com.tdtin.springdatacommon.entity.UserId;

public interface UserService {

    /**
     * Create user endpoint
     * @param tenantId  tenant id
     * @param user user as {@link UserDto}
     * @return ResponseEntity
     */
    UserId createUser(String tenantId, UserDto user);

    /**
     * update user endpoint
     * @param tenantId  tenant id
     * @param user user as {@link UserDto}
     * @return ResponseEntity
     */
    UserId update(String tenantId, UserDto user);

    /**
     * search user endpoint
     * @param tenantId  tenant id
     * @param params query parameters
     * @return ResponseEntity
     */
    List<User> search(String tenantId, Map<String, String> params);
}
