package notblank.boatvote.domain.question.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Option {
    @Id
    @GeneratedValue
    private int id;

    private String text;

    private int cnt;
}
