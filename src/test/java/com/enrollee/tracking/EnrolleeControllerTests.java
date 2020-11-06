package com.enrollee.tracking;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.enrollee.tracking.model.Enrollee;
import com.enrollee.tracking.service.EnrolleeService;

public class EnrolleeControllerTests extends EnrolleeTrackingApplicationTests {

	@MockBean
	private EnrolleeService enrolleeService;

	private String baseUri = "/enrollee";

	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	public void getEnrolleesShouldReturnEnrollees() throws Exception {
		String uri = baseUri + "s";
		Enrollee enrollee1 = new Enrollee();
		enrollee1.setId(1L);
		enrollee1.setName("name 1");

		Enrollee enrollee2 = new Enrollee();
		enrollee2.setId(2L);
		enrollee2.setName("name 2");

		when(enrolleeService.getEnrollees()).thenReturn(Lists.newArrayList(enrollee1, enrollee2));
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		Enrollee[] enrolleelist = mapFromJson(content, Enrollee[].class);
		assertEquals(2, enrolleelist.length);
	}

	@Test
	public void createEnrolleeShouldReturn200() throws Exception {
		Enrollee enrollee = new Enrollee();
		enrollee.setId(3L);
		enrollee.setName("Enrollee 3");
		enrollee.setBirthDate("1990-03-02");

		when(enrolleeService.save(Mockito.any())).thenReturn(enrollee);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(baseUri)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(enrollee))).andReturn();
		int status = mvcResult.getResponse().getStatus();
		Enrollee enrolleeSaved = mapFromJson(mvcResult.getResponse().getContentAsString(), Enrollee.class);
		assertEquals(200, status);
		assertEquals(enrolleeSaved.getName(), "Enrollee 3");
	}

	@Test(expected = MethodArgumentNotValidException.class)
	public void createEnrolleeShouldResolvedToExceptionOnMissingBirthDate() throws Exception {
		Enrollee enrollee = new Enrollee();
		enrollee.setId(3L);
		enrollee.setName("Enrollee 3");
		when(enrolleeService.save(Mockito.any())).thenReturn(enrollee);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(baseUri)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(enrollee))).andReturn();
		throw mvcResult.getResolvedException();
	}

	@Test
	public void createEnrolleeShouldReturn400OnInvalidEntity() throws Exception {
		Enrollee enrollee = new Enrollee();
		enrollee.setId(3L);
		enrollee.setName("Enrollee 3");
		when(enrolleeService.save(Mockito.any())).thenReturn(enrollee);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(baseUri)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(enrollee))).andReturn();
		assertEquals(400, mvcResult.getResponse().getStatus());
	}

	@Test(expected = MethodArgumentNotValidException.class)
	public void createEnrolleeShouldResolvedToExceptionOnName() throws Exception {
		Enrollee enrollee = new Enrollee();
		enrollee.setId(3L);
		enrollee.setName("");
		enrollee.setBirthDate("1990-01-01");
		when(enrolleeService.save(Mockito.any())).thenReturn(enrollee);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(baseUri)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(enrollee))).andReturn();
		throw mvcResult.getResolvedException();
	}

	@Test
	public void updateEnrolleeShouldReturn200() throws Exception {
		Enrollee enrollee = new Enrollee();
		enrollee.setId(2L);
		enrollee.setName("Enrollee 2");
		enrollee.setBirthDate("1990-01-01");
		when(enrolleeService.save(Mockito.any())).thenReturn(enrollee);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(baseUri)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(enrollee))).andReturn();
		int status = mvcResult.getResponse().getStatus();
		Enrollee enrolleeSaved = mapFromJson(mvcResult.getResponse().getContentAsString(), Enrollee.class);
		assertEquals(200, status);
		assertEquals(enrolleeSaved.getName(), "Enrollee 2");
	}

	@Test(expected = MethodArgumentNotValidException.class)
	public void updateEnrolleeMissingBirthDateShouldThrowException() throws Exception {
		Enrollee enrollee = new Enrollee();
		enrollee.setId(2L);
		enrollee.setName("Enrollee 2");
		when(enrolleeService.save(Mockito.any())).thenReturn(enrollee);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(baseUri)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(enrollee))).andReturn();
		throw mvcResult.getResolvedException();
	}

	@Test
	public void updateEnrolleeShouldReturn400OnInvalidEntity() throws Exception {
		Enrollee enrollee = new Enrollee();
		enrollee.setId(2L);
		enrollee.setName("Enrollee 2");
		when(enrolleeService.save(Mockito.any())).thenReturn(enrollee);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(baseUri)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(enrollee))).andReturn();
		assertEquals(400, mvcResult.getResponse().getStatus());
	}

	@Test(expected = MethodArgumentNotValidException.class)
	public void updateEnrolleeMissingNameShouldThrowException() throws Exception {
		Enrollee enrollee = new Enrollee();
		enrollee.setId(2L);
		enrollee.setBirthDate("1990-01-01");
		when(enrolleeService.save(Mockito.any())).thenReturn(enrollee);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(baseUri)
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(mapToJson(enrollee))).andReturn();
		throw mvcResult.getResolvedException();
	}

	@Test
	public void deleteEnrolleeShouldReturn200() throws Exception {
		Enrollee enrollee = new Enrollee();
		enrollee.setId(8L);
		enrollee.setName("Enrollee 8");
		String uri = "/enrollee/8";
		when(enrolleeService.delete(8L)).thenReturn(enrollee);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		Enrollee enrolleeDeleted = mapFromJson(mvcResult.getResponse().getContentAsString(), Enrollee.class);
		assertEquals(200, status);
		assertEquals(enrolleeDeleted.getName(), "Enrollee 8");
	}

}
