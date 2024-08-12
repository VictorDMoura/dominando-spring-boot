package academy.devdojo.animeservice.service;

import academy.devdojo.animeservice.domain.Producer;
import academy.devdojo.animeservice.exception.NotFoundException;
import academy.devdojo.animeservice.repository.ProducerHardCodedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProducerService {

    private final ProducerHardCodedRepository repository;


    public List<Producer> listAll(String name) {
        return repository.findByName(name);
    }

    public Producer save(Producer producer) {
        return repository.save(producer);
    }

    public Optional<Producer> findById(Long id) {
        return repository.findById(id);
    }

    public void delete(Long id) {
        var producer = findById(id)
                .orElseThrow(() -> new NotFoundException("Producer not found to be deleted"));
        repository.delete(producer);
    }

    public void update(Producer producerToUpdate) {
        var producer = findById(producerToUpdate.getId())
                .orElseThrow(() -> new NotFoundException("Producer not found to be updated"));
        producerToUpdate.setCreatedAt(producer.getCreatedAt());
        repository.update(producerToUpdate);
    }
}
