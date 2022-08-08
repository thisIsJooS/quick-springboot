package com.springstudy.boot;

import com.springstudy.boot.domain.Answer;
import com.springstudy.boot.domain.Question;
import com.springstudy.boot.repository.AnswerRepository;
import com.springstudy.boot.repository.QuestionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class QuestionTest {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;


    @Test
    void testJpa() {
        Question q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q1);  // 첫번째 질문 저장

        Question q2 = new Question();
        q2.setSubject("스프링부트 모델 질문입니다.");
        q2.setContent("id는 자동으로 생성되나요?");
        q2.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q2);  // 두번째 질문 저장

        List<Question> all = this.questionRepository.findAll();
        assertEquals(2, all.size());

        Question q = all.get(0);
        assertEquals("sbb가 무엇인가요?", q.getSubject());

        Optional<Question> oq = this.questionRepository.findById(1);
        if(oq.isPresent()) {
            Question q3 = oq.get();
            assertEquals("sbb가 무엇인가요?", q3.getSubject());
        }

        Question q4 = this.questionRepository.findBySubject("sbb가 무엇인가요?");
        assertEquals(1, q4.getId());

        Question q5 = this.questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
        assertEquals(1, q5.getId());

        List<Question> qList = this.questionRepository.findBySubjectLike("sbb%");
        Question q6 = qList.get(0);
        assertEquals("sbb가 무엇인가요?", q6.getSubject());

        Optional<Question> oq1 = this.questionRepository.findById(1);
        assertTrue(oq1.isPresent());
        Question q7 = oq1.get();
        q7.setSubject("수정된 제목");
        this.questionRepository.save(q7);

        assertEquals(2, this.questionRepository.count());
        Optional<Question> oq3 = this.questionRepository.findById(1);
        assertTrue(oq3.isPresent());
        Question q8 = oq3.get();
        this.questionRepository.delete(q8);
        assertEquals(1, this.questionRepository.count());


        Optional<Question> oq4 = this.questionRepository.findById(2);
        assertTrue(oq4.isPresent());
        Question q9 = oq4.get();

        Answer a = new Answer();
        a.setContent("네 자동으로 생성됩니다.");
        a.setQuestion(q9);
        a.setCreateDate(LocalDateTime.now());
        this.answerRepository.save(a);

        Optional<Answer> oa = this.answerRepository.findById(1);
        assertTrue(oa.isPresent());
        Answer a1 = oa.get();
        assertEquals(2, a1.getQuestion().getId());


        // 아래 코드를 질문에 달린 답변 찾기
        // 그냥 돌리면 에러가 난다.
        // Question 레포지토리가 findById를 호출하여 Question 객체를 조회하고 나면 DB세션이 끊어진다.
        // 그 이후에 실행되는 q.getAnswerList() 메서드는 세션이 종료되어 오류가 발생한다.
        // 답변 데이터 리스트는 q객체를 조회할때 가져오지 않고 q.getAnswerList() 메서드를 호출하는 시점에 가져오기 때문이다.
        // 이 문제는 테스트 코드에서만 발생한다. 실제 서버에서 JPA프로그램을 실행할 때는 DB세션이 종료되지 않기 때문이다.
        // 해결방법은 함수 위에 @Transactional 붙이기.
        // 이 어노테이션을 사용하면 메서드가 종료될 때까지 DB세션이 유지돤다. 
//        Optional<Question> oq5 = this.questionRepository.findById(2);
//        assertTrue(oq5.isPresent());
//        Question qq = oq5.get();
//
//        List<Answer> answerList = qq.getAnswerList();
//
//        assertEquals(1, answerList.size());
//        assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
    }
}
