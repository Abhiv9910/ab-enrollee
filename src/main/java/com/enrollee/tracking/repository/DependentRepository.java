package com.enrollee.tracking.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.enrollee.tracking.model.Dependent;

public interface DependentRepository<T> extends PagingAndSortingRepository<Dependent, Long> {
}
