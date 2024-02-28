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

    @Test
    @DisplayName("findById() returns a anime when successful")
    void findById_ReturnsAnime_WhenSuccessful() throws Exception {
        var validId = 1L;
        var response = readResourceFile("get-anime-1-id-200.json");
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/animes/{id}", validId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("findById() returns a exception when anime not found")
    void findById_ReturnsException_WhenAnimeNotFound() throws Exception {
        var invalidId = 99L;
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/animes/{id}", invalidId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("save() returns status code 201 when successful")
    void save_ReturnsStatusCode201_WhenSuccessful() throws Exception {
        var request = readResourceFile("post-request-anime-200.json");
        var response = readResourceFile("post-response-anime-201.json");
        var animeToBeSaved = Anime.builder()
                .id(99L).name("Bleach")
                .createdAt(LocalDateTime.now())
                .build();
        BDDMockito.when(repository.save(BDDMockito.any())).thenReturn(animeToBeSaved);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/animes")
                        .contentType("application/json")
                        .content(request))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    @DisplayName("replace() returns status code 204 when successful")
    void replace_ReturnsStatusCode204_WhenSuccessful() throws Exception {
        var request = readResourceFile("put-request-anime-200.json");
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/animes")
                        .contentType("application/json")
                        .content(request))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("replace() throws exception when no anime is found")
    void replace_ThrowsException_WhenAnimeNotFound() throws Exception {
        var request = readResourceFile("put-request-anime-404.json");
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/animes")
                        .contentType("application/json")
                        .content(request))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("Anime not found to be updated"));
    }

    @Test
    @DisplayName("deleteById() returns status code 204 when successful")
    void deleteById_ReturnsStatusCode204_WhenSuccessful() throws Exception {
        var id = 1L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/animes/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("deleteById() throws exception when anime not found")
    void deleteById_ThrowsException_WhenAnimeNotFound() throws Exception {
        var id = 99L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/animes/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("Anime not found to be deleted"));
    }

    private String readResourceFile(String fileName) throws IOException {
        var file = resourceLoader.getResource("classpath:%s".formatted(fileName))
                .getFile();
        return new String(Files.readAllBytes(file.toPath()));
    }

}