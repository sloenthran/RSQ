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
import pl.nogacz.rsq.dto.AddPatientDto;
import pl.nogacz.rsq.dto.DoctorDto;
import pl.nogacz.rsq.dto.PatientDto;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PatientControllerTest {
    @LocalServerPort
    private int serverPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void getPatient() {
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void getPatients() {
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
    public void editPatient() {
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void deletePatient() {
    }
}