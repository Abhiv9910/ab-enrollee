package com.enrollee.tracking.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.enrollee.tracking.model.Enrollee;
import com.enrollee.tracking.service.EnrolleeService;

@RestController
@Validated
public class EnrolleeController {

	@Autowired
	private EnrolleeService enrolleeService;

	@GetMapping(value = "enrollees")
	public @ResponseBody List<Enrollee> getEnrollees() {
		return enrolleeService.getEnrollees();
	}

	@GetMapping(value = "enrollee/{id}")
	public @ResponseBody Enrollee getEnrolleeById(@PathVariable("id") String id) {
		return enrolleeService.getEnrollee(Long.valueOf(id));
	}

	@PostMapping(value = "enrollee")
	public @ResponseBody Enrollee postEnrollee(@Valid @RequestBody Enrollee enrollee) {
		return enrolleeService.save(enrollee);
	}

	@PutMapping(value = "enrollee")
	public @ResponseBody Enrollee putEnrollee(@Valid @RequestBody Enrollee enrollee) {
		return enrolleeService.save(enrollee);
	}

	@DeleteMapping(value = "enrollee/{id}")
	public @ResponseBody Enrollee deleteEnrollee(@PathVariable("id") String id) {
		return enrolleeService.delete(Long.valueOf(id));
	}

	@GetMapping(value = "paged-enrollees/{page-number}/{page-size}")
	public PagedListHolder<Enrollee> getPagedEnrollees(@PathVariable("page-number") int pageNumber,
			@PathVariable("page-size") int pageSize, @RequestParam("search") Optional<String> search,
			@RequestParam("sort-by") Optional<String> sortBy) {
		return enrolleeService.getPagedEnrollees(pageNumber - 1, pageSize, search, sortBy);
	}

}
