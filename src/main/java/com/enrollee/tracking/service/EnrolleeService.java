package com.enrollee.tracking.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.enrollee.tracking.model.Enrollee;
import com.enrollee.tracking.repository.EnrolleeRepository;
import com.enrollee.tracking.util.Constants;

@Service
public class EnrolleeService {

	@Autowired
	private EnrolleeRepository<Enrollee> enrolleeRepository;

	public List<Enrollee> getEnrollees() {
		return (List<Enrollee>) enrolleeRepository.findAll();
	}

	public Enrollee save(Enrollee enrollee) {
		return enrolleeRepository.save(enrollee);
	}

	public Enrollee delete(Long id) {
		Enrollee enrollee = null;
		try {
			enrollee = enrolleeRepository.findById(id).get();
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.ENTITY_NOT_FOUND);
		}
		enrolleeRepository.delete(enrollee);
		return enrollee;
	}

	public Enrollee getEnrollee(Long id) {
		Enrollee enrollee = null;
		try {
			enrollee = enrolleeRepository.findById(id).get();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.ENTITY_NOT_FOUND);
		}
		return enrollee;
	}

	public PagedListHolder<Enrollee> getPagedEnrollees(Integer pageNumber, Integer pageSize, Optional<String> search,
			Optional<String> sortBy) {
		List<Enrollee> enrollees = null;
		PagedListHolder<Enrollee> page = new PagedListHolder<Enrollee>();
		page.setPageSize(pageSize);
		enrollees = (List<Enrollee>) enrolleeRepository.findAll();
		if (search.isPresent() && !search.get().isEmpty()) {
			if (pageNumber * pageSize + pageSize >= enrollees.size()) {
				pageNumber = 0;
			}
			enrollees = enrollees.stream().filter((dbCategory) -> isSearchedEnrollee(dbCategory, search.get()))
					.collect(Collectors.toList());
		}
		page.setPage(pageNumber);
		if (sortBy.isPresent()) {
			PropertyComparator.sort(enrollees, new MutableSortDefinition(sortBy.get(), true, true));
		}
		page.setSource(enrollees);
		return page;
	}

	private boolean isSearchedEnrollee(Enrollee enrollee, String searchParam) {
		if (enrollee.getName().contains(searchParam))
			return true;
		return false;
	}

}
