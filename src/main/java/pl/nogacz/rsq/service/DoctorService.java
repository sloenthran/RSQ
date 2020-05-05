package pl.nogacz.rsq.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.nogacz.rsq.domain.Doctor;
import pl.nogacz.rsq.repository.DoctorRepository;

import javax.transaction.Transactional;

@Transactional
@Service
@AllArgsConstructor
public class DoctorService {
    private DoctorRepository doctorRepository;

    public Doctor addDoctor(final Doctor doctor) {
        return doctorRepository.save(doctor);
    }
}
