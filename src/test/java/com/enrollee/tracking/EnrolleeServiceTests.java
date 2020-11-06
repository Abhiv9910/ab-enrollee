package com.enrollee.tracking;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.server.ResponseStatusException;

import com.enrollee.tracking.model.Enrollee;
import com.enrollee.tracking.repository.EnrolleeRepository;
import com.enrollee.tracking.service.EnrolleeService;

@RunWith(MockitoJUnitRunner.class)
public class EnrolleeServiceTests {

	private static final String ENTITY_NOT_FOUND = "Entity Not Found";

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Mock
	private EnrolleeRepository<Enrollee> enrolleeRepository;

	@InjectMocks
	private EnrolleeService enrolleeService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getEnrolleesShouldReturnAllEnrollees() {
		Enrollee Enrollee1 = new Enrollee();
		Enrollee1.setId(1L);
		Enrollee1.setName("En 1");

		Enrollee Enrollee2 = new Enrollee();
		Enrollee2.setId(2L);
		Enrollee2.setName("En 2");
		List<Enrollee> list = new ArrayList<Enrollee>();
		list.add(Enrollee1);
		list.add(Enrollee2);
		when(enrolleeRepository.findAll()).thenReturn(list);
		assertEquals(2, enrolleeService.getEnrollees().size());
	}

	@Test
	public void saveEnrolleeShouldReturnSavedEnrollee() {
		Enrollee Enrollee = new Enrollee();
		Enrollee.setId(1L);
		Enrollee.setName("En");
		when(enrolleeRepository.save(Enrollee)).thenReturn(Enrollee);
		assertEquals("En", enrolleeService.save(Enrollee).getName());
	}

	@Test
	public void findByIdEnrolleeShouldReturnNotFoundException() {
		thrown.expect(ResponseStatusException.class);
		thrown.expectMessage(ENTITY_NOT_FOUND);
		enrolleeService.getEnrollee(100L);
	}

	@Test
	public void deleteByIdDependentShouldReturnNotFoundException() {
		thrown.expect(ResponseStatusException.class);
		thrown.expectMessage(ENTITY_NOT_FOUND);
		enrolleeService.delete(100L);
	}

}
