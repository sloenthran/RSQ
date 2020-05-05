package pl.nogacz.rsq.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.nogacz.rsq.domain.Visit;
import pl.nogacz.rsq.dto.AddVisitDto;
import pl.nogacz.rsq.dto.VisitDto;
import pl.nogacz.rsq.exception.DoctorNotFoundException;
import pl.nogacz.rsq.exception.PatientNotFoundException;
import pl.nogacz.rsq.service.DoctorService;
import pl.nogacz.rsq.service.PatientService;

@Component
@AllArgsConstructor
public class VisitMapper {
    private DoctorService doctorService;
    private PatientService patientService;

    public Visit mapAddVisitDtoToVisit(final AddVisitDto visitDto) throws DoctorNotFoundException, PatientNotFoundException {
        return Visit.builder()
                .doctor(
                        doctorService.getDoctor(visitDto.getDoctor())
                )
                .patient(
                        patientService.getPatient(visitDto.getPatient())
                )
                .place(visitDto.getPlace())
                .date(visitDto.getDate())
                .build();
    }

    public VisitDto mapVisitToVisitDto(final Visit visit) {
        return VisitDto.builder()
                .id(visit.getId())
                .doctor(visit.getDoctor().getId())
                .patient(visit.getPatient().getId())
                .place(visit.getPlace())
                .date(visit.getDate())
                .build();
    }
}
