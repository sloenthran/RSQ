package pl.nogacz.rsq.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AddVisitDto {
    private Long patientId;
    private Long doctorId;
    private LocalDateTime date;
}
