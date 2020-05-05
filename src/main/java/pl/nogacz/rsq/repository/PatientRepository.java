package pl.nogacz.rsq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.nogacz.rsq.domain.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
}
