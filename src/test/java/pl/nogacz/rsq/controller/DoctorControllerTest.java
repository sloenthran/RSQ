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
import pl.nogacz.rsq.exception.DoctorNotFoundException;
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
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
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

    @Test(expected = Exception.class)
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void getDoctorException() throws Exception {
        //Given
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity httpEntity = new HttpEntity(headers);

        //When
        ResponseEntity<String> responseEntity = this.restTemplate.exchange("http://localhost:" + this.serverPort + "/doctor/1", HttpMethod.GET, httpEntity, String.class);
        new ObjectMapper().readValue(responseEntity.getBody(), DoctorDto.class);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
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
    public void editDoctor() {
    }

    @Test
    public void deleteDoctor() {
    }
}