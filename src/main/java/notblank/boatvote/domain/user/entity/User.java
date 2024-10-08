package notblank.boatvote.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import notblank.boatvote.domain.survey.constant.RegionCode;
import notblank.boatvote.domain.survey.entity.Survey;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name="user_table")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;

    private String password;

    private int regionCode;

    private int jobCode;

    private int genderCode;

    private int ageCode;

    @Builder.Default
    @OneToMany(mappedBy = "owner", fetch=FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Survey> requestedSurveyList = new ArrayList<>();

    // 연관관계 편의 메서드
    public void addRequestedSurvey(Survey survey){
        requestedSurveyList.add(survey);
    }
}
