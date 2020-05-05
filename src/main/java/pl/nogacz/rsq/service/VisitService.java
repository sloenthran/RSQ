package pl.nogacz.rsq.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.nogacz.rsq.domain.Visit;
import pl.nogacz.rsq.repository.VisitRepository;

import javax.transaction.Transactional;

@Transactional
@Service
@AllArgsConstructor
public class VisitService {
    private VisitRepository visitRepository;

    public Visit addVisit(final Visit visit) {
        return visitRepository.save(visit);
    }
}
