package notblank.boatvote.domain.survey.entity;

import jakarta.persistence.*;
import lombok.*;
import notblank.boatvote.domain.question.entity.Question;
import notblank.boatvote.domain.user.entity.User;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="owner_id", nullable = false)
    private User owner;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime endAt;

    private int point;

    // FetchType.EAGER로 할까??
    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="sid")
    List<Question> questionList = new ArrayList<>();
}
