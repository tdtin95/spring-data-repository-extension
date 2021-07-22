package com.tdtin.springdatacommon.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.persistence.criteria.JoinType;
import com.tdtin.springdatacommon.dto.UserDto;
import com.tdtin.springdatacommon.entity.Address;
import com.tdtin.springdatacommon.entity.User;
import com.tdtin.springdatacommon.entity.UserId;
import com.tdtin.springdatacommon.mapping.UserMapper;
import com.tdtin.springdatacommon.repository.UserRepository;
import com.tdtin.springdatacommon.repository.search.BasicOperation;
import com.tdtin.springdatacommon.repository.search.UserSpecification;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper userMapper;

    /**
     * Constructor support Injection
     * @param repository User repository
     * @param userMapper user mapper
     */
    public UserServiceImpl(UserRepository repository, UserMapper userMapper) {
        this.repository = repository;
        this.userMapper = userMapper;
    }

    @Override
    public UserId createUser(String tenantId, UserDto userDto) {
        User user = userMapper.toUser(userDto);
        List<Address> address = user.getAddress();
        if (CollectionUtils.isNotEmpty(address)) {
            address.forEach(item -> item.setUser(user));
        }
        user.setId(UserId.builder().identifier(UUID.randomUUID().toString()).tenantId(tenantId).build());
        return repository.save(user).getId();
    }

    @Override
    public UserId update(String tenantId, UserDto userDto) {
        User user = userMapper.toUser(userDto);
        List<Address> address = user.getAddress();
        if (CollectionUtils.isNotEmpty(address)) {
            address.forEach(item -> item.setUser(user));
        }
        return repository.save(user).getId();
    }

    @Override
    public List<User> search(String tenantId, Map<String, String> params) {
        int offset = Optional.ofNullable(params.get("offset")).map(Integer::valueOf).orElseGet(() -> 0);
        int limit = Optional.ofNullable(params.get("limit")).map(Integer::valueOf).orElseGet(() -> Integer.MAX_VALUE);
        return repository.search(new UserSpecification("address.city", BasicOperation.EQUAL_TO, params.get("city"), JoinType.INNER),
                Pageable.unpaged());

    }
}
