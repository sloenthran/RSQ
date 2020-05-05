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
import pl.nogacz.rsq.dto.AddDoctorDto;
import pl.nogacz.rsq.dto.DoctorDto;
import pl.nogacz.rsq.repository.DoctorRepository;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DoctorControllerTest {
    @LocalServerPort
    private int serverPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private DoctorRepository doctorRepository;

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void getDoctor() throws Exception {
        //Given
        Doctor doctor = Doctor.builder()
                .name("Alfred")
                .surname("Dratewka")
                .specialization(DoctorSpecialization.VET)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity httpEntity = new HttpEntity(headers);

        //When
        doctorRepository.save(doctor);

        ResponseEntity<String> responseEntity = this.restTemplate.exchange("http://localhost:" + this.serverPort + "/doctor/1", HttpMethod.GET, httpEntity, String.class);
        DoctorDto doctorDto = new ObjectMapper().readValue(responseEntity.getBody(), DoctorDto.class);

        //Then
        assertEquals(doctorDto.getId(), 1L, 0.0);
        assertEquals(doctorDto.getName(), "Alfred");
        assertEquals(doctorDto.getSurname(), "Dratewka");
        assertEquals(doctorDto.getSpecialization(), DoctorSpecialization.VET);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void getDoctorException() throws Exception {
        //Given
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity httpEntity = new HttpEntity(headers);

        //When
        ResponseEntity<String> responseEntity = this.restTemplate.exchange("http://localhost:" + this.serverPort + "/doctor/1", HttpMethod.GET, httpEntity, String.class);

        //Then
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void addDoctor() throws Exception {
        //Given
        AddDoctorDto addDoctorDto = AddDoctorDto.builder()
                .name("Alfred")
                .surname("Dratewka")
                .specialization(DoctorSpecialization.VET)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity httpEntity = new HttpEntity(addDoctorDto, headers);

        //When
        ResponseEntity<String> responseEntity = this.restTemplate.exchange("http://localhost:" + this.serverPort + "/doctor", HttpMethod.POST, httpEntity, String.class);
        DoctorDto doctorDto = new ObjectMapper().readValue(responseEntity.getBody(), DoctorDto.class);

        //Then
        assertEquals(doctorDto.getId(), 1L, 0.0);
        assertEquals(doctorDto.getName(), "Alfred");
        assertEquals(doctorDto.getSurname(), "Dratewka");
        assertEquals(doctorDto.getSpecialization(), DoctorSpecialization.VET);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void editDoctor() throws Exception {
        //Given
        Doctor doctor = Doctor.builder()
                .name("Alfred")
                .surname("Dratewka")
                .specialization(DoctorSpecialization.VET)
                .build();

        DoctorDto editDoctorDto = DoctorDto.builder()
                .id(1L)
                .name("Alfred")
                .surname("Zębacz")
                .specialization(DoctorSpecialization.DENTIST)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity httpEntity = new HttpEntity(editDoctorDto, headers);

        //When
        doctorRepository.save(doctor);

        ResponseEntity<String> responseEntity = this.restTemplate.exchange("http://localhost:" + this.serverPort + "/doctor", HttpMethod.PUT, httpEntity, String.class);
        DoctorDto doctorDto = new ObjectMapper().readValue(responseEntity.getBody(), DoctorDto.class);

        //Then
        assertEquals(doctorDto.getId(), 1L, 0.0);
        assertEquals(doctorDto.getName(), "Alfred");
        assertEquals(doctorDto.getSurname(), "Zębacz");
        assertEquals(doctorDto.getSpecialization(), DoctorSpecialization.DENTIST);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void editDoctorException() throws Exception {
        //Given
        DoctorDto editDoctorDto = DoctorDto.builder()
                .id(1L)
                .name("Alfred")
                .surname("Zębacz")
                .specialization(DoctorSpecialization.DENTIST)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity httpEntity = new HttpEntity(editDoctorDto, headers);

        //When
        ResponseEntity<String> responseEntity = this.restTemplate.exchange("http://localhost:" + this.serverPort + "/doctor", HttpMethod.PUT, httpEntity, String.class);

        //Then
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void deleteDoctor() {
        //Given
        Doctor doctor = Doctor.builder()
                .name("Alfred")
                .surname("Dratewka")
                .specialization(DoctorSpecialization.VET)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity httpEntity = new HttpEntity(headers);

        //When
        doctorRepository.save(doctor);

        ResponseEntity<String> responseEntity = this.restTemplate.exchange("http://localhost:" + this.serverPort + "/doctor/1", HttpMethod.DELETE, httpEntity, String.class);

        //Then
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void deleteDoctorException() {
        //Given
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity httpEntity = new HttpEntity(headers);

        //When

        ResponseEntity<String> responseEntity = this.restTemplate.exchange("http://localhost:" + this.serverPort + "/doctor/1", HttpMethod.DELETE, httpEntity, String.class);

        //Then
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }
}