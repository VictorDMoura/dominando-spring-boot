package academy.devdojo.animeservice.service;

import academy.devdojo.animeservice.domain.Producer;
import academy.devdojo.animeservice.repository.ProducerHardCodedRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProducerServiceTest {

    @InjectMocks
    private ProducerService service;
    @Mock
    private ProducerHardCodedRepository repository;
    private List<Producer> producers;

    @BeforeEach
    void init() {
        var ufotable = Producer.builder().id(1L).name("Ufotable").createdAt(LocalDateTime.now()).build();
        var witStudio = Producer.builder().id(2L).name("Wit Studio").createdAt(LocalDateTime.now()).build();
        var studioGhibli = Producer.builder().id(3L).name("Studio Ghibli").createdAt(LocalDateTime.now()).build();
        producers = new ArrayList<>(List.of(ufotable, witStudio, studioGhibli));
    }

    @Test
    @Order(1)
    @DisplayName("findAll() returns a list with all producers")
    void findAll_ReturnsAllProducers_WhenSuccessful() {
        BDDMockito.when(repository.findByName(null)).thenReturn(producers);
        var producers = service.listAll(null);
        Assertions.assertThat(producers).hasSameElementsAs(producers);
    }

    @Test
    @Order(2)
    @DisplayName("findAll() returns a list with found producers when name is not null")
    void findAll_ReturnsFoundProducers_WhenNameIsPassedAndFound() {
        String name = "Ufotable";
        var producerFound = producers.stream().filter(producer -> producer.getName().equalsIgnoreCase(name)).toList();
        BDDMockito.when(repository.findByName(name)).thenReturn(producerFound);
        var producer = service.listAll(name);
        Assertions.assertThat(producer).hasSize(1).contains(producerFound.get(0));
    }

    @Test
    @Order(3)
    @DisplayName("findAll() returns an empty list when no producer is found by name")
    void findAll_ReturnsEmptyList_WhenNoNameIsFound() {
        String name = "x";
        BDDMockito.when(repository.findByName(name)).thenReturn(Collections.emptyList());
        var producer = service.listAll(name);
        Assertions.assertThat(producer).isNotNull().isEmpty();
    }
}