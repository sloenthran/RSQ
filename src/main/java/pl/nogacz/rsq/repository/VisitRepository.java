package pl.nogacz.rsq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.nogacz.rsq.domain.Visit;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {
}
