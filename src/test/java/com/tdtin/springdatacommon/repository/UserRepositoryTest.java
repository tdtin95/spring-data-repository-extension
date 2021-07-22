package com.tdtin.springdatacommon.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.persistence.criteria.JoinType;
import com.github.javafaker.Faker;
import com.tdtin.springdatacommon.entity.Address;
import com.tdtin.springdatacommon.entity.User;
import com.tdtin.springdatacommon.entity.UserId;
import com.tdtin.springdatacommon.repository.search.BasicOperation;
import com.tdtin.springdatacommon.repository.search.ComparableOperation;
import com.tdtin.springdatacommon.repository.search.UserSpecification;
import com.tdtin.springdatacommon.repository.search.WildCardOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootTest
@EnableTransactionManagement
public class UserRepositoryTest {

    private static final OffsetDateTime MAX_DAY = OffsetDateTime.of(9999, 12, 12, 12, 0, 0, 0, ZoneOffset.UTC);
    private UserRepository repo;

    /**
     * Clean up
     */
    @AfterEach
    public void tearDown() {
        repo.deleteAll();
    }

    /**
     * Query by equalTo
     */
    @Test
    public void search_shouldSearchEqual() {
        final List<User> users = createUser(3);
        repo.saveAll(users);
        final String city = users.get(0).getAddress().get(0).getCity();
        List<User> foundUsers = repo.search(new UserSpecification("address.city", BasicOperation.EQUAL_TO, city, JoinType.INNER), Pageable.unpaged());
        assertNotNull(foundUsers.get(0));
    }

    /**
     * Search with notEqualTo operation
     */
    @Test
    public void search_shouldSearchNotEqualTo() {
        final List<User> users = createUser(3);
        repo.saveAll(users);
        final String city = users.get(0).getAddress().get(0).getCity();
        List<User> foundUsers = repo.search(new UserSpecification("address.city", BasicOperation.NOT_EQUAL_TO, city, JoinType.INNER), Pageable.unpaged());
        assertEquals(2, foundUsers.size());
    }

    /**
     * Search with IN operation
     */
    @Test
    public void search_shouldSearchWith_IN() {
        final List<User> users = createUser(3);
        repo.saveAll(users);

        List<String> cities = users.stream().map(item -> item.getAddress().get(0).getCity()).collect(Collectors.toList());
        cities.remove(0);

        List<User> foundUsers = repo.search(new UserSpecification("address.city", BasicOperation.IN, cities, JoinType.INNER), Pageable.unpaged());
        assertEquals(2, foundUsers.size());
    }

    /**
     * Search with LESS THAN operation
     */
    @Test
    public void search_shouldSearchWith_lessThan() {
        final List<User> users = createUser(2);
        users.get(0).setValidFrom(MAX_DAY);
        repo.saveAll(users);

        List<User> foundUsers = repo.search(new UserSpecification("validFrom", ComparableOperation.LESS_THAN, MAX_DAY), Pageable.unpaged());
        assertEquals(1, foundUsers.size());
    }

    /**
     * Search with LESS THAN or Equal To operation
     */
    @Test
    public void search_shouldSearchWith_lessThanOrEqualTo() {
        final List<User> users = createUser(2);
        users.get(0).setValidFrom(MAX_DAY);
        repo.saveAll(users);

        List<User> foundUsers = repo.search(new UserSpecification("validFrom", ComparableOperation.LESS_THAN_OR_EQUAL_TO, MAX_DAY), Pageable.unpaged());
        assertEquals(2, foundUsers.size());
    }

    /**
     * Search with GREATER THAN operation
     */
    @Test
    public void search_shouldSearchWith_greaterThan() {
        final List<User> users = createUser(2);
        users.get(0).setValidFrom(MAX_DAY);
        repo.saveAll(users);

        List<User> foundUsers = repo.search(new UserSpecification("validFrom", ComparableOperation.GREATER_THAN, OffsetDateTime.now()), Pageable.unpaged());
        assertEquals(1, foundUsers.size());
    }

    /**
     * Search with GREATER THAN or EqualTo operation
     */
    @Test
    public void search_shouldSearchWith_greaterThanOrEqualTo() {
        final List<User> users = createUser(3);
        users.get(0).setValidFrom(MAX_DAY);
        users.get(1).setValidFrom(MAX_DAY);
        repo.saveAll(users);

        List<User> foundUsers = repo.search(new UserSpecification("validFrom", ComparableOperation.GREATER_THAN_OR_EQUAL_TO, OffsetDateTime.now()), Pageable.unpaged());
        assertEquals(2, foundUsers.size());
    }

    /**
     * Search with WILDCARD operation
     */
    @Test
    public void search_shouldSearchWith_wildCard() {
        final List<User> users = createUser(3);
        users.get(0).setUsername("tdtin");
        users.get(1).setUsername("tdtin123");
        users.get(2).setUsername("micheal");
        repo.saveAll(users);

        List<User> foundUsers = repo.search(new UserSpecification("username", WildCardOperation.LIKE, "%tin%"), Pageable.unpaged());
        assertEquals(2, foundUsers.size());

        foundUsers = repo.search(new UserSpecification("username", WildCardOperation.NOT_LIKE, "%tin%"), Pageable.unpaged());
        assertEquals(1, foundUsers.size());
    }

    /**
     * Search with combined operation
     */
    @Test
    public void search_shouldSearchWith_combinedCriteria() {
        final List<User> users = createUser(4);
        users.get(0).setValidFrom(MAX_DAY);
        users.get(1).setValidFrom(MAX_DAY);

        users.get(0).setUsername("tdtin");
        users.get(1).setUsername("tdtin123");
        users.get(2).setUsername("micheal");
        repo.saveAll(users);


        UserSpecification validFromQuery = new UserSpecification("validFrom", ComparableOperation.GREATER_THAN_OR_EQUAL_TO, OffsetDateTime.now());
        UserSpecification usernameQuery = new UserSpecification("username", BasicOperation.EQUAL_TO, "micheal");

        List<User> foundUsers = repo.search(validFromQuery.or(usernameQuery), Pageable.unpaged());
        assertEquals(3, foundUsers.size());
    }

    /**
     * Search with paging and sort
     */
    @Test
    public void search_shouldSearchWith_paging_and_sorting() {
        final List<User> users = createUser(4);
        users.get(0).setUsername("B");
        users.get(1).setUsername("A");
        users.get(2).setUsername("A1");
        users.get(3).setUsername("C");
        repo.saveAll(users);

        List<User> foundUsers = repo.search(null,  PageRequest.of(1, 2, Sort.Direction.ASC, "username"));
        assertEquals(2, foundUsers.size());
        assertEquals("B", foundUsers.get(0).getUsername());
        assertEquals("C", foundUsers.get(1).getUsername());
    }

    /**
     * Create dummy users
     *
     * @param amount amount of user
     * @return list of dummy user
     */
    private List<User> createUser(int amount) {
        List<User> users = new ArrayList<>();
        String tenant = UUID.randomUUID().toString();
        for (int i = 0; i < amount; i++) {
            User user = User.builder()
                    .id(new UserId(tenant))
                    .username(Faker.instance().name().username())
                    .firstname(Faker.instance().name().firstName())
                    .lastname(Faker.instance().name().lastName())
                    .validFrom(OffsetDateTime.now()).build();
            user.setAddress(List.of(Address.builder()
                    .city(Faker.instance().address().city())
                    .user(user).build()));
            users.add(user);
        }
        return users;
    }

    /**
     * Inject repository
     * @param repo repository
     */
    @Autowired
    public void setRepo(UserRepository repo) {
        this.repo = repo;
    }
}
