package codesquad.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import codesquad.domain.User;
import codesquad.dto.QuestionDto;
import support.test.AcceptanceTest;

public class QuestionAcceptanceTest extends AcceptanceTest{
	private static final Logger log = LoggerFactory.getLogger(QuestionAcceptanceTest.class);

	@Autowired
	private QuestionRepository questionRepository;

	@Test
	public void createForm() throws Exception {
		ResponseEntity<String> response = template().getForEntity("/questions/form", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		log.debug("body : {}", response.getBody());
	}

	@Test
	public void create() throws Exception {
		HtmlFormDataBuilder htmlFormDataBuilder = HtmlFormDataBuilder.urlEncodedForm();
		htmlFormDataBuilder.addSampleQuestion();
		HttpEntity<MultiValueMap<String, Object>> request = htmlFormDataBuilder.build();

		ResponseEntity<String> response = basicAuthTemplate().postForEntity("/questions/create", request, String.class);

		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		assertEquals(1, questionRepository.count());
		assertThat(response.getHeaders().getLocation().getPath(), is("/"));
	}

	@Test
	public void list() throws Exception {
		ResponseEntity<String> response = template().getForEntity("/questions", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		log.debug("body : {}", response.getBody());
	}

	@Test
	public void updateForm_question() throws Exception {
		User loginUser = defaultUser();
		ResponseEntity<String> response = basicAuthTemplate(loginUser)
				.getForEntity(String.format("/questions/%d/form", questionRepository.count()), String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
	}
}
