package pl.nogacz.rsq.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.nogacz.rsq.dto.AddVisitDto;
import pl.nogacz.rsq.dto.ChangeVisitTimeDto;
import pl.nogacz.rsq.dto.VisitDto;
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
    public VisitDto addVisit(@RequestBody AddVisitDto visitDto) {
        return null;
    }

    @DeleteMapping("/visit/{id}")
    public void deleteVisit(@PathVariable("id") Long id) {

    }

    @PutMapping("/visit/time")
    public VisitDto changeVisitTime(@RequestBody ChangeVisitTimeDto visitTimeDto) {
        return null;
    }

    @GetMapping("/visits")
    public List<VisitDto> getVisits() {
        return null;
    }

    @GetMapping("/visits/{id}")
    public List<VisitDto> getPatientVisits(@PathVariable("id") Long patientId) {
        return null;
    }
}
