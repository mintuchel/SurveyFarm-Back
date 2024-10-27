package notblank.boatvote.domain.participatedSurvey.entity;

import jakarta.persistence.*;
import lombok.*;
import notblank.boatvote.domain.survey.entity.Survey;
import notblank.boatvote.domain.user.entity.User;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ParticipatedSurvey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="uid")
    private User user;

    @ManyToOne
    @JoinColumn(name="sid")
    private Survey survey;

    @CreationTimestamp
    private LocalDateTime participatedAt;
}