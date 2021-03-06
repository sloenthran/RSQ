package pl.nogacz.rsq.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.nogacz.rsq.domain.Patient;
import pl.nogacz.rsq.dto.AddPatientDto;
import pl.nogacz.rsq.dto.PatientDto;
import pl.nogacz.rsq.exception.PatientNotFoundException;
import pl.nogacz.rsq.mapper.PatientMapper;
import pl.nogacz.rsq.service.PatientService;

import java.util.List;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class PatientController {
    private PatientMapper mapper;
    private PatientService service;

    @GetMapping("/patient/{id}")
    public PatientDto getPatient(@PathVariable("id") Long id) throws PatientNotFoundException {
        return mapper.mapPatientToPatientDto(
                service.getPatient(id)
        );
    }

    @GetMapping("/patients")
    public List<PatientDto> getPatients() {
        return mapper.mapListPatientToListPatientDto(
                service.getPatients()
        );
    }

    @PostMapping("/patient")
    public PatientDto addPatient(@RequestBody AddPatientDto patientDto) {
        Patient patient = service.addPatient(
                mapper.mapAddPatientDtoToPatient(patientDto)
        );

        return mapper.mapPatientToPatientDto(patient);
    }

    @PutMapping("/patient")
    public PatientDto editPatient(@RequestBody PatientDto patientDto) throws PatientNotFoundException {
        Patient patient = service.editPatient(
                mapper.mapPatientDtoToPatient(patientDto)
        );

        return mapper.mapPatientToPatientDto(patient);
    }

    @DeleteMapping("/patient/{id}")
    public void deletePatient(@PathVariable("id") Long id) throws PatientNotFoundException {
        service.deletePatient(id);
    }
}
