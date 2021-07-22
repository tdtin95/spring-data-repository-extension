package com.tdtin.springdatacommon.repository;

import java.io.Serializable;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface SearchSpecificationExecutor<E, I extends Serializable> extends JpaSpecificationExecutor<E> {

    /**
     * Do a SELECT query based on specification and do paging
     * @param spec see {@link Specification}
     * @param pageable see {@link Pageable}
     * @return list of paged entities that matched to the specification
     */
    List<E> search(Specification<E> spec, Pageable pageable);
}
