package com.tdtin.springdatacommon.repository;

import static org.springframework.data.jpa.repository.query.QueryUtils.toOrders;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;


public class BaseRepository<T, I extends Serializable> extends SimpleJpaRepository<T, I> implements SearchSpecificationExecutor<T, I> {

    private final JpaEntityInformation<T, I> entityInformation;
    private final EntityManager entityManager;

    /**
     * Constructor
     * @param entityInformation entity Information
     * @param entityManager entity manager
     */
    public BaseRepository(JpaEntityInformation<T, I> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
        this.entityInformation = entityInformation;
    }

    /**
     * Because of N+1 query issue and HHH000104 (firstResult-maxResults-warning) to improve performance,
     * first we need to get list of id that matched to criteria and do paging, then we do 2nd SELECT
     * to fetch all entity IN that list of Id
     * @param specification query criteria see {@link org.springframework.data.jpa.repository.JpaSpecificationExecutor#findAll(Specification, Pageable)}
     * @param pageable paging
     * @return list of paged ids that match to the specification
     */
    @SuppressWarnings("unchecked")
    public List<I> findEntityIds(Specification<T> specification, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<I> criteriaQuery = criteriaBuilder.createQuery(this.entityInformation.getIdType());
        Root<T> root = criteriaQuery.from(this.getDomainClass());

        // Get the entities ID only
        CriteriaQuery<I> query = criteriaQuery.select((Path<I>) root.get(this.entityInformation.getIdAttribute()));
        if (specification != null) {
            query.where(specification.toPredicate(root, criteriaQuery, criteriaBuilder));
        }

        // Update Sorting
        Sort sort = pageable.isPaged() ? pageable.getSort() : Sort.unsorted();
        if (sort.isSorted()) {
            criteriaQuery.orderBy(toOrders(sort, root, criteriaBuilder));
        }

        TypedQuery<I> typedQuery = this.entityManager.createQuery(criteriaQuery);

        // Update Pagination attributes
        if (pageable.isPaged()) {
            typedQuery.setFirstResult((int) pageable.getOffset());
            typedQuery.setMaxResults(pageable.getPageSize());
        }

        return typedQuery.getResultList();
    }

    @Override
    public List<T> search(Specification<T> spec, Pageable pageable) {
        List<I> entityIds = findEntityIds(spec, pageable);
        if (CollectionUtils.isEmpty(entityIds)) {
            return List.of();
        } else {
            Specification<T> querySpecification = (root, query, builder) -> root.get(this.entityInformation.getIdAttribute()).in(entityIds);

            if (pageable.isPaged()) {
                return this.findAll(querySpecification, pageable.getSort());
            }
            return this.findAll(querySpecification);
        }
    }
}
