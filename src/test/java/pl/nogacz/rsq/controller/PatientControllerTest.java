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
import pl.nogacz.rsq.domain.Patient;
import pl.nogacz.rsq.dto.AddPatientDto;
import pl.nogacz.rsq.dto.PatientDto;
import pl.nogacz.rsq.repository.PatientRepository;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PatientControllerTest {
    @LocalServerPort
    private int serverPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PatientRepository patientRepository;

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void getPatient() throws Exception {
        //Given
        Patient patient = Patient.builder()
                .name("Radosław")
                .surname("Koparka")
                .address("Budowlana 1, 00-000 Zakopane")
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity httpEntity = new HttpEntity(headers);

        //When
        patientRepository.save(patient);

        ResponseEntity<String> responseEntity = this.restTemplate.exchange("http://localhost:" + this.serverPort + "/patient/1", HttpMethod.GET, httpEntity, String.class);
        PatientDto patientDto = new ObjectMapper().readValue(responseEntity.getBody(), PatientDto.class);

        //Then
        assertEquals(patientDto.getId(), 1L, 0.0);
        assertEquals(patientDto.getName(), "Radosław");
        assertEquals(patientDto.getSurname(), "Koparka");
        assertEquals(patientDto.getAddress(), "Budowlana 1, 00-000 Zakopane");
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void getPatientException() throws Exception {
        //Given
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity httpEntity = new HttpEntity(headers);

        //When

        ResponseEntity<String> responseEntity = this.restTemplate.exchange("http://localhost:" + this.serverPort + "/patient/1", HttpMethod.GET, httpEntity, String.class);

        //Then
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void getPatients() throws Exception {
        //Given
        Patient patientOne = Patient.builder()
                .name("Radosław")
                .surname("Koparka")
                .address("Budowlana 1, 00-000 Zakopane")
                .build();

        Patient patientTwo = Patient.builder()
                .name("Radosław")
                .surname("Koparka")
                .address("Budowlana 1, 00-000 Zakopane")
                .build();

        Patient patientThree = Patient.builder()
                .name("Radosław")
                .surname("Koparka")
                .address("Budowlana 1, 00-000 Zakopane")
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity httpEntity = new HttpEntity(headers);

        //When
        patientRepository.save(patientOne);
        patientRepository.save(patientTwo);
        patientRepository.save(patientThree);

        ResponseEntity<String> responseEntity = this.restTemplate.exchange("http://localhost:" + this.serverPort + "/patients", HttpMethod.GET, httpEntity, String.class);
        List<PatientDto> patientDto = new ObjectMapper().readValue(responseEntity.getBody(), List.class);

        //Then
        assertEquals(patientDto.size(), 3);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void addPatient() throws Exception {
        //Given
        AddPatientDto addPatientDto = AddPatientDto.builder()
                .name("Radosław")
                .surname("Koparka")
                .address("Budowlana 1, 00-000 Zakopane")
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity httpEntity = new HttpEntity(addPatientDto, headers);

        //When
        ResponseEntity<String> responseEntity = this.restTemplate.exchange("http://localhost:" + this.serverPort + "/patient", HttpMethod.POST, httpEntity, String.class);
        PatientDto patientDto = new ObjectMapper().readValue(responseEntity.getBody(), PatientDto.class);

        //Then
        assertEquals(patientDto.getId(), 1L, 0.0);
        assertEquals(patientDto.getName(), "Radosław");
        assertEquals(patientDto.getSurname(), "Koparka");
        assertEquals(patientDto.getAddress(), "Budowlana 1, 00-000 Zakopane");
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void editPatient() throws Exception {
        //Given
        Patient patient = Patient.builder()
                .name("Radosław")
                .surname("Koparka")
                .address("Budowlana 1, 00-000 Zakopane")
                .build();

        PatientDto editPatient = PatientDto.builder()
                .id(1L)
                .name("Zdzisław")
                .surname("Wiertarka")
                .address("Głośna 1, 11-111 Sobótka")
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity httpEntity = new HttpEntity(editPatient, headers);

        //When
        patientRepository.save(patient);

        ResponseEntity<String> responseEntity = this.restTemplate.exchange("http://localhost:" + this.serverPort + "/patient", HttpMethod.PUT, httpEntity, String.class);
        PatientDto patientDto = new ObjectMapper().readValue(responseEntity.getBody(), PatientDto.class);

        //Then
        assertEquals(patientDto.getId(), 1L, 0.0);
        assertEquals(patientDto.getName(), "Zdzisław");
        assertEquals(patientDto.getSurname(), "Wiertarka");
        assertEquals(patientDto.getAddress(), "Głośna 1, 11-111 Sobótka");
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void editPatientException() throws Exception {
        //Given
        PatientDto editPatient = PatientDto.builder()
                .id(1L)
                .name("Zdzisław")
                .surname("Wiertarka")
                .address("Głośna 1, 11-111 Sobótka")
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity httpEntity = new HttpEntity(editPatient, headers);

        //When
        ResponseEntity<String> responseEntity = this.restTemplate.exchange("http://localhost:" + this.serverPort + "/patient", HttpMethod.PUT, httpEntity, String.class);

        //Then
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void deletePatient() throws Exception {
        //Given
        Patient patient = Patient.builder()
                .name("Radosław")
                .surname("Koparka")
                .address("Budowlana 1, 00-000 Zakopane")
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity httpEntity = new HttpEntity(headers);

        //When
        patientRepository.save(patient);

        ResponseEntity<String> responseEntity = this.restTemplate.exchange("http://localhost:" + this.serverPort + "/patient/1", HttpMethod.DELETE, httpEntity, String.class);

        //Then
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void deletePatientException() throws Exception {
        //Given
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity httpEntity = new HttpEntity(headers);

        //When
        ResponseEntity<String> responseEntity = this.restTemplate.exchange("http://localhost:" + this.serverPort + "/patient/1", HttpMethod.DELETE, httpEntity, String.class);

        //Then
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }
}