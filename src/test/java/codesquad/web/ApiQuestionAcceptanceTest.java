package codesquad.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import codesquad.domain.Question;
import codesquad.domain.User;
import codesquad.dto.QuestionDto;
import support.test.AcceptanceTest;

public class ApiQuestionAcceptanceTest extends AcceptanceTest {
	@Test
	public void create() throws Exception {
		QuestionDto newQuestion = createQuestionDto("Test title");
		ResponseEntity<String> response = basicAuthTemplate().postForEntity("/api/questions", newQuestion, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.CREATED));

		String location = response.getHeaders().getLocation().getPath(); 
		QuestionDto dbQuestion = basicAuthTemplate().getForObject(location, QuestionDto.class);
		assertThat(dbQuestion.getId(), is(newQuestion.getId()));
		assertThat(dbQuestion.getTitle(), is(newQuestion.getTitle()));
		assertThat(dbQuestion.getContents(), is(newQuestion.getContents()));
	}

	@Test
	public void show() throws Exception {
		QuestionDto newQuestion = createQuestionDto("Test title");
		ResponseEntity<String> response = basicAuthTemplate().postForEntity("/api/questions", newQuestion, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
		String location = response.getHeaders().getLocation().getPath();  

		response = basicAuthTemplate(defaultUser()).getForEntity(location, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
	}

	private QuestionDto createQuestionDto(String title) {
		QuestionDto question = new QuestionDto(title, "This is a test question contents.");
		return question;
	}

	private QuestionDto createUpdatedQuestionDto(String title) {
		QuestionDto question = new QuestionDto(title, "This is a updated question contents.");
		return question;
	}

	@Test
	public void update() throws Exception {
		QuestionDto newQuestion = createQuestionDto("Test title.");

		ResponseEntity<String> response = basicAuthTemplate().postForEntity("/api/questions", newQuestion, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
		String location = response.getHeaders().getLocation().getPath();

		QuestionDto updateQuestion = createUpdatedQuestionDto("Update title.");

		basicAuthTemplate().put(location, updateQuestion);

		QuestionDto dbQuestion = basicAuthTemplate().getForObject(location, QuestionDto.class);

		assertThat(dbQuestion.getId(), is(updateQuestion.getId()));
		assertThat(dbQuestion.getTitle(), is(updateQuestion.getTitle()));
		assertThat(dbQuestion.getContents(), is(updateQuestion.getContents()));
	}

	@Test
	public void update_다른_사람() throws Exception {
		QuestionDto newQuestion = createQuestionDto("Test title.");

		ResponseEntity<String> response = basicAuthTemplate(defaultUser()).postForEntity("/api/questions", newQuestion, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
		String location = response.getHeaders().getLocation().getPath();

		QuestionDto updateQuestion = createUpdatedQuestionDto("Update title.");
		User wrongUser = new User("koo", "koosangyoon", "test", "koo@naver.com");

		basicAuthTemplate(wrongUser).put(location, updateQuestion);

		QuestionDto dbQuestion = basicAuthTemplate(defaultUser()).getForObject(location, QuestionDto.class);

		assertThat(dbQuestion.getId(), is(newQuestion.getId()));
		assertThat(dbQuestion.getTitle(), is(newQuestion.getTitle()));
		assertThat(dbQuestion.getContents(), is(newQuestion.getContents()));
	}
}
