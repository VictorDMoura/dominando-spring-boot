package academy.devdojo.service;


import academy.devdojo.domain.Producer;
import academy.devdojo.repository.ProducerHardCodedRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    @DisplayName("findAll() should a list with all producers when successful")
    void findAll_ReturnsAllProducers_WhenSuccessful() {
        BDDMockito.when(repository.findByName(null)).thenReturn(this.producers);
        var producers = service.findAll(null);
        Assertions.assertThat(producers).hasSameElementsAs(this.producers);
    }

    @Test
    @Order(2)
    @DisplayName("findAll() should returns a list with found producer when name is not null")
    void findAll_ReturnsAllProducers_WhenNameIsNotNull() {
        var name = "Ufotable";
        var producerFound = this.producers.stream()
                .filter(producer -> producer.getName().equals(name))
                .toList();
        BDDMockito.when(repository.findByName(name)).thenReturn(producerFound);
        var producers = service.findAll(name);
        Assertions.assertThat(producers).hasSize(1)
                .contains(producerFound.get(0));
    }

    @Test
    @Order(3)
    @DisplayName("findAll() should a empty list when no producer is found by name")
    void findAll_ReturnsEmptyList_WhenNameIsNotNull() {
        var name = "x";
        BDDMockito.when(repository.findByName(name)).thenReturn(Collections.emptyList());

        var producers = service.findAll(name);

        Assertions.assertThat(producers).isNotNull().isEmpty();
    }

    @Test
    @Order(4)
    @DisplayName("findById() should return a optional producer when id exists")
    void findById_ReturnsAProducer_WhenIdExists() {
        var id = 1L;
        var producerExpected = this.producers.get(0);
        BDDMockito.when(repository.findById(id)).thenReturn(Optional.of(producerExpected));

        var producerOptional = service.findById(id);

        Assertions.assertThat(producerOptional).isPresent().contains(producerExpected);
    }

    @Test
    @Order(5)
    @DisplayName("findById() should return a empty optional producer when id does not exists")
    void findById_ReturnsEmptyOptional_WhenIdDoesNotExists() {
        var id = 99L;
        var producerExpected = this.producers.stream()
                .filter(producer -> producer.getId().equals(id))
                .findFirst();
        BDDMockito.when(repository.findById(id)).thenReturn(producerExpected);

        var producerOptional = service.findById(id);

        Assertions.assertThat(producerOptional).isNotNull().isEmpty();
    }

    @Test
    @Order(6)
    @DisplayName("save() should create a producer when successful")
    void save_CreatesProducer_WhenSuccessful() {
        var producerToBeSaved = Producer.builder()
                .id(99L).name("MAPPA")
                .createdAt(LocalDateTime.now())
                .build();
        BDDMockito.when(repository.save(producerToBeSaved)).thenReturn(producerToBeSaved);

        var producer = service.save(producerToBeSaved);

        Assertions.assertThat(producer)
                .isEqualTo(producerToBeSaved)
                .hasNoNullFieldsOrProperties();
    }

    @Test
    @Order(7)
    @DisplayName("deleteById() should remove a producer when successful")
    void deleteById_RemovesProducer_WhenSuccessful() {
        var id = 1L;
        var producerToDelete = this.producers.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
        BDDMockito.when(repository.findById(id)).thenReturn(producerToDelete);
        BDDMockito.doNothing().when(repository).deleteById(id);

        Assertions.assertThatNoException().isThrownBy(() -> service.deleteById(id));
    }

    @Test
    @Order(8)
    @DisplayName("deleteById() should throw a exception when producer is not found")
    void deleteById_ThrowsException_WhenProducerIsNotFound() {
        var id = 99L;
        BDDMockito.when(repository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThatException().isThrownBy(() -> service.deleteById(id))
                .withMessageContaining("Producer not found to be deleted")
                .withMessageContaining("NOT_FOUND")
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @Order(9)
    @DisplayName("update() should update a producer when successful")
    void update_UpdateProducer_WhenSuccessful() {
        var producerToUpdate = this.producers.get(0);
        producerToUpdate.setName("Aniplex");

        BDDMockito.when(repository.findById(producerToUpdate.getId())).thenReturn(Optional.of(producerToUpdate));
        BDDMockito.doNothing().when(repository).update(producerToUpdate);

        Assertions.assertThatNoException().isThrownBy(() -> service.update(producerToUpdate));
    }

    @Test
    @Order(10)
    @DisplayName("update() should throw a exception when producer is not found")
    void update_ThrowsException_WhenProducerIsNotFound() {
        var producerToUpdate = this.producers.get(0);
        producerToUpdate.setName("Aniplex");

        BDDMockito.when(repository.findById(producerToUpdate.getId())).thenReturn(Optional.empty());

        Assertions.assertThatException().isThrownBy(() -> service.update(producerToUpdate))
                .withMessageContaining("Producer not found to be updated")
                .withMessageContaining("NOT_FOUND")
                .isInstanceOf(ResponseStatusException.class);
    }




}