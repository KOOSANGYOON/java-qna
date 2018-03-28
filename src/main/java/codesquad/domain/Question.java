package codesquad.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Where;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.annotation.JsonProperty;

<<<<<<< HEAD
import codesquad.CannotDeleteException;
=======
>>>>>>> 606ea02d20adf35398698d9e27d08af530181aac
import codesquad.UnAuthenticationException;
import codesquad.dto.QuestionDto;
import support.domain.AbstractEntity;
import support.domain.UrlGeneratable;

@Entity
public class Question extends AbstractEntity implements UrlGeneratable {
<<<<<<< HEAD
=======
	//	@Id
	//	@GeneratedValue
	//	@JsonProperty
	//	private long id;

>>>>>>> 606ea02d20adf35398698d9e27d08af530181aac
	@Size(min = 3, max = 100)
	@Column(length = 100, nullable = false)
	private String title;

	@Size(min = 3)
	@Lob
	private String contents;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
	private User writer;

	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
	@Where(clause = "deleted = false")
	@OrderBy("id ASC")
	private List<Answer> answers = new ArrayList<>();

	private boolean deleted = false;

	public Question() {
	}

	public Question(String title, String contents) {
		this.title = title;
		this.contents = contents;
	}
<<<<<<< HEAD
	
	public void update(User loginUser, String title, String contents) {
		if (!this.isOwner(loginUser)) {
			return;
		}
=======

	public void update(String title, String contents) {
>>>>>>> 606ea02d20adf35398698d9e27d08af530181aac
		this.title = title;
		this.contents = contents;
	}

	public String getTitle() {
		return title;
	}

	public String getContents() {
		return contents;
	}

	public User getWriter() {
		return writer;
	}
<<<<<<< HEAD
	
	public Answer getAnswer(Long id) {
		for (Answer answer : answers) {
			if (answer.getId() == id) {
				return answer;
			}
		}
		return null;
	}
=======
>>>>>>> 606ea02d20adf35398698d9e27d08af530181aac

	public void writeBy(User loginUser) {
		this.writer = loginUser;
	}

	public void addAnswer(Answer answer) {
		answer.toQuestion(this);
		answers.add(answer);
	}
<<<<<<< HEAD
	
=======

>>>>>>> 606ea02d20adf35398698d9e27d08af530181aac
	public boolean isOwner(User loginUser) {
		return writer.equals(loginUser);
	}

	public boolean isDeleted() {
		return deleted;
	}

<<<<<<< HEAD
	@Transactional
	public List<DeleteHistory> deleteQuestion(User loginUser) throws CannotDeleteException {
		if (!this.isOwner(loginUser)) {
			throw new CannotDeleteException("본인의 글만 삭제할 수 있습니다.");
		}
		
		if (this.isDeleted()) {
			throw new CannotDeleteException("이미 삭제된 질문입니다.");
		}
		
		List<DeleteHistory> histories = new ArrayList<DeleteHistory> ();
		
		for (Answer answer : answers) {
			histories.add(answer.delete(loginUser));
		}
		this.deleted = true;
		histories.add(new DeleteHistory(ContentType.QUESTION, this.getId(), this.writer, LocalDateTime.now()));
		return histories;
	}
	
=======
	public void deleteQuestion() {
		this.deleted = true;
	}

>>>>>>> 606ea02d20adf35398698d9e27d08af530181aac
	@Override
	public String generateUrl() {
		return String.format("/questions/%d", getId());
	}

	public QuestionDto toQuestionDto() {
<<<<<<< HEAD
		return new QuestionDto(this.title, this.contents);
=======
		return new QuestionDto(getId(), this.title, this.contents);
>>>>>>> 606ea02d20adf35398698d9e27d08af530181aac
	}

	@Override
	public String toString() {
		return "Question [id=" + getId() + ", title=" + title + ", contents=" + contents + ", writer=" + writer + "]";
	}
}
