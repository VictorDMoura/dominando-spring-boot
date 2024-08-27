package academy.devdojo.service;

import academy.devdojo.commons.AnimeUtils;
import academy.devdojo.domain.Anime;
import academy.devdojo.repository.AnimeHardCodedRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AnimeServiceTest {

    @InjectMocks
    private AnimeService service;
    @InjectMocks
    private AnimeUtils animeUtils;
    @Mock
    private AnimeHardCodedRepository repository;
    private List<Anime> animeList;

    @BeforeEach
    void init() {
        animeList = animeUtils.newAnimeList();
    }

    @Test
    @Order(1)
    @DisplayName("findAll() returns a list with all animes")
    void findAll_ReturnsAllAnimes_WhenSuccessful() {
        BDDMockito.when(repository.findByName(null)).thenReturn(animeList);
        var animes = service.listAll(null);
        Assertions.assertThat(animes).isNotNull().hasSameElementsAs(animeList);
    }

    @Test
    @Order(2)
    @DisplayName("findAll() returns a list with found animes when name is not null")
    void findAll_ReturnsFoundAnime_WhenNameIsPassedAndFound() {
        String name = "Mashle";
        var animeFound = animeList.stream()
                .filter(animes -> animes.getName().equalsIgnoreCase(name))
                .toList();
        BDDMockito.when(repository.findByName(name)).thenReturn(animeFound);
        var animes = service.listAll(name);
        Assertions.assertThat(animes).hasSize(1).contains(animeFound.get(0));
    }

    @Test
    @Order(3)
    @DisplayName("findAll() returns an empty list when no anime is found by name")
    void findAll_ReturnsEmptyList_WhenNoNameIsFound() {
        String name = "x";
        BDDMockito.when(repository.findByName(name)).thenReturn(Collections.emptyList());
        var anime = service.listAll(name);
        Assertions.assertThat(anime).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("findById() returns a anime when id exists")
    @Order(4)
    void findById_ReturnsOptionalAnime_WhenIdExists() {
        var id = 1L;
        var animeOptional = animeList.stream()
                .filter(anime -> anime.getId().equals(id))
                .findFirst();
        BDDMockito.when(repository.findById(id)).thenReturn(animeOptional);
        var animeFound = service.findById(id);

        Assertions.assertThat(animeFound).isNotNull().isEqualTo(animeOptional.get());
    }

    @Test
    @DisplayName("findById() throw ResponseStatusException when no anime is found")
    @Order(5)
    void findById_ThrowsResponseStatusException_WhenNoAnimeIsFound() {
        var id = 99L;
        BDDMockito.when(repository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.findById(id))
                .isInstanceOf(ResponseStatusException.class);

    }

    @Test
    @DisplayName("save() creates anime")
    @Order(6)
    void save_CreatesAnime_WhenSuccessful() {
        var animeToSave = animeUtils.newAnimeToSave();
        BDDMockito.when(repository.save(animeToSave)).thenReturn(animeToSave);

        var anime = service.save(animeToSave);

        Assertions.assertThat(anime).isEqualTo(animeToSave)
                .hasNoNullFieldsOrProperties();
    }

    @Test
    @DisplayName("delete() removes a anime")
    @Order(7)
    void delete_RemovesAnime_WhenSuccessful() {
        var id = 1L;
        var animeToDelete = animeList.get(0);

        BDDMockito.when(repository.findById(id)).thenReturn(Optional.of(animeToDelete));
        BDDMockito.doNothing().when(repository).delete(animeToDelete);

        Assertions.assertThatNoException().isThrownBy(() -> service.deleteById(id));
    }

    @Test
    @DisplayName("delete() throw ResponseStatusException when no anime is found")
    @Order(8)
    void delete_ThrowsResponseStatusException_WhenNoAnimeIsFound() {
        var id = 99L;

        BDDMockito.when(repository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.deleteById(id))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @DisplayName("update() update a anime")
    @Order(9)
    void update_UpdateAnime_WhenSuccessful() {
        var animeToSave = animeList.get(0);
        animeToSave.setName("One Piece");

        BDDMockito.when(repository.findById(animeToSave.getId())).thenReturn(Optional.of(animeList.get(0)));
        BDDMockito.doNothing().when(repository).update(animeToSave);

        Assertions.assertThatNoException().isThrownBy(() -> service.update(animeToSave));
    }

    @Test
    @DisplayName("update() throw ResponseStatusException when no anime is found")
    @Order(10)
    void update_ThrowsResponseStatusException_WhenNoAnimeIsFound() {
        var animeToSave = animeList.get(0);
        animeToSave.setName("One Piece");

        BDDMockito.when(repository.findById(animeToSave.getId())).thenReturn(Optional.empty());

        Assertions.assertThatException()
                .isThrownBy(() -> service.deleteById(animeToSave.getId()))
                .isInstanceOf(ResponseStatusException.class);
    }

}