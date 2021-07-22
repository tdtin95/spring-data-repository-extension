package com.tdtin.springdatacommon.controller;

import java.util.List;
import java.util.Map;
import com.tdtin.springdatacommon.dto.UserDto;
import com.tdtin.springdatacommon.entity.User;
import com.tdtin.springdatacommon.entity.UserId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UserApi.USER_PATH)
public interface UserApi {
    String USER_PATH = "/api/users";

    /**
     * Create user endpoint
     * @param tenantId  tenant id
     * @param user user as {@link UserDto}
     * @return ResponseEntity
     */
    @PostMapping
    ResponseEntity<UserId> createUser(@RequestHeader("tenantId") String tenantId, @RequestBody UserDto user);

    /**
     * update user endpoint
     * @param tenantId  tenant id
     * @param user user as {@link UserDto}
     * @return ResponseEntity
     */
    @PutMapping
    ResponseEntity<UserId> updateUser(@RequestHeader("tenantId") String tenantId, @RequestBody UserDto user);

    /**
     * search user endpoint
     * @param tenantId  tenant id
     * @param params query parameters
     * @return ResponseEntity
     */
    @GetMapping
    ResponseEntity<List<User>> search(@RequestHeader("tenantId") String tenantId, @RequestParam Map<String, String> params);

    /**
     * delete user endpoint
     * @param tenantId  tenant id
     * @param id id of user
     * @return ResponseEntity
     */
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteUser(@RequestHeader("tenantId") String tenantId, @PathVariable String id);

}
