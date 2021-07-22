package com.tdtin.springdatacommon.repository;

import java.util.List;
import com.tdtin.springdatacommon.entity.User;
import com.tdtin.springdatacommon.entity.UserId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UserId>, SearchSpecificationExecutor<User, UserId> {

    @EntityGraph(value = "user-entity-graph")
    @Override
    List<User> search(Specification<User> spec, Pageable pageable);

    @EntityGraph(value = "user-entity-graph")
    @Override
    void deleteAll();

}
