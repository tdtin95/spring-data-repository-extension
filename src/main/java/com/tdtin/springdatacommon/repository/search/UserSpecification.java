package com.tdtin.springdatacommon.repository.search;

import javax.persistence.criteria.JoinType;
import com.tdtin.springdatacommon.entity.User;

public class UserSpecification extends AbstractEntitySpecification<User> {


    /**
     * Constructor
     * @param path entity path (e.g: address.city)
     * @param operation query operation (E.g EQUAL, LESSTHAN, ... ). See more in {@link ComparableOperation}, {@link BasicOperation}
     * @param value query value
     */
    public UserSpecification(String path, Operation<?> operation, Object value) {
        super(path, operation, value);
    }

    /**
     * Constructor
     * @param path entity path (e.g: address.city)
     * @param operation query operation (E.g EQUAL, LESSTHAN, ... ). See more in {@link ComparableOperation}, {@link BasicOperation}
     * @param value query value
     * @param joinType joinType if we need to join a table (e.g in order to query address.city from user,we need to do INNER JOIN
     *                 with Address table
     */
    public UserSpecification(String path, Operation<?> operation, Object value, JoinType joinType) {
        super(path, operation, value, joinType);
    }
}
