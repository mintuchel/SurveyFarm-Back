package notblank.boatvote.answer.service;

import notblank.boatvote.domain.answer.repository.AnswerRepository;
import notblank.boatvote.domain.answer.service.AnswerService;
import notblank.boatvote.domain.question.service.QuestionService;
import notblank.boatvote.domain.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class AnswerServiceTest {
    @InjectMocks
    private AnswerService answerService;

    @Mock
    private UserService userService;
    @Mock
    private QuestionService questionService;
    @Mock
    private AnswerRepository answerRepository;


}
