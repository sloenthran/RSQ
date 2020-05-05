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
import pl.nogacz.rsq.domain.DoctorSpecialization;
import pl.nogacz.rsq.dto.AddDoctorDto;
import pl.nogacz.rsq.dto.DoctorDto;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DoctorControllerTest {
    @LocalServerPort
    private int serverPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getDoctor() {
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

        DoctorDto expectedDto = DoctorDto.builder()
                .id(1L)
                .name("Alfred")
                .surname("Dratewka")
                .specialization(DoctorSpecialization.VET)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        //When
        HttpEntity httpEntity = new HttpEntity(addDoctorDto, headers);

        ResponseEntity<String> responseEntity = this.restTemplate.exchange("http://localhost:" + this.serverPort + "/doctor", HttpMethod.POST, httpEntity, String.class);
        DoctorDto doctorDto = new ObjectMapper().readValue(responseEntity.getBody(), DoctorDto.class);

        //Then
        assertEquals(doctorDto, expectedDto);
    }

    @Test
    public void editDoctor() {
    }

    @Test
    public void deleteDoctor() {
    }
}