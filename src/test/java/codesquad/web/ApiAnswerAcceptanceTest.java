package codesquad.web;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import codesquad.domain.User;
import codesquad.dto.QuestionDto;
import support.test.AcceptanceTest;

public class ApiAnswerAcceptanceTest extends AcceptanceTest{
	private static final Logger log = LoggerFactory.getLogger(QuestionController.class);
	
	private QuestionDto createQuestionDto(String title) {
		QuestionDto question = new QuestionDto(title, "This is a test question contents.");
		return question;
	}
	
	@Test
    public void create() {
		QuestionDto newQuestion = createQuestionDto("Test title");
		String questionLocation = createResource("/api/questions", newQuestion);
		
		User guest = new User("guestId", "test", "guestName", "guest@naver.com");
		String answerLocation = questionLocation + "/answers";
//		basicAuthTemplate(guest).put(answerLocation, "First answer.");
		
		HtmlFormDataBuilder htmlFormDataBuilder = HtmlFormDataBuilder.urlEncodedForm();
		htmlFormDataBuilder.addParameter("contents", "Add answer.");
		HttpEntity<MultiValueMap<String, Object>> request = htmlFormDataBuilder.build();
		String contents = "First answer.";
		ResponseEntity<String> response = basicAuthTemplate(guest).postForEntity(answerLocation, contents, String.class);
		
//		basicAuthTemplate().postForEntity(answerLocation, request, responseType);
		
		System.out.println("shit");
//		String answerLocation = createResource(String.format("/api/questions/%d/addAnswer", newQuestion.getId()), "First answer.");
		
//        Answer dbAnswer = basicAuthTemplate(findByUserId(newUser.getUserId())).getForObject(location, UserDto.class);
//        UserDto dbUser = basicAuthTemplate(findByUserId(newUser.getUserId())).getForObject(location, UserDto.class);
//        assertThat(dbUser, is(newUser));
    }
	
//	@Test
//	public void create() throws Exception {
//		QuestionDto newQuestion = createQuestionDto("Test title");
//		String location = createResource("/api/questions", newQuestion);
//		QuestionDto dbQuestion = getResource(location, QuestionDto.class, defaultUser());
//		assertThat(dbQuestion.getId(), is(newQuestion.getId()));
//		assertThat(dbQuestion.getTitle(), is(newQuestion.getTitle()));
//		assertThat(dbQuestion.getContents(), is(newQuestion.getContents()));
//	}
//	
//    @Test
//    public void show_다른_사람() throws Exception {
//        UserDto newUser = createUserDto("testuser2");
//        ResponseEntity<String> response = template().postForEntity("/api/users", newUser, String.class);
//        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
//        String location = response.getHeaders().getLocation().getPath();  
//        
//        response = basicAuthTemplate(defaultUser()).getForEntity(location, String.class);
//        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
//    }
//
	
//
//    @Test
//    public void update() throws Exception {
//        UserDto newUser = createUserDto("testuser3");
//        ResponseEntity<String> response = template().postForEntity("/api/users", newUser, String.class);
//        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
//        String location = response.getHeaders().getLocation().getPath();  
//        
//        User loginUser = findByUserId(newUser.getUserId());
//        UserDto updateUser = new UserDto(newUser.getUserId(), "password", "name2", "javajigi@slipp.net2");
//        basicAuthTemplate(loginUser).put(location, updateUser);
//        
//        UserDto dbUser = basicAuthTemplate(findByUserId(newUser.getUserId())).getForObject(location, UserDto.class);
//        assertThat(dbUser, is(updateUser));
//    }
//    
//    @Test
//    public void update_다른_사람() throws Exception {
//        UserDto newUser = createUserDto("testuser4");
//        ResponseEntity<String> response = template().postForEntity("/api/users", newUser, String.class);
//        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
//        String location = response.getHeaders().getLocation().getPath(); 
//        
//        UserDto updateUser = new UserDto(newUser.getUserId(), "password", "name2", "javajigi@slipp.net2");
//        basicAuthTemplate(defaultUser()).put(location, updateUser);
//        
//        UserDto dbUser = basicAuthTemplate(findByUserId(newUser.getUserId())).getForObject(location, UserDto.class);
//        assertThat(dbUser, is(newUser));
//    }
}
