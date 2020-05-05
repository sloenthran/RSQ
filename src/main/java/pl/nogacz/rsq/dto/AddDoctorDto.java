package pl.nogacz.rsq.dto;

import lombok.*;
import pl.nogacz.rsq.domain.DoctorSpecialization;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AddDoctorDto {
    private String name;
    private String surname;
    private DoctorSpecialization specialization;
}
