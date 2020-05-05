package pl.nogacz.rsq.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.nogacz.rsq.domain.Patient;
import pl.nogacz.rsq.repository.PatientRepository;

import javax.transaction.Transactional;

@Transactional
@Service
@AllArgsConstructor
public class PatientService {
    private PatientRepository patientRepository;

    public Patient addPatient(final Patient patient) {
        return patientRepository.save(patient);
    }
}
