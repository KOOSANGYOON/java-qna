package codesquad.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import codesquad.domain.User;
import codesquad.domain.UserRepository;
import codesquad.service.UserService;
import support.test.AcceptanceTest;

public class LoginAcceptanceTest extends AcceptanceTest{
	@Autowired
	private UserRepository userRepository;

	@Test
	public void login() throws Exception {
		HtmlFormDataBuilder htmlFormDataBuilder = HtmlFormDataBuilder.urlEncodedForm();
		String userId = "testuser"; String password = "test";
		htmlFormDataBuilder.addParameter("userId", userId);
		htmlFormDataBuilder.addParameter("password", password);
		
		HttpEntity<MultiValueMap<String, Object>> request = htmlFormDataBuilder.build();
		ResponseEntity<String> response = template().postForEntity("/users", request, String.class);
//		UserService.login(userId, password);

		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		assertNotNull(userRepository.findByUserId(userId));
		assertThat(response.getHeaders().getLocation().getPath(), is("/users"));
	}
}
