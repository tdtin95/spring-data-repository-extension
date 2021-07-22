# spring-data-repository-extension

A spring boot project with Spring Data to prevent N+1 query issue and support paging without
"HHH000104: firstResult/maxResults specified with collection fetch; applying in memory!"
## Installation

```bash
gradlew bootRun
```

## Usage
spring-data-repository-extension leverage Spring Data Specification to make an easy-to-use query, improve readability 
and maintainability.

### Create Entity With Entity Graph
```java
@NamedEntityGraph(
    name = "user-entity-graph",
    attributeNodes = {
        @NamedAttributeNode("address")
    }
)
@Table(name = "fp_user", uniqueConstraints = {@UniqueConstraint(columnNames = {"tenantid", "username"})})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Setter
public class User {
    @EmbeddedId
    private UserId id;

    private String username;

    private String firstname;

    private String lastname;

    private OffsetDateTime validFrom;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<Address> address;
}
```

### Create repository
Register a repository, and define entity graph to prevent N+1 query
```java
@Repository
public interface UserRepository extends JpaRepository<User, UserId>, SearchSpecificationExecutor<User, UserId> {

    @EntityGraph(value = "user-entity-graph")
    @Override
    List<User> search(Specification<User> spec, Pageable pageable);

    @EntityGraph(value = "user-entity-graph")
    @Override
    void deleteAll();

}
```

### Make a query
```java
import static com.tdtin.springdatacommon.repository.search.ComparableOperation.*;
import static com.tdtin.springdatacommon.repository.search.BasicOperation.*;
```

```java
UserSpecification validFromQuery = new UserSpecification("validFrom", ComparableOperation.GREATER_THAN_OR_EQUAL_TO, OffsetDateTime.now());

UserSpecification usernameQuery = new UserSpecification("username", BasicOperation.EQUAL_TO, "micheal");

List<User> foundUsers = repo.search(validFromQuery.or(usernameQuery), Pageable.of(0, 2, PageRequest.of(1, 2, Sort.Direction.ASC, "username")));
```

For more use cases, please refer [UserRepositoryTest.java](src/test/java/com/tdtin/springdatacommon/repository/UserRepositoryTest.java)