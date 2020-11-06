package com.enrollee.tracking;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.enrollee.tracking.model.Dependent;
import com.enrollee.tracking.model.Enrollee;
import com.enrollee.tracking.service.DependentService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EnrolleeTrackingApplication.class)
@WebAppConfiguration
public class DependentControllerTests extends EnrolleeTrackingApplicationTests {

	@MockBean
	private DependentService dependentService;

	private String baseUri = "/dependent";

	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	public void getDependentsShouldReturnDependents() throws Exception {
		Dependent dependent1 = new Dependent();
		dependent1.setId(1L);
		dependent1.setName("name 1");

		Dependent dependent2 = new Dependent();
		dependent2.setId(2L);
		dependent2.setName("name 2");

		when(dependentService.getDependents()).thenReturn(Lists.newArrayList(dependent1, dependent2));
		MvcResult mvcResult = mockMvc
				.perform(MockMvcRequestBuilders.get(baseUri + "s").accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Enrollee[] enrolleelist = mapFromJson(content, Enrollee[].class);
		assertEquals(2, enrolleelist.length);
	}

	@Test
	public void createDependentShouldReturn200() throws Exception {
		Dependent dependent = new Dependent();
		dependent.setId(1L);
		dependent.setName("Dependent 3");
		dependent.setBirthDate("1990-01-01");
		when(dependentService.save(Mockito.any())).thenReturn(dependent);
		String inputJson = mapToJson(dependent);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(baseUri).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(inputJson)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		Dependent dependentSaved = mapFromJson(mvcResult.getResponse().getContentAsString(), Dependent.class);
		assertEquals(200, status);
		assertEquals(dependentSaved.getName(), "Dependent 3");
	}

	@Test(expected = MethodArgumentNotValidException.class)
	public void createDependentOnMissingNameShouldThrowException() throws Exception {
		Dependent dependent = new Dependent();
		dependent.setId(1L);
		dependent.setBirthDate("1990-01-01");

		when(dependentService.save(Mockito.any())).thenReturn(dependent);
		String inputJson = mapToJson(dependent);
		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.post(baseUri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();
		throw mvcResult.getResolvedException();
	}

	@Test(expected = MethodArgumentNotValidException.class)
	public void createDependentOnMissingBirthDateShouldThrowException() throws Exception {
		Dependent dependent = new Dependent();
		dependent.setId(1L);
		dependent.setName("Dep");

		when(dependentService.save(dependent)).thenReturn(dependent);
		String inputJson = mapToJson(dependent);
		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.post(baseUri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();
		throw mvcResult.getResolvedException();
	}

	@Test
	public void createDependentOnInvalidEntityShouldReturn400() throws Exception {
		Dependent dependent = new Dependent();
		dependent.setId(1L);
		dependent.setName("Dep");

		when(dependentService.save(dependent)).thenReturn(dependent);
		String inputJson = mapToJson(dependent);
		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.post(baseUri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();
		assertEquals(400, mvcResult.getResponse().getStatus());
	}

	@Test
	public void updateDependentShouldReturn200() throws Exception {
		Dependent dependent = new Dependent();
		dependent.setId(2L);
		dependent.setName("Dependent 2");
		dependent.setBirthDate("1990-01-01");

		when(dependentService.save(Mockito.any())).thenReturn(dependent);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(baseUri)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(dependent))).andReturn();
		int status = mvcResult.getResponse().getStatus();
		Dependent dependentSaved = mapFromJson(mvcResult.getResponse().getContentAsString(), Dependent.class);
		assertEquals(200, status);
		assertEquals(dependentSaved.getName(), "Dependent 2");
	}

	@Test(expected = MethodArgumentNotValidException.class)
	public void updateDependentOnMissingNameShouldThrowException() throws Exception {
		Dependent dependent = new Dependent();
		dependent.setId(1L);
		dependent.setBirthDate("1990-01-01");

		when(dependentService.save(Mockito.any())).thenReturn(dependent);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(baseUri)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(dependent))).andReturn();
		throw mvcResult.getResolvedException();
	}

	@Test(expected = MethodArgumentNotValidException.class)
	public void updateDependentOnMissingBirthDateShouldThrowException() throws Exception {
		Dependent dependent = new Dependent();
		dependent.setId(1L);
		dependent.setName("Dep");

		when(dependentService.save(dependent)).thenReturn(dependent);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(baseUri)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(dependent))).andReturn();
		throw mvcResult.getResolvedException();
	}

	@Test
	public void updateDependentOnInvalidEntityShouldReturn400() throws Exception {
		Dependent dependent = new Dependent();
		dependent.setId(1L);
		dependent.setName("Dep");
		when(dependentService.save(dependent)).thenReturn(dependent);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(baseUri)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(dependent))).andReturn();
		assertEquals(400, mvcResult.getResponse().getStatus());
	}

	@Test
	public void deleteDependentShouldReturn200() throws Exception {
		Dependent dependent = new Dependent();
		dependent.setId(8L);
		dependent.setName("Dep 8");
		String uri = "/dependent/8";
		when(dependentService.delete(8L)).thenReturn(dependent);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		Dependent dependentDeleted = mapFromJson(mvcResult.getResponse().getContentAsString(), Dependent.class);
		assertEquals(200, status);
		assertEquals(dependentDeleted.getName(), "Dep 8");
	}

}
