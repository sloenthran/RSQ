package pl.nogacz.rsq.dto;

import lombok.*;
import pl.nogacz.rsq.domain.VisitPlace;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AddVisitDto {
    private Long patient;
    private Long doctor;
    private VisitPlace place;
    private LocalDateTime date;
}
