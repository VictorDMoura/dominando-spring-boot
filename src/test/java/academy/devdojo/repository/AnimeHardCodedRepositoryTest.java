package academy.devdojo.repository;


import academy.devdojo.domain.Anime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class AnimeHardCodedRepositoryTest {

    @InjectMocks
    private AnimeHardCodedRepository repository;

    @Mock
    private AnimeData animeData;

    private List<Anime> animes;

    @BeforeEach
    void init() {
        var onePunchMan = Anime.builder().id(1L).name("One Punch Man").createdAt(LocalDateTime.now()).build();
        var onePiece = Anime.builder().id(2L).name("One Piece").createdAt(LocalDateTime.now()).build();
        var demonSlayer = Anime.builder().id(3L).name("Demon Slayer").createdAt(LocalDateTime.now()).build();

        animes = new ArrayList<>(List.of(onePunchMan, onePiece, demonSlayer));

        BDDMockito.when(animeData.getAnimes()).thenReturn(animes);
    }


    @Test
    @DisplayName("findAll() returns list of anime when successful")
    void findAll_ReturnsListOfAnime_WhenSuccessful() {
        var animes = repository.findAll();

        Assertions.assertThat(animes).isNotNull().isNotEmpty().hasSize(this.animes.size());
    }

    @Test
    @DisplayName("findById() returns an anime when successful")
    void findById_ReturnsAnAnime_WhenSuccessful() {
        var anime = repository.findById(1L);

        Assertions.assertThat(anime)
                .isNotNull()
                .contains(animes.get(0));

    }

    @Test
    @DisplayName("findById() returns empty optional when anime is not found")
    void findById_ReturnsEmptyOptional_WhenAnimeIsNotFound() {
        var anime = repository.findById(4L);

        Assertions.assertThat(anime).isEmpty();
    }

    @Test
    @DisplayName("findByName() returns list of a anime when successful")
    void findByName_ReturnsListOfAnime_WhenSuccessful() {
        var animes = repository.findByName("One Punch Man");

        Assertions.assertThat(animes)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1)
                .contains(animes.get(0));
    }

    @Test
    @DisplayName("findByName() returns empty list when anime is not found")
    void findByName_ReturnsEmptyList_WhenAnimeIsNotFound() {
        var animes = repository.findByName("Naruto");

        Assertions.assertThat(animes).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("findByName() returns list of anime when name is null")
    void findByName_ReturnsListOfAnime_WhenNameIsNull() {
        var animes = repository.findByName(null);

        Assertions.assertThat(animes)
                .hasSameElementsAs(this.animes);
    }

    @Test
    @DisplayName("save() returns anime when successful")
    void save_ReturnsAnime_WhenSuccessful() {
        var animeToSave = Anime.builder().id(4L).name("Naruto").createdAt(LocalDateTime.now()).build();

        var savedAnime = repository.save(animeToSave);

        Assertions.assertThat(savedAnime).isNotNull().isEqualTo(animeToSave);

        this.animes.stream()
                .filter(a -> a.getId().equals(savedAnime.getId()))
                .findFirst()
                .ifPresent(a -> Assertions.assertThat(a).isEqualTo(savedAnime));

    }

    @Test
    @DisplayName("deleteById() removes anime when successful")
    void deleteById_RemovesAnime_WhenSuccessful() {
        var animeToDelete = animes.get(0);
        repository.deleteById(animeToDelete.getId());

        Assertions.assertThat(animes).doesNotContain(animeToDelete)
                .hasSize(2);
    }

    @Test
    @DisplayName("update() updates anime when successful")
    void update_UpdatesAnime_WhenSuccessful() {
        var animeToUpdate = Anime.builder().id(1L).name("Naruto").createdAt(LocalDateTime.now()).build();
        repository.update(animeToUpdate);

        Assertions.assertThat(animes).contains(animeToUpdate);

        this.animes.stream()
                .filter(a -> a.getId().equals(animeToUpdate.getId()))
                .findFirst()
                .ifPresent(a -> Assertions.assertThat(a).isEqualTo(animeToUpdate));
    }
}