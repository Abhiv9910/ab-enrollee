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

import com.enrollee.tracking.model.Dependent;
import com.enrollee.tracking.repository.DependentRepository;
import com.enrollee.tracking.service.DependentService;

@RunWith(MockitoJUnitRunner.class)
public class DependentServiceTests {

	private static final String ENTITY_NOT_FOUND = "Entity Not Found";

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Mock
	private DependentRepository<Dependent> dependentRepository;

	@InjectMocks
	private DependentService dependentService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getDependentsShouldReturnAllDependents() {
		Dependent dependent1 = new Dependent();
		dependent1.setId(1L);
		dependent1.setName("Dep 1");

		Dependent dependent2 = new Dependent();
		dependent2.setId(2L);
		dependent2.setName("Dep 2");
		List<Dependent> list = new ArrayList<Dependent>();
		list.add(dependent1);
		list.add(dependent2);
		when(dependentRepository.findAll()).thenReturn(list);
		assertEquals(2, dependentService.getDependents().size());
	}

	@Test
	public void saveDependentShouldReturnSavedDependent() {
		Dependent dependent = new Dependent();
		dependent.setId(1L);
		dependent.setName("Dep");
		when(dependentRepository.save(dependent)).thenReturn(dependent);
		assertEquals("Dep", dependentService.save(dependent).getName());
	}

	@Test
	public void findByIdDependentShouldReturnNotFoundException() {
		thrown.expect(ResponseStatusException.class);
		thrown.expectMessage(ENTITY_NOT_FOUND);
		dependentService.getDependent(100L);
	}

	@Test
	public void deleteByIdDependentShouldReturnNotFoundException() {
		thrown.expect(ResponseStatusException.class);
		thrown.expectMessage(ENTITY_NOT_FOUND);
		dependentService.delete(100L);
	}

}
