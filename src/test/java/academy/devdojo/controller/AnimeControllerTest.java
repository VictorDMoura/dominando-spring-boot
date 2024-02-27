package academy.devdojo.controller;


import academy.devdojo.domain.Anime;
import academy.devdojo.repository.AnimeData;
import academy.devdojo.repository.AnimeHardCodedRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@WebMvcTest(AnimeController.class)
class AnimeControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    private AnimeData animeData;
    @SpyBean
    private AnimeHardCodedRepository repository;

    @BeforeEach
    void init() {
        var shingekiNoKyojin = Anime.builder().id(1L).name("Shingeki no Kyojin").createdAt(LocalDateTime.now()).build();
        var bokuNoHero = Anime.builder().id(2L).name("Boku no Hero").createdAt(LocalDateTime.now()).build();
        var onePunchMan = Anime.builder().id(3L).name("One Piece").createdAt(LocalDateTime.now()).build();

        List<Anime> animes = new ArrayList<>(List.of(shingekiNoKyojin, bokuNoHero, onePunchMan));
        BDDMockito.when(animeData.getAnimes()).thenReturn(animes);
    }

}