/*
 *
 *
 * Copyright by Axon Ivy (Lucerne), all rights reserved.
 */

package com.tdtin.springdatacommon.repository.search;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

@SuppressWarnings({"unchecked", "rawtypes"})
public enum ComparableOperation implements Operation<Comparable> {

    LESS_THAN {
        @Override
        public Predicate createCriteria(CriteriaBuilder builder, Expression<Comparable> expression, Comparable value) {
            return builder.lessThan(expression, value);
        }
    },

    GREATER_THAN {
        @Override
        public Predicate createCriteria(CriteriaBuilder builder, Expression<Comparable> expression, Comparable value) {
            return builder.greaterThan(expression, value);
        }
    },
    LESS_THAN_OR_EQUAL_TO {
        @Override
        public Predicate createCriteria(CriteriaBuilder builder, Expression<Comparable> expression, Comparable value) {
            return builder.lessThanOrEqualTo(expression, value);
        }

        @Override
        public Predicate createCriteria(CriteriaBuilder builder, Expression<Comparable> firstExpression, Expression<Comparable> secondExpression) {
            return builder.lessThanOrEqualTo(firstExpression, secondExpression);
        }
    },
    GREATER_THAN_OR_EQUAL_TO {
        @Override
        public Predicate createCriteria(CriteriaBuilder builder, Expression<Comparable> expression, Comparable value) {
            return builder.greaterThanOrEqualTo(expression, value);
        }
    }


}
