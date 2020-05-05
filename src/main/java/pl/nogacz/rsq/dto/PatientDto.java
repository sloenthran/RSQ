package pl.nogacz.rsq.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PatientDto {
    private Long id;
    private String name;
    private String surname;
    private String address;
}
