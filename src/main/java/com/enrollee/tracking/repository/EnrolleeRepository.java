package com.enrollee.tracking.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.enrollee.tracking.model.Enrollee;

public interface EnrolleeRepository<T> extends PagingAndSortingRepository<Enrollee, Long> {
}
