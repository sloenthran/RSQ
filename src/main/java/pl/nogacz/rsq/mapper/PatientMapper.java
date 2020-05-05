package pl.nogacz.rsq.mapper;

import org.springframework.stereotype.Component;
import pl.nogacz.rsq.domain.Patient;
import pl.nogacz.rsq.dto.AddPatientDto;
import pl.nogacz.rsq.dto.PatientDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PatientMapper {
    public Patient mapAddPatientDtoToPatient(final AddPatientDto patientDto) {
        return Patient.builder()
                .name(patientDto.getName())
                .surname(patientDto.getSurname())
                .address(patientDto.getAddress())
                .build();
    }

    public PatientDto mapPatientToPatientDto(final Patient patient) {
        return PatientDto.builder()
                .id(patient.getId())
                .name(patient.getName())
                .surname(patient.getSurname())
                .address(patient.getAddress())
                .build();
    }

    public List<PatientDto> mapListPatientToListPatientDto(final List<Patient> patients) {
        return patients.stream()
                .map(this::mapPatientToPatientDto)
                .collect(Collectors.toList());
    }
}
