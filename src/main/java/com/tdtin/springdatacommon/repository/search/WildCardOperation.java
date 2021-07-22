/*
 *
 *
 *  Copyright by Axon Ivy (Lucerne), all rights reserved.
 */
package com.tdtin.springdatacommon.repository.search;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

public enum WildCardOperation implements Operation<String> {
    LIKE {
        @Override
        public Predicate createCriteria(CriteriaBuilder builder, Expression<String> expression, String value) {
            return builder.like(expression, value);
        }
    },
    NOT_LIKE {
        @Override
        public Predicate createCriteria(CriteriaBuilder builder, Expression<String> expression, String value) {
            return builder.notLike(expression, value);
        }
    }
}
