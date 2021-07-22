package com.tdtin.springdatacommon.controller;

import java.util.List;
import java.util.Map;
import com.tdtin.springdatacommon.dto.UserDto;
import com.tdtin.springdatacommon.entity.User;
import com.tdtin.springdatacommon.entity.UserId;
import com.tdtin.springdatacommon.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements UserApi {

    private final UserService service;

    /**
     * Constructor
     * @param service {@link UserService}
     */
    public UserController(UserService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<UserId> createUser(String tenantId, UserDto user) {
        return ResponseEntity.ok().body(service.createUser(tenantId, user));
    }

    @Override
    public ResponseEntity<UserId> updateUser(String tenantId, UserDto user) {
        return ResponseEntity.ok().body(service.update(tenantId, user));
    }

    @Override
    public ResponseEntity<List<User>> search(String tenantId, Map<String, String> params) {
        return ResponseEntity.ok().body(service.search(tenantId, params));
    }

    @Override
    public ResponseEntity<Void> deleteUser(String tenantId, String id) {
        return null;
    }
}
