package pl.nogacz.rsq.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.nogacz.rsq.domain.Visit;
import pl.nogacz.rsq.exception.VisitNotFoundException;
import pl.nogacz.rsq.repository.VisitRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Transactional
@Service
@AllArgsConstructor
public class VisitService {
    private VisitRepository visitRepository;

    public Visit addVisit(final Visit visit) {
        return visitRepository.save(visit);
    }

    public void deleteVisit(final Long id) throws VisitNotFoundException {
        Visit visit = getVisit(id);
        visitRepository.delete(visit);
    }

    public Visit getVisit(final Long id) throws VisitNotFoundException {
        return visitRepository.findById(id).orElseThrow(VisitNotFoundException::new);
    }

    public Visit changeVisitTime(final Long id, final LocalDateTime date) throws VisitNotFoundException {
        Visit visit = getVisit(id);
        visit.setDate(date);
        return visitRepository.save(visit);
    }
}
