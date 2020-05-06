package pl.nogacz.rsq.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.nogacz.rsq.domain.*;
import pl.nogacz.rsq.dto.AddVisitDto;
import pl.nogacz.rsq.dto.ChangeVisitTimeDto;
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

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void addVisit() throws Exception {
        //Given
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
        doctorRepository.save(doctor);
        patientRepository.save(patient);

        ResponseEntity<String> responseEntity = this.restTemplate.exchange("http://localhost:" + this.serverPort + "/visit", HttpMethod.POST, httpEntity, String.class);
        VisitDto responseDto = new ObjectMapper().registerModule(new JavaTimeModule()).readValue(responseEntity.getBody(), VisitDto.class);

        //Then
        assertEquals(responseDto.getId(), 1L, 0.0);
        assertEquals(responseDto.getDoctor(), 1L, 0.0);
        assertEquals(responseDto.getPatient(), 1L, 0.0);
        assertEquals(responseDto.getDate(), date);
        assertEquals(responseDto.getPlace(), VisitPlace.INSTITUTION);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void deleteVisit() {
        //Given
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

        Visit visit = Visit.builder()
                .doctor(doctor)
                .patient(patient)
                .place(VisitPlace.INSTITUTION)
                .date(LocalDateTime.now())
                .build();

        doctor.getVisits().add(visit);
        patient.getVisits().add(visit);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity httpEntity = new HttpEntity(headers);

        //When
        doctorRepository.save(doctor);
        patientRepository.save(patient);
        visitRepository.save(visit);

        ResponseEntity<String> responseEntity = this.restTemplate.exchange("http://localhost:" + this.serverPort + "/visit/1", HttpMethod.DELETE, httpEntity, String.class);

        //Then
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void deleteVisitException() {
        //Given
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity httpEntity = new HttpEntity(headers);

        //When
        ResponseEntity<String> responseEntity = this.restTemplate.exchange("http://localhost:" + this.serverPort + "/visit/1", HttpMethod.DELETE, httpEntity, String.class);

        //Then
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void changeVisitTime() throws Exception {
        //Given
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

        Visit visit = Visit.builder()
                .doctor(doctor)
                .patient(patient)
                .place(VisitPlace.INSTITUTION)
                .date(LocalDateTime.now())
                .build();

        doctor.getVisits().add(visit);
        patient.getVisits().add(visit);

        ChangeVisitTimeDto visitTimeDto = ChangeVisitTimeDto.builder()
                .id(1L)
                .date(LocalDateTime.MIN)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity httpEntity = new HttpEntity(visitTimeDto, headers);

        //When
        doctorRepository.save(doctor);
        patientRepository.save(patient);
        visitRepository.save(visit);

        ResponseEntity<String> responseEntity = this.restTemplate.exchange("http://localhost:" + this.serverPort + "/visit/time", HttpMethod.PUT, httpEntity, String.class);
        VisitDto responseDto = new ObjectMapper().registerModule(new JavaTimeModule()).readValue(responseEntity.getBody(), VisitDto.class);

        //Then
        assertEquals(responseDto.getId(), 1L, 0.0);
        assertEquals(responseDto.getDoctor(), 1L, 0.0);
        assertEquals(responseDto.getPatient(), 1L, 0.0);
        assertEquals(responseDto.getDate(), LocalDateTime.MIN);
        assertEquals(responseDto.getPlace(), VisitPlace.INSTITUTION);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void changeVisitTimeException() throws Exception {
        //Given
        ChangeVisitTimeDto visitTimeDto = ChangeVisitTimeDto.builder()
                .id(1L)
                .date(LocalDateTime.MIN)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity httpEntity = new HttpEntity(visitTimeDto, headers);

        //When
        ResponseEntity<String> responseEntity = this.restTemplate.exchange("http://localhost:" + this.serverPort + "/visit/time", HttpMethod.PUT, httpEntity, String.class);

        //Then
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
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