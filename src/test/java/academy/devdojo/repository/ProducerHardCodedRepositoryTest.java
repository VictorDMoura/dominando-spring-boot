package academy.devdojo.repository;

import academy.devdojo.domain.Producer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProducerHardCodedRepositoryTest {

    @InjectMocks
    private ProducerHardCodedRepository repository;
    @Mock
    private ProducerData producerData;

    private List<Producer> producers;

    @BeforeEach
    void init(){
        var ufotable = Producer.builder().id(1L).name("Ufotable").createdAt(LocalDateTime.now()).build();
        var witStudio = Producer.builder().id(2L).name("Wit Studio").createdAt(LocalDateTime.now()).build();
        var studioGhibli = Producer.builder().id(3L).name("Studio Ghibli").createdAt(LocalDateTime.now()).build();

        producers = List.of(ufotable, witStudio, studioGhibli);
        BDDMockito.when(producerData.getProducers()).thenReturn(producers);
    }


    @Test
    @Order(1)
    @DisplayName("findAll() should a list with all producers when successful")
    void findAll_ReturnsAllProducers_WhenSuccessful() {
        var producers = repository.findAll();
        Assertions.assertThat(producers).hasSameElementsAs(this.producers);
    }

    @Test
    @Order(2)
    @DisplayName("findById() returns an object with given id")
    void findById_ReturnsAProducers_WhenSuccessful() {
        var producerOptional = repository.findById(3L);
        Assertions.assertThat(producerOptional).contains(producers.get(2));

    }

    @Test
    @Order(3)
    @DisplayName("findByName() returns all producers when name is null")
    void findByName_ReturnsAllProducers_WhenNameIsNull() {
        var producers = repository.findByName(null);
        Assertions.assertThat(producers).hasSameElementsAs(this.producers);

    }

    @Test
    @Order(4)
    @DisplayName("findByName() return list with filtered producers when name is not null")
    void findByName_ReturnsFilteredProducers_WhenNameIsNotNull() {
        var producers = repository.findByName("Ufotable");
        Assertions.assertThat(producers).hasSize(1).contains(this.producers.get(0));

    }

    @Test
    @Order(5)
    @DisplayName("findByName() returns empty list when list when no producer is found")
    void findByName_ReturnsEmptyList_WhenNoProducerIsFound() {
        var producers = repository.findByName("xxxx");
        Assertions.assertThat(producers).isNotNull().isEmpty();

    }
}
