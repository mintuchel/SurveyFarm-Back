package notblank.surveyfarm.domain.answer.entity;

import jakarta.persistence.*;
import lombok.*;
import notblank.surveyfarm.domain.question.entity.Question;
import notblank.surveyfarm.domain.user.entity.User;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="uid")
    private User participant;

    @ManyToOne
    @JoinColumn(name="qid")
    private Question question;

    private String text;
}
