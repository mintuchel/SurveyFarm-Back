package notblank.boatvote.domain.question.entity;

import jakarta.persistence.*;
import lombok.*;
import notblank.boatvote.domain.answer.entity.Answer;
import notblank.boatvote.domain.survey.dto.internal.OptionDTO;
import notblank.boatvote.domain.survey.dto.internal.QuestionDTO;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@AllArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    @Enumerated(EnumType.STRING)
    private QuestionType type;

    private boolean isMultipleAnswer;

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="qid")
    List<Option> optionList = new ArrayList<>();

    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="qid")
    List<Answer> answerList = new ArrayList<>();
}
