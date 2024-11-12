package notblank.surveyfarm.domain.question.entity;

import jakarta.persistence.*;
import lombok.*;
import notblank.surveyfarm.domain.survey.dto.internal.OptionDTO;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name="option_table")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String text;

    public OptionDTO toOptionDto(){
        return OptionDTO.builder()
                .text(text)
                .build();
    }
}
