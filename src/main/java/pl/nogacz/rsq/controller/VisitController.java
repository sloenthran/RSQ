package pl.nogacz.rsq.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.nogacz.rsq.domain.Visit;
import pl.nogacz.rsq.dto.AddVisitDto;
import pl.nogacz.rsq.dto.ChangeVisitTimeDto;
import pl.nogacz.rsq.dto.VisitDto;
import pl.nogacz.rsq.exception.DoctorNotFoundException;
import pl.nogacz.rsq.exception.PatientNotFoundException;
import pl.nogacz.rsq.exception.VisitNotFoundException;
import pl.nogacz.rsq.mapper.VisitMapper;
import pl.nogacz.rsq.service.VisitService;

import java.util.List;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class VisitController {
    private VisitMapper mapper;
    private VisitService service;

    @PostMapping("/visit")
    public VisitDto addVisit(@RequestBody AddVisitDto visitDto) throws DoctorNotFoundException, PatientNotFoundException {
        Visit visit = service.addVisit(
                mapper.mapAddVisitDtoToVisit(visitDto)
        );

        return mapper.mapVisitToVisitDto(visit);
    }

    @DeleteMapping("/visit/{id}")
    public void deleteVisit(@PathVariable("id") Long id) throws VisitNotFoundException {
        service.deleteVisit(id);
    }

    @PutMapping("/visit/time")
    public VisitDto changeVisitTime(@RequestBody ChangeVisitTimeDto visitTimeDto) throws VisitNotFoundException {
        Visit visit = service.changeVisitTime(visitTimeDto.getId(), visitTimeDto.getDate());
        return mapper.mapVisitToVisitDto(visit);
    }

    @GetMapping("/visits")
    public List<VisitDto> getVisits() {
        return mapper.mapListVisitToListVisitDto(
                service.getVisits()
        );
    }

    @GetMapping("/visits/{id}")
    public List<VisitDto> getPatientVisits(@PathVariable("id") Long patientId) {
        return null;
    }
}
