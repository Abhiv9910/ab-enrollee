package com.enrollee.tracking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.enrollee.tracking.model.Dependent;
import com.enrollee.tracking.repository.DependentRepository;
import com.enrollee.tracking.util.Constants;

@Service
public class DependentService {

	@Autowired
	private DependentRepository<Dependent> dependentRepository;

	public List<Dependent> getDependents() {
		return (List<Dependent>) dependentRepository.findAll();
	}

	public Dependent save(Dependent dependent) {
		return dependentRepository.save(dependent);
	}

	public Dependent getDependent(Long id) {
		Dependent dependent = null;
		try {
			dependent = dependentRepository.findById(id).get();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.ENTITY_NOT_FOUND);
		}
		return dependent;
	}

	public Dependent delete(Long id) {
		Dependent dependent = null;
		try {
			dependent = dependentRepository.findById(id).get();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, Constants.ENTITY_NOT_FOUND);
		}
		dependentRepository.delete(dependent);
		return dependent;
	}

}
