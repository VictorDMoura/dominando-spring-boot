package academy.devdojo.controller;


import academy.devdojo.domain.Anime;
import academy.devdojo.repository.AnimeData;
import academy.devdojo.repository.AnimeHardCodedRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.nio.file.Files;
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

    @Autowired
    private ResourceLoader resourceLoader;

    @BeforeEach
    void init() {
        var shingekiNoKyojin = Anime.builder().id(1L).name("Shingeki no Kyojin").createdAt(LocalDateTime.now()).build();
        var bokuNoHero = Anime.builder().id(2L).name("Boku no Hero").createdAt(LocalDateTime.now()).build();
        var onePunchMan = Anime.builder().id(3L).name("One Piece").createdAt(LocalDateTime.now()).build();

        List<Anime> animes = new ArrayList<>(List.of(shingekiNoKyojin, bokuNoHero, onePunchMan));
        BDDMockito.when(animeData.getAnimes()).thenReturn(animes);
    }

    @Test
    @DisplayName("list() returns a list and when name is null status code 200 when successful")
    void list_ReturnsStatusCode200_WhenSuccessful() throws Exception {
        var response = readResourceFile("get-anime-null-name-200.json");
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/animes"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));

    }

    @Test
    @DisplayName("findAll() returns a list and when find anime status code 200 when successful")
    void findById_ReturnsStatusCode200_WhenSuccessful() throws Exception {
        var response = readResourceFile("get-anime-OnePiece-name-200.json");
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/animes").param("name", "One Piece"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("findAll() return a empty list when name not found and status code 200")
    void list_ReturnsEmptyList_WhenNameNotFound() throws Exception {
        var response = readResourceFile("get-anime-not-found-name-200.json");
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/animes").param("name", "NotFound"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    private String readResourceFile(String fileName) throws IOException {
        var file = resourceLoader.getResource("classpath:%s".formatted(fileName))
                .getFile();
        return new String(Files.readAllBytes(file.toPath()));
    }

}