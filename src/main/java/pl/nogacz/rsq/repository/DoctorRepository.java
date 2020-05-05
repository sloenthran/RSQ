package pl.nogacz.rsq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.nogacz.rsq.domain.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
