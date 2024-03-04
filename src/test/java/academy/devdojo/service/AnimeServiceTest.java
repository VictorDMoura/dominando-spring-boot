package academy.devdojo.service;


import academy.devdojo.commons.AnimeUtils;
import academy.devdojo.domain.Anime;
import academy.devdojo.repository.AnimeHardCodedRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AnimeServiceTest {

    @InjectMocks
    private AnimeService service;
    @Mock
    private AnimeHardCodedRepository repository;
    @InjectMocks
    private AnimeUtils animeUtils;

    private List<Anime> animes;

    @BeforeEach
    void init() {
        animes = animeUtils.newAnimeList();
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
        var name = "One Piece";
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

    @Test
    @Order(4)
    @DisplayName("findById() should return a optional anime when successful")
    void findById_ReturnsOptionalAnime_WhenSuccessful() {
        var id = 1L;
        var animeToFound = animes.get(0);
        BDDMockito.when(repository.findById(id)).thenReturn(Optional.of(animeToFound));

        var animeFound = service.findById(id);

        Assertions.assertThat(animeFound).isNotEmpty()
                .contains(animeToFound);
    }

    @Test
    @Order(5)
    @DisplayName("findById() should return a empty optional when no anime is found")
    void findById_ReturnsEmptyOptional_WhenAnimeIsNotFound() {
        var id = 4L;
        BDDMockito.when(repository.findById(id)).thenReturn(Optional.empty());

        var animeFound = service.findById(id);

        Assertions.assertThat(animeFound).isNotNull().isEmpty();
    }

    @Test
    @Order(6)
    @DisplayName("save() should return a anime when successful")
    void save_ReturnsAnime_WhenSuccessful() {
        var animeToSave = animeUtils.newAnimeToSave();

        BDDMockito.when(repository.save(animeToSave)).thenReturn(animeToSave);

        var anime = service.save(animeToSave);

        Assertions.assertThat(anime)
                .isEqualTo(animeToSave)
                .hasNoNullFieldsOrProperties();
    }

    @Test
    @Order(7)
    @DisplayName("deleteById() should throw a exception when no anime is found")
    void deleteById_ThrowsException_WhenAnimeIsNotFound() {
        var id = 4L;
        BDDMockito.when(repository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThatException().isThrownBy(() -> service.deleteById(id))
                .isInstanceOf(ResponseStatusException.class)
                .withMessageContaining("Anime not found to be deleted")
                .withMessageContaining("NOT_FOUND");
    }

    @Test
    @Order(8)
    @DisplayName("deleteById() should remove a anime when successful")
    void deleteById_RemovesAnime_WhenSuccessful() {
        var id = 1L;
        var animeToDelete = animes.get(0);
        BDDMockito.when(repository.findById(id)).thenReturn(Optional.of(animeToDelete));
        BDDMockito.doNothing().when(repository).deleteById(id);

        Assertions.assertThatNoException().isThrownBy(() -> service.deleteById(id));
    }

    @Test
    @Order(9)
    @DisplayName("update() should throw a exception when no anime is found")
    void update_ThrowsException_WhenAnimeIsNotFound() {
        var animeToUpdate = animes.get(0);
        animeToUpdate.setName("Animplex");
        BDDMockito.when(repository.findById(animeToUpdate.getId())).thenReturn(Optional.empty());

        Assertions.assertThatException().isThrownBy(() -> service.update(animeToUpdate))
                .isInstanceOf(ResponseStatusException.class)
                .withMessageContaining("Anime not found to be updated")
                .withMessageContaining("NOT_FOUND");
    }

    @Test
    @Order(10)
    @DisplayName("update() should update a anime when successful")
    void update_UpdateAnime_WhenSuccessful() {
        var animeToUpdate = animes.get(0);
        animeToUpdate.setName("Naruto Shippuden");

        BDDMockito.when(repository.findById(animeToUpdate.getId())).thenReturn(Optional.of(animeToUpdate));
        BDDMockito.doNothing().when(repository).update(animeToUpdate);

        Assertions.assertThatNoException().isThrownBy(() -> service.update(animeToUpdate));
    }

}