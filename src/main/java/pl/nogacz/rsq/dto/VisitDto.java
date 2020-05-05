package pl.nogacz.rsq.dto;

import lombok.*;
import pl.nogacz.rsq.domain.VisitPlace;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class VisitDto {
    private Long id;
    private LocalDateTime time;
    private VisitPlace place;
    private Long doctor;
    private Long patient;
}
