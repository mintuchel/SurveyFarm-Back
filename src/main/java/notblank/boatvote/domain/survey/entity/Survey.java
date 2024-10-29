package notblank.boatvote.domain.survey.entity;

import jakarta.persistence.*;
import lombok.*;
import notblank.boatvote.domain.question.entity.Question;
import notblank.boatvote.domain.survey.dto.response.OptionInfoResponse;
import notblank.boatvote.domain.survey.dto.response.QuestionInfoResponse;
import notblank.boatvote.domain.survey.dto.response.SurveyInfoResponse;
import notblank.boatvote.domain.user.entity.User;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    private String title;
    private String description;
    private String imgUrl;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime endAt;
    private int duration;

    private int maxHeadCnt;
    private int currentHeadCnt;

    private int regionCode;
    private int jobCode;
    private int ageCode;
    private int genderCode;

    private int point;

    // FetchType.EAGER로 할까??
    // 여기서 굳이 new 를 해줄 필요가 있을까?
    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="sid")
    private List<Question> questionList = new ArrayList<>();

    @PostPersist
    public void initializeEndAt() {
        endAt = createdAt.plusDays(duration);
    }
}