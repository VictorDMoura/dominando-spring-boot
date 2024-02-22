package academy.devdojo.service;

import academy.devdojo.domain.Producer;
import academy.devdojo.repository.ProducerHardCodedRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

public class ProducerService {
    private ProducerHardCodedRepository producerRepository;

    public ProducerService() {
        this.producerRepository = new ProducerHardCodedRepository();
    }

    public List<Producer> findAll(String name) {
        return producerRepository.findByName(name);
    }

    public Producer save(Producer producer) {
        return producerRepository.save(producer);
    }

    public Optional<Producer> findById(Long id) {
        return producerRepository.findById(id);
    }

    public void deleteById(Long id) {
        var producer = producerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Producer not found to be deleted"));
        producerRepository.deleteById(producer.getId());
    }

    public void update(Producer producerToUpdate) {
        var producer = producerRepository.findById(producerToUpdate.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Producer not found to be updated"));
        producerToUpdate.setCreatedAt(producer.getCreatedAt());
        producerRepository.update(producerToUpdate);
    }
}
