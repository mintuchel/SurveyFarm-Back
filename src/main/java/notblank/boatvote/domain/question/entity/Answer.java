package notblank.boatvote.domain.question.entity;

import jakarta.persistence.*;
import lombok.*;
import notblank.boatvote.domain.user.entity.User;

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
    private User user;

    @ManyToOne
    @JoinColumn(name="qid")
    private Question question;

    // 객관식 답변
    private int mcAnswer;

    // 주관식 답변
    private String saAnswer;
}
