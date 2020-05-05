package pl.nogacz.rsq.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.nogacz.rsq.domain.Patient;
import pl.nogacz.rsq.exception.PatientNotFoundException;
import pl.nogacz.rsq.repository.PatientRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
@AllArgsConstructor
public class PatientService {
    private PatientRepository patientRepository;

    public Patient addPatient(final Patient patient) {
        return patientRepository.save(patient);
    }

    public List<Patient> getPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatient(final Long id) throws PatientNotFoundException {
        return patientRepository.findById(id).orElseThrow(PatientNotFoundException::new);
    }

    public Patient editPatient(final Patient patient) throws PatientNotFoundException {
        getPatient(patient.getId());

        return patientRepository.save(patient);
    }

    public void deletePatient(final Long id) throws PatientNotFoundException {
        getPatient(id);

        patientRepository.deleteById(id);
    }
}
