package codesquad.web;

import static org.hamcrest.CoreMatchers.is;
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

import codesquad.domain.QuestionRepository;
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
//		String userId = "testuser";
		htmlFormDataBuilder.addQuestion();
		HttpEntity<MultiValueMap<String, Object>> request = htmlFormDataBuilder.build();

		ResponseEntity<String> response = template().postForEntity("/questions/createQna", request, String.class);

		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
//		assertNotNull(userRepository.findByUserId(userId));
		assertThat(response.getHeaders().getLocation().getPath(), is("/users"));
	}

}
