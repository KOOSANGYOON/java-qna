package codesquad.web;

import java.net.URI;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquad.domain.Question;
import codesquad.domain.QuestionRepository;
import codesquad.domain.User;
import codesquad.dto.QuestionDto;
import codesquad.dto.UserDto;
import codesquad.security.LoginUser;
import codesquad.service.QnaService;
import codesquad.service.UserService;

@RestController
@RequestMapping("/api/questions")
public class ApiQuestionController {
	@Resource(name = "qnaService")
    private QnaService qnaService;

    @PostMapping("")
    public ResponseEntity<Void> create(@Valid @RequestBody QuestionDto question) {
        Question savedQuestion = qnaService.add(question);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/api/questions/" + savedQuestion.getId()));
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public QuestionDto show(@PathVariable long id) {
        Question question = qnaService.findById(id);
        return question.toQuestionDto();
    }

}
