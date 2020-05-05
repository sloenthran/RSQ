package pl.nogacz.rsq.mapper;

import org.springframework.stereotype.Component;
import pl.nogacz.rsq.domain.Doctor;
import pl.nogacz.rsq.dto.AddDoctorDto;
import pl.nogacz.rsq.dto.DoctorDto;

@Component
public class DoctorMapper {
    public Doctor mapAddDoctorDtoToDoctor(final AddDoctorDto doctorDto) {
        return Doctor.builder()
                .name(doctorDto.getName())
                .surname(doctorDto.getSurname())
                .specialization(doctorDto.getSpecialization())
                .build();
    }

    public DoctorDto mapDoctorToDoctorDto(final Doctor doctor) {
        return DoctorDto.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .surname(doctor.getSurname())
                .specialization(doctor.getSpecialization())
                .build();
    }

    public Doctor mapDoctorDtoToDoctor(final DoctorDto doctorDto) {
        return Doctor.builder()
                .id(doctorDto.getId())
                .name(doctorDto.getName())
                .surname(doctorDto.getSurname())
                .specialization(doctorDto.getSpecialization())
                .build();
    }
}
