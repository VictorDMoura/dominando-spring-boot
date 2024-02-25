package academy.devdojo.service;


import academy.devdojo.domain.Anime;
import academy.devdojo.repository.AnimeHardCodedRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AnimeServiceTest {

    @InjectMocks
    private AnimeService service;
    @Mock
    private AnimeHardCodedRepository repository;

    private List<Anime> animes;

    @BeforeEach
    void init() {
        var naruto = Anime.builder().id(1L).name("Naruto").build();
        var dragonBall = Anime.builder().id(2L).name("Dragon Ball").build();
        var bleach = Anime.builder().id(3L).name("Bleach").build();

        animes = new ArrayList<>(List.of(naruto, dragonBall, bleach));
    }

    @Test
    @Order(1)
    @DisplayName("findAll() should a list with all animes when successful")
    void findAll_ReturnsAllAnimes_WhenSuccessful() {
        BDDMockito.when(repository.findByName(null)).thenReturn(this.animes);

        var animes = service.findAll(null);

        Assertions.assertThat(animes).hasSameElementsAs(this.animes);
    }

    @Test
    @Order(2)
    @DisplayName("findAll() should returns a list with found anime when name is not null")
    void findAll_ReturnsAllAnimes_WhenNameIsNotNull() {
        var name = "Naruto";
        var animeToFound = this.animes.stream()
                .filter(anime -> anime.getName().equals(name))
                .toList();

        BDDMockito.when(repository.findByName(name)).thenReturn(animeToFound);

        var animeFound = service.findAll(name);

        Assertions.assertThat(animeFound).hasSize(1)
                .contains(animeToFound.get(0));
    }

    @Test
    @Order(3)
    @DisplayName("findAll() should a empty list when no anime is found by name")
    void findAll_ReturnsEmptyList_WhenNameIsNotNull() {
        var name = "One Piece";
        BDDMockito.when(repository.findByName(name)).thenReturn(List.of());

        var animes = service.findAll(name);

        Assertions.assertThat(animes).isNotNull().isEmpty();
    }



}