package codesquad.web;

import java.net.URI;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquad.domain.Answer;
import codesquad.domain.Question;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.QnaService;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {
	private static final Logger log = LoggerFactory.getLogger(QuestionController.class);
	
	@Resource(name = "qnaService")
	private QnaService qnaService;
	
	@PostMapping("")
	public void create(@LoginUser User loginUser, @PathVariable long questionId, String contents) {
		//TODO : loginUser 가 아니면 댓글 불가.
		log.debug("hihihihi");
		Question question = qnaService.findById(questionId);
		Answer newAnswer = qnaService.addAnswer(loginUser, questionId, contents);
		
		log.debug("New Answer is" + newAnswer.toString());
		
//		HttpHeaders headers = new HttpHeaders();
//		headers.setLocation(URI.create("/api/questions/" + question.getId()));
//		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
}
