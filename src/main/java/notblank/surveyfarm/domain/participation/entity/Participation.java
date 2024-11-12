package notblank.surveyfarm.domain.participation.entity;

import jakarta.persistence.*;
import lombok.*;
import notblank.surveyfarm.domain.survey.entity.Survey;
import notblank.surveyfarm.domain.user.entity.User;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Participation {
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