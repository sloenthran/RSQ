package pl.nogacz.rsq.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ChangeVisitTimeDto {
    private Long id;
    private LocalDateTime date;
}
