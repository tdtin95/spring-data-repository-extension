/*
 *
 *
 * Copyright by Axon Ivy (Lucerne), all rights reserved.
 */

package com.tdtin.springdatacommon.repository.search;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

public interface Operation<T> {

    /**
     * create a query criteria by its expression and value
     *
     * @param builder    javax CriteriaBuilder to create criteria query
     * @param expression field expression
     * @param value      use for query by value
     * @return predicate used for query
     */
    Predicate createCriteria(CriteriaBuilder builder, Expression<T> expression, T value);


    /**
     * create a query criteria using two expressions
     *
     * @param builder          javax CriteriaBuilder to create criteria query
     * @param firstExpression  expression
     * @param secondExpression expression
     * @return predicate used for query
     */
    default Predicate createCriteria(CriteriaBuilder builder, Expression<T> firstExpression, Expression<T> secondExpression) {
        return null;
    }

}
