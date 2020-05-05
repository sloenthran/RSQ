package pl.nogacz.rsq.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.nogacz.rsq.domain.Doctor;
import pl.nogacz.rsq.domain.DoctorSpecialization;
import pl.nogacz.rsq.domain.Patient;
import pl.nogacz.rsq.domain.VisitPlace;
import pl.nogacz.rsq.dto.AddVisitDto;
import pl.nogacz.rsq.dto.VisitDto;
import pl.nogacz.rsq.repository.DoctorRepository;
import pl.nogacz.rsq.repository.PatientRepository;
import pl.nogacz.rsq.repository.VisitRepository;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VisitControllerTest {
    @LocalServerPort
    private int serverPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private VisitRepository visitRepository;

    public void addDoctorAndPatient() {
        Doctor doctor = Doctor.builder()
                .name("Alfred")
                .surname("Dratewka")
                .specialization(DoctorSpecialization.PSYCHOLOGIST)
                .build();

        Patient patient = Patient.builder()
                .name("Radosław")
                .surname("Koparka")
                .address("Budowlana 1, 00-000 Zakopane")
                .build();

        doctorRepository.save(doctor);
        patientRepository.save(patient);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void addVisit() throws Exception {
        //Given
        addDoctorAndPatient();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        LocalDateTime date = LocalDateTime.now();

        AddVisitDto visitDto = AddVisitDto.builder()
                .doctor(1L)
                .patient(1L)
                .place(VisitPlace.INSTITUTION)
                .date(date)
                .build();

        HttpEntity httpEntity = new HttpEntity(visitDto, headers);

        //When
        ResponseEntity<String> responseEntity = this.restTemplate.exchange("http://localhost:" + this.serverPort + "/visit", HttpMethod.POST, httpEntity, String.class);
        VisitDto responseDto = new ObjectMapper().readValue(responseEntity.getBody(), VisitDto.class);

        //Then
        assertEquals(responseDto.getId(), 1L, 0.0);
        assertEquals(responseDto.getDoctor(), 1L, 0.0);
        assertEquals(responseDto.getPatient(), 1L, 0.0);
        assertEquals(responseDto.getTime(), date);
        assertEquals(responseDto.getPlace(), VisitPlace.INSTITUTION);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void deleteVisit() {
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void changeVisitTime() {
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void getVisits() {
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void getPatientVisits() {
    }
}