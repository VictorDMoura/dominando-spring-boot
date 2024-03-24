package academy.devdojo.repository;

import academy.devdojo.domain.Producer;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import test.outside.Connection;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Log4j2
public class ProducerHardCodedRepository {

    private final ProducerData producerData;
    @Qualifier(value = "connectionMySql")
    private final Connection connection;

    public List<Producer> findAll() {
        return producerData.getProducers();
    }

    public Optional<Producer> findById(Long id) {
        return producerData.getProducers().stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    public List<Producer> findByName(String name) {
        log.info(connection);
        return name == null ? findAll() :
                producerData.getProducers()
                        .stream()
                        .filter(p -> p.getName().equalsIgnoreCase(name))
                        .toList();
    }

    public Producer save(Producer producer) {
        producerData.getProducers().add(producer);
        return producer;
    }

    public void deleteById(Long id) {
        producerData.getProducers().removeIf(p -> p.getId().equals(id));
    }

    public void update(Producer producer) {
        deleteById(producer.getId());
        save(producer);
    }

}
