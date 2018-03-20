package codesquad.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import codesquad.dto.QuestionDto;
import support.test.AcceptanceTest;

public class ApiQuestionAcceptanceTest extends AcceptanceTest {
	@Test
	public void create() throws Exception {
		QuestionDto newQuestion = createQuestionDto("Test title");
		ResponseEntity<String> response = template().postForEntity("/api/questions", newQuestion, String.class);
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
		ResponseEntity<String> response = template().postForEntity("/api/questions", newQuestion, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
		String location = response.getHeaders().getLocation().getPath();  

		response = basicAuthTemplate(defaultUser()).getForEntity(location, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
	}
	    
	private QuestionDto createQuestionDto(String title) {
		return new QuestionDto(title, "This is a test question contents");
	}
	
	
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
