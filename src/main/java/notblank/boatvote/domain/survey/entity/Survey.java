package notblank.boatvote.domain.survey.entity;

import jakarta.persistence.*;
import lombok.*;
import notblank.boatvote.domain.question.entity.Question;
import notblank.boatvote.domain.user.entity.User;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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

    // db에 저장되는 순간 자동으로 초기화됨
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime endAt;

    private int headCnt;

    private int regionCode;

    private int jobCode;

    private int genderCode;

    private int ageCode;

    private int point;

    private String description;
    // FetchType.EAGER로 할까??
    // 여기서 굳이 new 를 해줄 필요가 있을까?
    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="sid")
    private List<Question> questionList = new ArrayList<>();

    public void setEndAt(int duration) {
        this.endAt = this.createdAt.plusDays(duration);
    }
}
