package pl.nogacz.rsq.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.nogacz.rsq.domain.Doctor;
import pl.nogacz.rsq.dto.AddDoctorDto;
import pl.nogacz.rsq.dto.DoctorDto;
import pl.nogacz.rsq.exception.DoctorNotFoundException;
import pl.nogacz.rsq.mapper.DoctorMapper;
import pl.nogacz.rsq.service.DoctorService;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
@RequestMapping(
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class DoctorController {
    private DoctorMapper mapper;
    private DoctorService service;

    @GetMapping("/doctor/{id}")
    public DoctorDto getDoctor(@PathVariable("id") Long id) throws DoctorNotFoundException {
        return mapper.mapDoctorToDoctorDto(
          service.getDoctor(id)
        );
    }

    @PostMapping("/doctor")
    public DoctorDto addDoctor(@RequestBody AddDoctorDto doctorDto) {
        Doctor doctor = service.addDoctor(
                mapper.mapAddDoctorDtoToDoctor(doctorDto)
        );

        return mapper.mapDoctorToDoctorDto(doctor);
    }

    @PutMapping("/doctor")
    public DoctorDto editDoctor(@RequestBody DoctorDto doctorDto) {
        return null;
    }

    @DeleteMapping("/doctor/{id}")
    public void deleteDoctor(@PathVariable("id") Long id) {

    }
}
