package com.tdtin.springdatacommon.repository.search;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class AbstractEntitySpecification<T> implements Specification<T> {

    private final String path;
    private final Operation operation;
    private final Object value;
    private JoinType joinType;


    /**
     * Constructor
     *
     * @param path      entity path (e.g : address, address.city,...
     * @param operation Operation to be query (e.g equal, lessthan,...) see {@link BasicOperation}, {@link ComparableOperation}
     * @param value     object value
     */
    public AbstractEntitySpecification(String path, Operation operation, Object value) {
        this.path = path;
        this.operation = operation;
        this.value = value;

    }

    /**
     * Constructor
     *
     * @param path      entity path (e.g : address, address.city,...
     * @param operation Operation to be query (e.g equal, lessthan,...) see {@link BasicOperation}, {@link ComparableOperation}
     * @param value     object value
     * @param joinType  join type if need to be join with other table
     */
    public AbstractEntitySpecification(String path, Operation operation, Object value, JoinType joinType) {
        this.path = path;
        this.operation = operation;
        this.value = value;
        this.joinType = joinType;
    }


    @Override
    public Predicate toPredicate(@NonNull Root<T> root, @Nullable CriteriaQuery<?> query, @NonNull CriteriaBuilder criteriaBuilder) {
        Expression<?> expression = buildExpression(root, path, joinType);
        return operation.createCriteria(criteriaBuilder, expression, value);
    }

    /**
     * Builds the expression.
     *
     * @param root      the root
     * @param fieldPath the field path
     * @param joinType  the join type
     * @return the expression
     */
    private Expression<?> buildExpression(Root<T> root, String fieldPath, JoinType joinType) {
        if (fieldPath.contains(".")) {
            String[] paths = fieldPath.split("\\.");
            Path<?> rootPath;

            if (joinType == null) {
                rootPath = root.get(paths[0]);
            } else {
                rootPath = root.join(paths[0], joinType);
            }

            for (int i = 1; i < paths.length; i++) {
                rootPath = rootPath.get(paths[i]);
            }
            return rootPath;
        }
        return root.get(fieldPath);
    }

}
