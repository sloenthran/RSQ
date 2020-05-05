package pl.nogacz.rsq.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.nogacz.rsq.repository.VisitRepository;

import javax.transaction.Transactional;

@Transactional
@Service
@AllArgsConstructor
public class VisitService {
    private VisitRepository visitRepository;
}
