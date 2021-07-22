/*
 *
 *
 * Copyright by Axon Ivy (Lucerne), all rights reserved.
 */

package com.tdtin.springdatacommon.repository.search;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

public enum BasicOperation implements Operation<Object> {
    EQUAL_TO {
        @Override
        public Predicate createCriteria(CriteriaBuilder builder, Expression<Object> expression, Object value) {
            return builder.equal(expression, value);
        }
    },
    NOT_EQUAL_TO {
        @Override
        public Predicate createCriteria(CriteriaBuilder builder, Expression<Object> expression, Object value) {
            return builder.notEqual(expression, value);
        }
    },
    IN {
        @Override
        public Predicate createCriteria(CriteriaBuilder builder, Expression<Object> expression, Object value) {
            return builder.in(expression).value(value);
        }
    }
}
