package pl.nogacz.rsq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.nogacz.rsq.domain.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
