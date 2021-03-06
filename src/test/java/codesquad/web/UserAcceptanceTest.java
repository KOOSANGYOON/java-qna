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
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import codesquad.domain.User;
import codesquad.domain.UserRepository;
import support.test.AcceptanceTest;

public class UserAcceptanceTest extends AcceptanceTest {
	private static final Logger log = LoggerFactory.getLogger(UserAcceptanceTest.class);

	@Autowired
	private UserRepository userRepository;

	@Test
	public void createForm() throws Exception {
		ResponseEntity<String> response = template().getForEntity("/users/form", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		log.debug("body : {}", response.getBody());
	}

	@Test
	public void create() throws Exception {
		HtmlFormDataBuilder htmlFormDataBuilder = HtmlFormDataBuilder.urlEncodedForm();
		String userId = "testuser";

		htmlFormDataBuilder.addSampleUser(userId);

		HttpEntity<MultiValueMap<String, Object>> request = htmlFormDataBuilder.build();

		ResponseEntity<String> response = template().postForEntity("/users", request, String.class);

		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		assertNotNull(userRepository.findByUserId(userId));
		assertThat(response.getHeaders().getLocation().getPath(), is("/users"));
	}

	@Test
	public void list() throws Exception {
		ResponseEntity<String> response = template().getForEntity("/users", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		log.debug("body : {}", response.getBody());
		assertThat(response.getBody().contains(defaultUser().getEmail()), is(true));
	}

	@Test
	public void updateForm_no_login() throws Exception {
		ResponseEntity<String> response = template().getForEntity(String.format("/users/%d/form", defaultUser().getId()),
				String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
	}

	@Test
	public void updateForm_login() throws Exception {
		User loginUser = defaultUser();
		ResponseEntity<String> response = basicAuthTemplate(loginUser)
				.getForEntity(String.format("/users/%d/form", loginUser.getId()), String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(response.getBody().contains(loginUser.getEmail()), is(true));
	}

	@Test
	public void update_no_login() throws Exception {
		ResponseEntity<String> response = update(template());
		assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
	}

	private ResponseEntity<String> update(TestRestTemplate template) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
		params.add("_method", "put");
		params.add("password", "password2");
		params.add("name", "자바지기2");
		params.add("email", "javajigi@slipp.net");
		HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(params, headers);

		return template.postForEntity(String.format("/users/%d", defaultUser().getId()), request, String.class);
	}

	@Test
	public void update() throws Exception {
		ResponseEntity<String> response = update(basicAuthTemplate());
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		assertTrue(response.getHeaders().getLocation().getPath().startsWith("/users"));
	}
}
