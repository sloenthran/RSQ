package pl.nogacz.rsq.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.nogacz.rsq.dto.AddPatientDto;
import pl.nogacz.rsq.dto.PatientDto;
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
    public PatientDto getPatient(@PathVariable("id") Long id) {
        return null;
    }

    @GetMapping("/patients")
    public List<PatientDto> getPatients() {
        return null;
    }

    @PostMapping("/patient")
    public PatientDto addPatient(@RequestBody AddPatientDto patientDto) {
        return null;
    }

    @PutMapping("/patient")
    public PatientDto editPatient(@RequestBody PatientDto patientDto) {
        return null;
    }

    @DeleteMapping("/patient/{id}")
    public void deletePatient(@PathVariable("id") Long id) {

    }
}
