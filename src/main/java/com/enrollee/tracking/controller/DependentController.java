package com.enrollee.tracking.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.enrollee.tracking.model.Dependent;
import com.enrollee.tracking.service.DependentService;

@RestController
@Validated
public class DependentController {

	@Autowired
	private DependentService dependentService;

	@GetMapping(value = "dependents")
	public @ResponseBody List<Dependent> getDependents() {
		return dependentService.getDependents();
	}

	@GetMapping(value = "dependent/{id}")
	public @ResponseBody Dependent getDependentById(@PathVariable("id") String id) {
		return dependentService.getDependent(Long.valueOf(id));
	}

	@PostMapping(value = "dependent")
	public @ResponseBody Dependent postDependent(@Valid @RequestBody Dependent dependent) {
		return dependentService.save(dependent);
	}

	@PutMapping(value = "dependent")
	public @ResponseBody Dependent putDependent(@Valid @RequestBody Dependent dependent) {
		return dependentService.save(dependent);
	}

	@DeleteMapping(value = "dependent/{id}")
	public @ResponseBody Dependent deleteDependent(@PathVariable("id") String id) {
		return dependentService.delete(Long.valueOf(id));
	}

}
