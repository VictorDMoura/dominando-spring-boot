package academy.devdojo.repository;

import academy.devdojo.domain.Producer;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import test.outside.Connection;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Log4j2
public class ProducerHardCodedRepository {

    private static final List<Producer> PRODUCERS = new ArrayList<>();
    @Qualifier(value = "mongoDb")
    private final Connection connection;

    static {
        var mappa = Producer.builder().id(1L).name("Mappa").createdAt(LocalDateTime.now()).build();
        var kyotoAnimatio = Producer.builder().id(2L).name("Kyoto Animation").createdAt(LocalDateTime.now()).build();
        var madhouse = Producer.builder().id(3L).name("Madhouse").createdAt(LocalDateTime.now()).build();

        PRODUCERS.addAll(List.of(mappa, kyotoAnimatio, madhouse));
    }

    public List<Producer> findAll() {
        return PRODUCERS;
    }

    public Optional<Producer> findById(Long id) {
        return PRODUCERS.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    public List<Producer> findByName(String name) {
        log.info(connection);
        return name == null ? findAll() :
                PRODUCERS
                        .stream()
                        .filter(p -> p.getName().equalsIgnoreCase(name))
                        .toList();
    }

    public Producer save(Producer producer) {
        PRODUCERS.add(producer);
        return producer;
    }

    public void deleteById(Long id) {
        PRODUCERS.removeIf(p -> p.getId().equals(id));
    }

    public void update(Producer producer) {
        deleteById(producer.getId());
        save(producer);
    }

}
